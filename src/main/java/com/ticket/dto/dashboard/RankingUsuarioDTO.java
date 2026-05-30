package com.ticket.dto.dashboard;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * Entrada de un ranking de usuarios por número de tickets. Se reutiliza para los
 * indicadores "Técnicos con más incidencias atendidas" y "Clientes con más
 * solicitudes".
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RankingUsuarioDTO {

    private Long idUsuario;
    private String nombre;
    private long cantidad;

    public RankingUsuarioDTO() {
    }

    public RankingUsuarioDTO(Long idUsuario, String nombre, long cantidad) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.cantidad = cantidad;
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

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
}
