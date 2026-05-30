package com.ticket.dto.ticket;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de salida del catálogo de prioridades.
 *
 * <p>Se serializa en {@code snake_case} ({@code id_prioridad}, {@code color_hex}).</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PrioridadTicketDTO {

    private Long idPrioridad;
    private String nivel;
    private String colorHex;

    public PrioridadTicketDTO() {
    }

    public PrioridadTicketDTO(Long idPrioridad, String nivel, String colorHex) {
        this.idPrioridad = idPrioridad;
        this.nivel = nivel;
        this.colorHex = colorHex;
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
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
}
