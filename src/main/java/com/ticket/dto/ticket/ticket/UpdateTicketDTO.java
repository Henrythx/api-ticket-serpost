package com.ticket.dto.ticket.ticket;

import jakarta.validation.constraints.NotBlank;

public class UpdateTicketDTO {

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    private String descripcion;
    private Long idUsuarioTecnico;
    private Long idCategoria;
    private Long idPrioridad;
    private Long idEstado;

    
    @Override
    public String toString() {
        return "UpdateTicketDTO [titulo=" + titulo + ", descripcion=" + descripcion + ", idUsuarioTecnico="
                + idUsuarioTecnico + ", idCategoria=" + idCategoria + ", idPrioridad=" + idPrioridad + ", idEstado="
                + idEstado + "]";
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
    public Long getIdUsuarioTecnico() {
        return idUsuarioTecnico;
    }
    public void setIdUsuarioTecnico(Long idUsuarioTecnico) {
        this.idUsuarioTecnico = idUsuarioTecnico;
    }
    public Long getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
    public Long getIdPrioridad() {
        return idPrioridad;
    }
    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }
    public Long getIdEstado() {
        return idEstado;
    }
    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    
}

