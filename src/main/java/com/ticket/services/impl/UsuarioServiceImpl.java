package com.ticket.services.impl;

import com.ticket.dto.usuario.usuario.*;
import com.ticket.model.usuario.AreaEntity;
import com.ticket.model.usuario.RolEntity;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.jpa.AreaRepository;
import com.ticket.repositories.jpa.RolRepository;
import com.ticket.repositories.jpa.UsuarioRepository;
import com.ticket.model.CustomError;
import com.ticket.services.interfaces.AuditoriaService;
import com.ticket.services.interfaces.UsuarioService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    @Transactional
    public UsuarioResponseDTO crearUsuario(CreateUsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw CustomError.conflict("Email ya registrado", "UsuarioServiceImpl", "Duplicado");
        }

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());
        // La contraseña se almacena cifrada con BCrypt, nunca en texto plano.
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setActivo(true);
        entity.setCreadoEn(LocalDateTime.now()); // Corregido: Se asigna ANTES de guardar

        this.asignarRelaciones(entity, dto.getIdArea(), dto.getIdRol());

        UsuarioResponseDTO creado = toResponseDTO(usuarioRepository.save(entity));
        auditoriaService.registrarActorActual(AuditoriaService.CREAR_USUARIO,
                "Usuario #" + creado.getIdUsuario(), "Creó al usuario " + creado.getEmail());
        return creado;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(UpdateUsuarioDTO dto) {
        UsuarioEntity entity = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> CustomError.notFound("Usuario no encontrado", "UsuarioServiceImpl"));
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());

        this.asignarRelaciones(entity, dto.getIdArea(), dto.getIdRol());

        UsuarioResponseDTO actualizado = toResponseDTO(usuarioRepository.save(entity));
        auditoriaService.registrarActorActual(AuditoriaService.MODIFICACION,
                "Usuario #" + actualizado.getIdUsuario(),
                "Modificó los datos de " + actualizado.getEmail());
        return actualizado;
    }










    @Override
    public void cambiarEstadoUsuario(ChangeEstadoUsuarioDTO dto) {
        UsuarioEntity entity = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> CustomError.notFound("Usuario no encontrado", "UsuarioServiceImpl"));
        entity.setActivo(dto.getActivo());
        usuarioRepository.save(entity);
        auditoriaService.registrarActorActual(AuditoriaService.MODIFICACION,
                "Usuario #" + entity.getIdUsuario(),
                (Boolean.TRUE.equals(dto.getActivo()) ? "Activó" : "Inactivó") + " la cuenta "
                        + entity.getEmail());
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long idUsuario) {
        UsuarioEntity entity = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> CustomError.notFound("Usuario no encontrado", "UsuarioServiceImpl"));
        String email = entity.getEmail();
        try {
            // flush fuerza el DELETE para capturar aquí una posible violación de
            // integridad referencial (usuario con tickets/historial asociados).
            usuarioRepository.delete(entity);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw CustomError.conflict(
                    "No se puede eliminar: el usuario tiene tickets o registros asociados. "
                            + "Inactívelo en su lugar.",
                    "UsuarioServiceImpl", "FK-constraint");
        }
        auditoriaService.registrarActorActual(AuditoriaService.ELIMINACION,
                "Usuario #" + idUsuario, "Eliminó al usuario " + email);
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Long idUsuario) {
        UsuarioEntity entity = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> CustomError.notFound("Usuario no encontrado", "UsuarioServiceImpl"));
        return toResponseDTO(entity);
    }









    @Override
    public List<UsuarioListDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioListDTO> buscarPorArea(Long idArea) {
        return usuarioRepository.findByArea_IdArea(idArea).stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioListDTO> buscarPorRol(Long idRol) {
        return usuarioRepository.findByRol_IdRol(idRol).stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioListDTO> buscarPorNombreApellido(String nombre, String apellido) {
        return usuarioRepository.findByNombreContainingOrApellidoContaining(nombre, apellido).stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }








    // Método privado auxiliar para evitar duplicar código de asignación
    private void asignarRelaciones(UsuarioEntity entity, Long idArea, Long idRol) {
        if (idArea != null && idArea > 0) {
            AreaEntity area = areaRepository.findById(idArea).orElse(null); // Corregido orElseGet
            entity.setArea(area);
        }
        if (idRol != null && idRol > 0) {
            RolEntity rol = rolRepository.findById(idRol).orElse(null); // Corregido orElseGet
            entity.setRol(rol);
        }
    }

    private UsuarioResponseDTO toResponseDTO(UsuarioEntity entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setActivo(entity.getActivo());
        dto.setCreadoEn(entity.getCreadoEn());

        if(entity.getArea() != null) dto.setAreaNombre(entity.getArea().getNombre());
        if(entity.getRol() != null) dto.setRolNombre(entity.getRol().getNombre());
        return dto;
    }

    private UsuarioListDTO toListDTO(UsuarioEntity entity) {
        UsuarioListDTO dto = new UsuarioListDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setActivo(entity.getActivo());

        if(entity.getArea() != null) dto.setAreaNombre(entity.getArea().getNombre());
        if(entity.getRol() != null) dto.setRolNombre(entity.getRol().getNombre());

        return dto;
    }
}
