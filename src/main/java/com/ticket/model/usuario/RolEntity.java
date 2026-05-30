package com.ticket.model.usuario;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "rol")
public class RolEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "rol")
    private List<UsuarioEntity> usuarios;



    
    public RolEntity() {
    }




    @Override
    public String toString() {
        return "RolEntity [idRol=" + idRol + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
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

    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }
}