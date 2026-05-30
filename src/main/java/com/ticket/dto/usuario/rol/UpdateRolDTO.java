package com.ticket.dto.usuario.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateRolDTO {
    @NotNull(message = "El ID del rol es obligatorio")
    private Long idRol;

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;



    
    @Override
    public String toString() {
        return "UpdateRolDTO [idRol=" + idRol + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }


    

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
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
