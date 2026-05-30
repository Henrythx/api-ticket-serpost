package com.ticket.dto.usuario;

import jakarta.validation.constraints.NotNull;

public class ChangeEstadoUsuarioDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El estado es obligatorio")
    private Boolean activo;

    
    

    @Override
    public String toString() {
        return "ChangeEstadoUsuarioDTO [idUsuario=" + idUsuario + ", activo=" + activo + "]";
    }



    
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
