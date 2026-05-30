package com.ticket.dto.dashboard;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * Conteo de tickets agrupados por prioridad, para el indicador
 * "Tickets por prioridad" del dashboard.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConteoPrioridadDTO {

    private String nivel;
    private String colorHex;
    private long cantidad;

    public ConteoPrioridadDTO() {
    }

    public ConteoPrioridadDTO(String nivel, String colorHex, long cantidad) {
        this.nivel = nivel;
        this.colorHex = colorHex;
        this.cantidad = cantidad;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
}
