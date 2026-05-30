package com.ticket.dto.ticket.otros;

import com.ticket.model.ticket.CategoriaTicketEntity;

public class CategoriaTicketDTO {
    private Long idCategoria;
    private String nombre;
    private String tipo;
    private Boolean activo;




    public static CategoriaTicketDTO fromEntity(CategoriaTicketEntity entity) {
        CategoriaTicketDTO dto = new CategoriaTicketDTO();
        dto.setIdCategoria(entity.getIdCategoria());
        dto.setNombre(entity.getNombre());
        dto.setTipo(entity.getTipo());
        dto.setActivo(entity.getActivo());
        return dto;
    }




    @Override
    public String toString() {
        return "CategoriaTicketDTO [idCategoria=" + idCategoria + ", nombre=" + nombre + ", tipo=" + tipo + ", activo="
                + activo + "]";
    }



    
    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    
}

