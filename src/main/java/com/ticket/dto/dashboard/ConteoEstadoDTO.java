package com.ticket.dto.dashboard;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * Conteo de tickets agrupados por estado, usado para el gráfico
 * "Tickets por Estado" del dashboard.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConteoEstadoDTO {

    private String codigo;
    private String nombre;
    private long cantidad;

    public ConteoEstadoDTO() {
    }

    public ConteoEstadoDTO(String codigo, String nombre, long cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
