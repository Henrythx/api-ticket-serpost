package com.ticket.dto.ticket.historialTicket;

import java.time.LocalDateTime;

import com.ticket.model.ticket.HistorialTicketEntity;

public class HistorialTicketDTO {

    private Long idHistorial;
    private Long idTicket;
    private String usuarioAccionNombre;
    private String estadoAnteriorNombre;
    private String estadoNuevoNombre;
    private String tipoEvento;
    private String comentario;
    private LocalDateTime fechaCambio;

    public static HistorialTicketDTO fromEntity(HistorialTicketEntity entity) {
        HistorialTicketDTO dto = new HistorialTicketDTO();
        dto.setIdHistorial(entity.getIdHistorial());
        dto.setIdTicket(entity.getTicket() != null ? entity.getTicket().getIdTicket() : null);
        dto.setTipoEvento(entity.getTipoEvento());
        dto.setComentario(entity.getComentario());
        dto.setFechaCambio(entity.getFechaCambio());

        dto.setUsuarioAccionNombre(entity.getUsuario() != null ? entity.getUsuario().getNombre() : null);
        dto.setEstadoAnteriorNombre(entity.getEstadoAnterior() != null ? entity.getEstadoAnterior().getNombre() : null);
        dto.setEstadoNuevoNombre(entity.getEstadoNuevo() != null ? entity.getEstadoNuevo().getNombre() : null);

        return dto;
    }

    public Long getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public String getUsuarioAccionNombre() {
        return usuarioAccionNombre;
    }

    public void setUsuarioAccionNombre(String usuarioAccionNombre) {
        this.usuarioAccionNombre = usuarioAccionNombre;
    }

    public String getEstadoAnteriorNombre() {
        return estadoAnteriorNombre;
    }

    public void setEstadoAnteriorNombre(String estadoAnteriorNombre) {
        this.estadoAnteriorNombre = estadoAnteriorNombre;
    }

    public String getEstadoNuevoNombre() {
        return estadoNuevoNombre;
    }

    public void setEstadoNuevoNombre(String estadoNuevoNombre) {
        this.estadoNuevoNombre = estadoNuevoNombre;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    // getters y setters
    
}

