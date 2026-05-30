package com.ticket.dto.ticket;

import java.time.LocalDateTime;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * Vista reducida de un usuario para anidar dentro de un ticket o su historial.
 *
 * <p>Se serializa en {@code snake_case} ({@code id_usuario}, {@code id_area}...) y,
 * por seguridad, nunca incluye la contraseña.</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UsuarioMiniDTO {

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private Long idArea;
    private Long idRol;
    private Boolean activo;
    private LocalDateTime creadoEn;
    private LocalDateTime ultimoAcceso;

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

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
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
