package com.ticket.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.auditoria.AuditoriaResponseDTO;
import com.ticket.services.interfaces.AuditoriaService;

/**
 * Controlador REST de consulta de la bitácora de auditoría.
 *
 * <p>Acceso restringido al Administrador (configurado en {@code SecurityConfig}).
 * Ruta: {@code GET /api/auditoria} con filtros opcionales {@code accion} e
 * {@code idUsuario}.</p>
 */
@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    /**
     * Lista los eventos de auditoría más recientes.
     *
     * @param accion    filtro por acción (opcional).
     * @param idUsuario filtro por actor (opcional).
     * @return eventos de auditoría.
     */
    @GetMapping
    public ResponseEntity<List<AuditoriaResponseDTO>> listar(
            @RequestParam(required = false) String accion,
            @RequestParam(required = false) Long idUsuario) {
        return ResponseEntity.ok(auditoriaService.listar(accion, idUsuario));
    }
}
