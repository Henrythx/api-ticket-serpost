package com.ticket.dto.ticket.ticket;

import java.time.LocalDateTime;

import com.ticket.model.ticket.TicketEntity;

public class TicketResponseDTO {

    private Long idTicket;
    private String titulo;
    private String descripcion;
    private String estadoNombre;
    private String prioridadNivel;
    private String categoriaNombre;
    private String usuarioSolicitanteNombre;
    private String usuarioTecnicoNombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaAtencion;
    private LocalDateTime fechaResolucion;
    private LocalDateTime slaVencimiento;

    
    public static TicketResponseDTO fromEntity(TicketEntity entity) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setIdTicket(entity.getIdTicket());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaAtencion(entity.getFechaAtencion());
        dto.setFechaResolucion(entity.getFechaResolucion());
        dto.setSlaVencimiento(entity.getSlaVencimiento());

        // referencias foráneas
        dto.setEstadoNombre(entity.getEstado() != null ? entity.getEstado().getNombre() : null);
        dto.setPrioridadNivel(entity.getPrioridad() != null ? entity.getPrioridad().getNivel() : null);
        dto.setCategoriaNombre(entity.getCategoria() != null ? entity.getCategoria().getNombre() : null);
        dto.setUsuarioSolicitanteNombre(entity.getUsuarioSolicitante() != null ? entity.getUsuarioSolicitante().getNombre() : null);
        dto.setUsuarioTecnicoNombre(entity.getUsuarioTecnico() != null ? entity.getUsuarioTecnico().getNombre() : null);

        return dto;
    }

    @Override
    public String toString() {
        return "TicketResponseDTO [idTicket=" + idTicket + ", titulo=" + titulo + ", descripcion=" + descripcion
                + ", estadoNombre=" + estadoNombre + ", prioridadNivel=" + prioridadNivel + ", categoriaNombre="
                + categoriaNombre + ", usuarioSolicitanteNombre=" + usuarioSolicitanteNombre + ", usuarioTecnicoNombre="
                + usuarioTecnicoNombre + ", fechaCreacion=" + fechaCreacion + ", fechaAtencion=" + fechaAtencion
                + ", fechaResolucion=" + fechaResolucion + ", slaVencimiento=" + slaVencimiento + "]";
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getUsuarioSolicitanteNombre() {
        return usuarioSolicitanteNombre;
    }

    public void setUsuarioSolicitanteNombre(String usuarioSolicitanteNombre) {
        this.usuarioSolicitanteNombre = usuarioSolicitanteNombre;
    }

    public String getUsuarioTecnicoNombre() {
        return usuarioTecnicoNombre;
    }

    public void setUsuarioTecnicoNombre(String usuarioTecnicoNombre) {
        this.usuarioTecnicoNombre = usuarioTecnicoNombre;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDateTime fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDateTime getSlaVencimiento() {
        return slaVencimiento;
    }

    public void setSlaVencimiento(LocalDateTime slaVencimiento) {
        this.slaVencimiento = slaVencimiento;
    }

    // getters y setters
    
}
