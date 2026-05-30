package com.ticket.dto.usuario.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateRolDTO {
    
    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;



    
    @Override
    public String toString() {
        return "CreateRolDTO [nombre=" + nombre + ", descripcion=" + descripcion + "]";
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
}
