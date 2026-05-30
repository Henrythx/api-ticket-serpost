package com.ticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.auth.LoginDTO;
import com.ticket.dto.auth.SesionUsuarioDTO;
import com.ticket.services.interfaces.AuditoriaService;
import com.ticket.services.interfaces.AuthService;

import jakarta.validation.Valid;

/**
 * Controlador REST de autenticación.
 *
 * <p>Punto de entrada del servicio de utilidad de autenticación. Acepta el inicio
 * de sesión en dos rutas equivalentes para mantener compatibilidad con el
 * frontend:</p>
 * <ul>
 *   <li>{@code POST /api/auth/login} (ruta canónica)</li>
 *   <li>{@code POST /api/usuarios/login} (alias)</li>
 * </ul>
 */
@RestController
public class AuthController {

    private final AuthService authService;
    private final AuditoriaService auditoriaService;

    public AuthController(AuthService authService, AuditoriaService auditoriaService) {
        this.authService = authService;
        this.auditoriaService = auditoriaService;
    }

    /**
     * Valida credenciales y devuelve la sesión del usuario.
     *
     * @param dto credenciales de acceso.
     * @return 200 con los datos de sesión si la autenticación es correcta.
     */
    @PostMapping({"/auth/login", "/usuarios/login"})
    public ResponseEntity<SesionUsuarioDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    /**
     * Cierra la sesión del usuario autenticado y registra el evento en la auditoría.
     *
     * @return 204 sin contenido.
     */
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            auditoriaService.registrar(null, auth.getName(), AuditoriaService.LOGOUT,
                    "Autenticación", "Cierre de sesión");
        }
        return ResponseEntity.noContent().build();
    }
}

