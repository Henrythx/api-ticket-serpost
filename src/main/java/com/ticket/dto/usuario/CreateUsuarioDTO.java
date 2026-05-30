package com.ticket.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateUsuarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotNull(message = "El área es obligatoria")
    private Long idArea;

    @NotNull(message = "El rol es obligatorio")
    private Long idRol;
    
    
    
    
    
    @Override
    public String toString() {
        return "CreateUsuarioDTO [nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", password="
                + password + ", idArea=" + idArea + ", idRol=" + idRol + "]";
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
}
