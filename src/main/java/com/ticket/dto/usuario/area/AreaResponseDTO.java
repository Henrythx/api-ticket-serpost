package com.ticket.dto.usuario.area;

public class AreaResponseDTO {
    private Long idArea;
    private String nombre;
    private String descripcion;
    private Boolean activo;

    


    @Override
    public String toString() {
        return "AreaResponseDTO [idArea=" + idArea + ", nombre=" + nombre + ", descripcion=" + descripcion + ", activo="
                + activo + "]";
    }




    public Long getIdArea() {
        return idArea;
    }
    public void setIdArea(Long idArea) {
        this.idArea = idArea;
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