package com.ticket.dto.usuario.rol;

public class RolResponseDTO {
    private Long idRol;
    private String nombre;
    private String descripcion;


    

    
    @Override
    public String toString() {
        return "RolResponseDTO [idRol=" + idRol + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
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
