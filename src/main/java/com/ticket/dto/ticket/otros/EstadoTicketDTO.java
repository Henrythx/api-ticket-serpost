package com.ticket.dto.ticket.otros;

import com.ticket.model.ticket.EstadoTicketEntity;

public class EstadoTicketDTO {
    private Long idEstado;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Boolean esTerminal;

    public static EstadoTicketDTO fromEntity(EstadoTicketEntity entity) {
        EstadoTicketDTO dto = new EstadoTicketDTO();
        dto.setIdEstado(entity.getIdEstado());
        dto.setCodigo(entity.getCodigo());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEsTerminal(entity.getEsTerminal());
        return dto;
    }

    

    @Override
    public String toString() {
        return "EstadoTicketDTO [idEstado=" + idEstado + ", codigo=" + codigo + ", nombre=" + nombre + ", descripcion="
                + descripcion + ", esTerminal=" + esTerminal + "]";
    }



    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEsTerminal() {
        return esTerminal;
    }

    public void setEsTerminal(Boolean esTerminal) {
        this.esTerminal = esTerminal;
    }
    
}
