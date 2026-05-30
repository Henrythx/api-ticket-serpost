package com.ticket.model.usuario;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "area")
public class AreaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArea;

    private String nombre;
    private String descripcion;
    private Boolean activo;

    @OneToMany(mappedBy = "area")
    private List<UsuarioEntity> usuarios;




    public AreaEntity() {
    }




    @Override
    public String toString() {
        return "AreaEntity [idArea=" + idArea + ", nombre=" + nombre + ", descripcion=" + descripcion + ", activo="
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

    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }

    
}
