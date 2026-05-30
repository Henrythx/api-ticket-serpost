package com.ticket.services.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ticket.dto.auth.LoginDTO;
import com.ticket.dto.auth.SesionUsuarioDTO;
import com.ticket.model.CustomError;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.jpa.UsuarioRepository;
import com.ticket.security.JwtService;
import com.ticket.services.interfaces.AuditoriaService;
import com.ticket.services.interfaces.AuthService;

import jakarta.transaction.Transactional;

/**
 * Implementación del servicio de autenticación.
 *
 * <p>Flujo de autenticación (alineado con el diseño de seguridad del proyecto):</p>
 * <ol>
 *   <li>Resuelve al usuario por correo o por nombre.</li>
 *   <li>Verifica la contraseña recibida contra el hash BCrypt almacenado.</li>
 *   <li>Comprueba que la cuenta esté activa.</li>
 *   <li>Registra el último acceso y devuelve la sesión.</li>
 * </ol>
 *
 * <p>Por seguridad, ante credenciales incorrectas se devuelve un mensaje genérico
 * (no se revela si el fallo fue por usuario inexistente o contraseña errónea).</p>
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuditoriaService auditoriaService;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                           JwtService jwtService, AuditoriaService auditoriaService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    @Transactional
    public SesionUsuarioDTO login(LoginDTO dto) {
        String identifier = dto.getIdentifier() == null ? "" : dto.getIdentifier().trim();

        // 1. Resolver usuario por email o, en su defecto, por nombre.
        UsuarioEntity usuario = usuarioRepository.findByEmail(identifier)
                .or(() -> usuarioRepository.findByNombreIgnoreCase(identifier))
                .orElse(null);
        if (usuario == null) {
            auditoriaService.registrar(null, identifier, AuditoriaService.LOGIN_FALLIDO,
                    "Autenticación", "Intento de acceso con un identificador inexistente");
            throw CustomError.unauthorized("Credenciales incorrectas", "AuthServiceImpl");
        }

        // 2. Verificar la contraseña contra el hash BCrypt.
        if (usuario.getPassword() == null
                || !passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            auditoriaService.registrar(usuario.getIdUsuario(), usuario.getEmail(),
                    AuditoriaService.LOGIN_FALLIDO, "Autenticación", "Contraseña incorrecta");
            throw CustomError.unauthorized("Credenciales incorrectas", "AuthServiceImpl");
        }

        // 3. Comprobar que la cuenta esté activa.
        if (Boolean.FALSE.equals(usuario.getActivo())) {
            auditoriaService.registrar(usuario.getIdUsuario(), usuario.getEmail(),
                    AuditoriaService.LOGIN_FALLIDO, "Autenticación", "Cuenta deshabilitada");
            throw CustomError.forbidden("La cuenta está deshabilitada", "AuthServiceImpl");
        }

        // 4. Registrar el último acceso.
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // 5. Emitir el token JWT firmado con los datos de la sesión.
        SesionUsuarioDTO sesion = toSesionDTO(usuario);
        sesion.setToken(jwtService.generarToken(
                usuario.getEmail(), usuario.getIdUsuario(),
                sesion.getIdRol(), sesion.getNombreRol()));

        // 6. Auditar el inicio de sesión exitoso.
        auditoriaService.registrar(usuario.getIdUsuario(), usuario.getEmail(),
                AuditoriaService.LOGIN, "Autenticación", "Inicio de sesión exitoso");
        return sesion;
    }

    /**
     * Construye el DTO de sesión a partir de la entidad de usuario.
     *
     * @param u usuario autenticado.
     * @return datos de sesión sin información sensible.
     */
    private SesionUsuarioDTO toSesionDTO(UsuarioEntity u) {
        SesionUsuarioDTO dto = new SesionUsuarioDTO();
        dto.setId(u.getIdUsuario());
        dto.setNombre(u.getNombre());
        dto.setApellido(u.getApellido());
        dto.setEmail(u.getEmail());
        dto.setActivo(u.getActivo());
        if (u.getRol() != null) {
            dto.setIdRol(u.getRol().getIdRol());
            dto.setNombreRol(u.getRol().getNombre());
        }
        if (u.getArea() != null) {
            dto.setIdArea(u.getArea().getIdArea());
        }
        return dto;
    }
}
