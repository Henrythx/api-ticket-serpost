package com.ticket.dto.usuario;

import java.time.LocalDateTime;

public class UsuarioReponseDTO {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String areaNombre;
    private String rolNombre;
    private Boolean activo;
    private LocalDateTime creadoEn;
    private LocalDateTime ultimoAcceso;

    


    @Override
    public String toString() {
        return "UsuarioReponseDTO [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido
                + ", email=" + email + ", areaNombre=" + areaNombre + ", rolNombre=" + rolNombre + ", activo=" + activo
                + ", creadoEn=" + creadoEn + ", ultimoAcceso=" + ultimoAcceso + "]";
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
    public String getAreaNombre() {
        return areaNombre;
    }
    public void setAreaNombre(String areaNombre) {
        this.areaNombre = areaNombre;
    }
    public String getRolNombre() {
        return rolNombre;
    }
    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }
    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    
}
