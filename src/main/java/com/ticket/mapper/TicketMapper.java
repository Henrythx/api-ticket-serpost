package com.ticket.mapper;

import com.ticket.dto.ticket.AreaTicketDTO;
import com.ticket.dto.ticket.CategoriaTicketDTO;
import com.ticket.dto.ticket.EstadoTicketDTO;
import com.ticket.dto.ticket.HistorialTicketDTO;
import com.ticket.dto.ticket.PrioridadTicketDTO;
import com.ticket.dto.ticket.SlaResponseDTO;
import com.ticket.dto.ticket.TicketResponseDTO;
import com.ticket.dto.ticket.UsuarioMiniDTO;
import com.ticket.model.ticket.CategoriaTicketEntity;
import com.ticket.model.ticket.EstadoTicketEntity;
import com.ticket.model.ticket.HistorialTicketEntity;
import com.ticket.model.ticket.PrioridadTicketEntity;
import com.ticket.model.ticket.SlaEntity;
import com.ticket.model.ticket.TicketEntity;
import com.ticket.model.usuario.AreaEntity;
import com.ticket.model.usuario.UsuarioEntity;

/**
 * Mapeador centralizado entre entidades JPA y DTOs del módulo de tickets.
 *
 * <p>Concentra en un único lugar la conversión de objetos de dominio a objetos de
 * transferencia (DTO), evitando duplicar lógica de mapeo en los distintos
 * servicios (principio DRY) y manteniendo a los servicios enfocados en las reglas
 * de negocio.</p>
 *
 * <p>Todos los métodos son nulo-seguros: devuelven {@code null} si la entidad de
 * entrada es {@code null}, lo que simplifica el mapeo de relaciones opcionales.</p>
 */
public final class TicketMapper {

    private TicketMapper() {
        // Clase de utilidad: no instanciable.
    }

    /**
     * Convierte una entidad de estado en su DTO.
     *
     * @param e entidad de estado (puede ser {@code null}).
     * @return DTO de estado o {@code null}.
     */
    public static EstadoTicketDTO toEstadoDTO(EstadoTicketEntity e) {
        if (e == null) {
            return null;
        }
        return new EstadoTicketDTO(e.getIdEstado(), e.getCodigo(), e.getNombre(),
                e.getDescripcion(), e.getEsTerminal());
    }

    /**
     * Convierte una entidad de prioridad en su DTO.
     *
     * @param p entidad de prioridad (puede ser {@code null}).
     * @return DTO de prioridad o {@code null}.
     */
    public static PrioridadTicketDTO toPrioridadDTO(PrioridadTicketEntity p) {
        if (p == null) {
            return null;
        }
        return new PrioridadTicketDTO(p.getIdPrioridad(), p.getNivel(), p.getColorHex());
    }

    /**
     * Convierte una entidad de categoría en su DTO.
     *
     * @param c entidad de categoría (puede ser {@code null}).
     * @return DTO de categoría o {@code null}.
     */
    public static CategoriaTicketDTO toCategoriaDTO(CategoriaTicketEntity c) {
        if (c == null) {
            return null;
        }
        return new CategoriaTicketDTO(c.getIdCategoria(), c.getNombre(), c.getTipo(), c.getActivo());
    }

    /**
     * Convierte un área en su vista reducida para anidar en un ticket.
     *
     * @param a entidad de área (puede ser {@code null}).
     * @return DTO de área o {@code null}.
     */
    public static AreaTicketDTO toAreaDTO(AreaEntity a) {
        if (a == null) {
            return null;
        }
        return new AreaTicketDTO(a.getIdArea(), a.getNombre());
    }

    /**
     * Convierte un usuario en su vista reducida (sin contraseña).
     *
     * @param u entidad de usuario (puede ser {@code null}).
     * @return vista reducida del usuario o {@code null}.
     */
    public static UsuarioMiniDTO toUsuarioMiniDTO(UsuarioEntity u) {
        if (u == null) {
            return null;
        }
        UsuarioMiniDTO dto = new UsuarioMiniDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setNombre(u.getNombre());
        dto.setApellido(u.getApellido());
        dto.setEmail(u.getEmail());
        dto.setActivo(u.getActivo());
        dto.setCreadoEn(u.getCreadoEn());
        dto.setUltimoAcceso(u.getUltimoAcceso());
        if (u.getArea() != null) {
            dto.setIdArea(u.getArea().getIdArea());
        }
        if (u.getRol() != null) {
            dto.setIdRol(u.getRol().getIdRol());
        }
        return dto;
    }

    /**
     * Convierte un ticket completo en su DTO de respuesta, incluyendo las
     * relaciones anidadas (estado, categoría, prioridad y usuarios).
     *
     * @param t entidad de ticket (puede ser {@code null}).
     * @return DTO de ticket o {@code null}.
     */
    public static TicketResponseDTO toTicketDTO(TicketEntity t) {
        if (t == null) {
            return null;
        }
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setIdTicket(t.getIdTicket());
        dto.setTitulo(t.getTitulo());
        dto.setDescripcion(t.getDescripcion());
        dto.setFechaCreacion(t.getFechaCreacion());
        dto.setFechaAtencion(t.getFechaAtencion());
        dto.setFechaResolucion(t.getFechaResolucion());
        dto.setSlaVencimiento(t.getSlaVencimiento());

        if (t.getUsuarioSolicitante() != null) {
            dto.setIdUsuarioSolicitante(t.getUsuarioSolicitante().getIdUsuario());
            dto.setUsuarioSolicitante(toUsuarioMiniDTO(t.getUsuarioSolicitante()));
        }
        if (t.getUsuarioTecnico() != null) {
            dto.setIdUsuarioTecnico(t.getUsuarioTecnico().getIdUsuario());
            dto.setUsuarioTecnico(toUsuarioMiniDTO(t.getUsuarioTecnico()));
        }
        if (t.getArea() != null) {
            dto.setIdArea(t.getArea().getIdArea());
            dto.setArea(toAreaDTO(t.getArea()));
        }
        if (t.getCategoria() != null) {
            dto.setIdCategoria(t.getCategoria().getIdCategoria());
            dto.setCategoria(toCategoriaDTO(t.getCategoria()));
        }
        if (t.getPrioridad() != null) {
            dto.setIdPrioridad(t.getPrioridad().getIdPrioridad());
            dto.setPrioridad(toPrioridadDTO(t.getPrioridad()));
        }
        if (t.getEstado() != null) {
            dto.setIdEstado(t.getEstado().getIdEstado());
            dto.setEstado(toEstadoDTO(t.getEstado()));
        }
        return dto;
    }

    /**
     * Convierte un evento de historial en su DTO, resolviendo el usuario
     * responsable y los códigos de estado anterior/nuevo.
     *
     * @param h entidad de historial (puede ser {@code null}).
     * @return DTO de historial o {@code null}.
     */
    public static HistorialTicketDTO toHistorialDTO(HistorialTicketEntity h) {
        if (h == null) {
            return null;
        }
        HistorialTicketDTO dto = new HistorialTicketDTO();
        dto.setIdHistorial(h.getIdHistorial());
        dto.setTipoEvento(h.getTipoEvento());
        dto.setComentario(h.getComentario());
        dto.setFechaCambio(h.getFechaCambio());
        if (h.getTicket() != null) {
            dto.setIdTicket(h.getTicket().getIdTicket());
        }
        if (h.getUsuario() != null) {
            dto.setIdUsuario(h.getUsuario().getIdUsuario());
            dto.setUsuario(toUsuarioMiniDTO(h.getUsuario()));
        }
        if (h.getEstadoAnterior() != null) {
            dto.setEstadoAnterior(h.getEstadoAnterior().getCodigo());
        }
        if (h.getEstadoNuevo() != null) {
            dto.setEstadoNuevo(h.getEstadoNuevo().getCodigo());
        }
        return dto;
    }

    /**
     * Convierte una regla de SLA en su DTO, mapeando los tiempos a horas e
     * incluyendo la categoría y prioridad asociadas.
     *
     * @param s entidad de SLA (puede ser {@code null}).
     * @return DTO de SLA o {@code null}.
     */
    public static SlaResponseDTO toSlaDTO(SlaEntity s) {
        if (s == null) {
            return null;
        }
        SlaResponseDTO dto = new SlaResponseDTO();
        dto.setIdSla(s.getIdSla());
        dto.setTiempoAtencionHoras(s.getTiempoAtencion());
        dto.setTiempoResolucionHoras(s.getTiempoResolucion());
        dto.setActivo(s.getActivo());
        if (s.getCategoria() != null) {
            dto.setIdCategoria(s.getCategoria().getIdCategoria());
            dto.setCategoria(toCategoriaDTO(s.getCategoria()));
        }
        if (s.getPrioridad() != null) {
            dto.setIdPrioridad(s.getPrioridad().getIdPrioridad());
            dto.setPrioridad(toPrioridadDTO(s.getPrioridad()));
        }
        return dto;
    }
}
