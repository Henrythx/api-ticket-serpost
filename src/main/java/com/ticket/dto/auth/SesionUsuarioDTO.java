package com.ticket.dto.auth;

/**
 * DTO de salida que representa la sesión de un usuario autenticado.
 *
 * <p>Su forma (camelCase: {@code id}, {@code idRol}, {@code nombreRol},
 * {@code idArea}) coincide con la interfaz {@code UsuarioSesion} consumida por el
 * frontend, que la persiste para resolver permisos por rol.</p>
 *
 * <p>Nota de seguridad: nunca incluye la contraseña ni datos sensibles
 * innecesarios, en línea con el principio de Confidencialidad del diseño.</p>
 */
public class SesionUsuarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Long idRol;
    private String nombreRol;
    private Long idArea;
    private Boolean activo;
    /** Token JWT firmado que el cliente debe enviar en las siguientes peticiones. */
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
