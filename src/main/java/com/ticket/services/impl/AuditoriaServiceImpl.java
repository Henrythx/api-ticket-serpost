package com.ticket.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ticket.dto.auditoria.AuditoriaResponseDTO;
import com.ticket.model.auditoria.AuditoriaEntity;
import com.ticket.repositories.jpa.AuditoriaRepository;
import com.ticket.services.interfaces.AuditoriaService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Implementación del servicio de auditoría.
 *
 * <p>Persiste cada evento en la tabla {@code auditoria}, resolviendo la IP de la
 * petición HTTP en curso. Todas las operaciones de registro son tolerantes a
 * fallos para no afectar nunca al flujo principal.</p>
 */
@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    private static final Logger log = LoggerFactory.getLogger(AuditoriaServiceImpl.class);

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaServiceImpl(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @Override
    public void registrar(Long idUsuario, String usuario, String accion, String entidad, String detalle) {
        try {
            AuditoriaEntity a = new AuditoriaEntity();
            a.setIdUsuario(idUsuario);
            a.setUsuario(usuario);
            a.setAccion(accion);
            a.setEntidad(entidad);
            a.setDetalle(detalle);
            a.setIp(obtenerIp());
            a.setFecha(LocalDateTime.now());
            auditoriaRepository.save(a);
        } catch (Exception ex) {
            log.warn("No se pudo registrar la auditoría '{}': {}", accion, ex.getMessage());
        }
    }

    @Override
    public void registrarActorActual(String accion, String entidad, String detalle) {
        String actor = "sistema";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getName() != null) {
            actor = auth.getName();
        }
        registrar(null, actor, accion, entidad, detalle);
    }

    @Override
    public List<AuditoriaResponseDTO> listar(String accion, Long idUsuario) {
        return auditoriaRepository.findTop500ByOrderByFechaDesc().stream()
                .filter(a -> accion == null || accion.isBlank() || accion.equalsIgnoreCase(a.getAccion()))
                .filter(a -> idUsuario == null || idUsuario.equals(a.getIdUsuario()))
                .map(this::toDTO)
                .toList();
    }

    /** Obtiene la IP de origen de la petición actual (respeta X-Forwarded-For). */
    private String obtenerIp() {
        try {
            if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs) {
                HttpServletRequest req = attrs.getRequest();
                String forwarded = req.getHeader("X-Forwarded-For");
                if (forwarded != null && !forwarded.isBlank()) {
                    return forwarded.split(",")[0].trim();
                }
                return req.getRemoteAddr();
            }
        } catch (Exception ignored) {
            // Sin contexto de petición (ej. tareas en segundo plano): IP nula.
        }
        return null;
    }

    private AuditoriaResponseDTO toDTO(AuditoriaEntity a) {
        AuditoriaResponseDTO dto = new AuditoriaResponseDTO();
        dto.setIdAuditoria(a.getIdAuditoria());
        dto.setIdUsuario(a.getIdUsuario());
        dto.setUsuario(a.getUsuario());
        dto.setAccion(a.getAccion());
        dto.setEntidad(a.getEntidad());
        dto.setDetalle(a.getDetalle());
        dto.setIp(a.getIp());
        dto.setFecha(a.getFecha());
        return dto;
    }
}
