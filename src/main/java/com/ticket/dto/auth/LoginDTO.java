package com.ticket.dto.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para el inicio de sesión.
 *
 * <p>El campo {@code identifier} acepta el correo electrónico o el nombre del
 * usuario, de modo que el formulario de acceso del frontend pueda usar cualquiera
 * de los dos.</p>
 */
public class LoginDTO {

    @NotBlank(message = "El usuario o correo es obligatorio")
    private String identifier;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
