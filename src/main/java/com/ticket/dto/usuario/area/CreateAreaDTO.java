package com.ticket.dto.usuario.area;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateAreaDTO {

    @NotBlank(message = "El nombre del área es obligatorio")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;



    
    @Override
    public String toString() {
        return "CreateAreaDTO [nombre=" + nombre + ", descripcion=" + descripcion + ", activo=" + activo + "]";
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}