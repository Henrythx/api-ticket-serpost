package com.ticket.dto.ticket.ticket;

import java.time.LocalDateTime;

import com.ticket.model.ticket.TicketEntity;

public class TicketListDTO {

    private Long idTicket;
    private String titulo;
    private String estadoNombre;
    private String prioridadNivel;
    private LocalDateTime fechaCreacion;
    private String usuarioSolicitanteNombre;

    public static TicketListDTO fromEntity(TicketEntity entity) {
        TicketListDTO dto = new TicketListDTO();
        dto.setIdTicket(entity.getIdTicket());
        dto.setTitulo(entity.getTitulo());
        dto.setFechaCreacion(entity.getFechaCreacion());

        dto.setEstadoNombre(entity.getEstado() != null ? entity.getEstado().getNombre() : null);
        dto.setPrioridadNivel(entity.getPrioridad() != null ? entity.getPrioridad().getNivel() : null);
        dto.setUsuarioSolicitanteNombre(entity.getUsuarioSolicitante() != null ? entity.getUsuarioSolicitante().getNombre() : null);

        return dto;
    }

    

    @Override
    public String toString() {
        return "TicketListDTO [idTicket=" + idTicket + ", titulo=" + titulo + ", estadoNombre=" + estadoNombre
                + ", prioridadNivel=" + prioridadNivel + ", fechaCreacion=" + fechaCreacion
                + ", usuarioSolicitanteNombre=" + usuarioSolicitanteNombre + "]";
    }



    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    public String getPrioridadNivel() {
        return prioridadNivel;
    }

    public void setPrioridadNivel(String prioridadNivel) {
        this.prioridadNivel = prioridadNivel;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioSolicitanteNombre() {
        return usuarioSolicitanteNombre;
    }

    public void setUsuarioSolicitanteNombre(String usuarioSolicitanteNombre) {
        this.usuarioSolicitanteNombre = usuarioSolicitanteNombre;
    }

    // getters y setters
    
}

