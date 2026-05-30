package com.ticket.dto.usuario.usuario;

public class UsuarioListDTO {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private Boolean activo;


    

    @Override
    public String toString() {
        return "UsuarioListDTO [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", email="
                + email + ", activo=" + activo + "]";
    }



    
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}

