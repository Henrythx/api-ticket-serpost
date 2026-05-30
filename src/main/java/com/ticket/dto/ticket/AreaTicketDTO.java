package com.ticket.dto.ticket;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * Vista reducida de un área para anidar dentro de un ticket.
 *
 * <p>Se serializa en {@code snake_case} ({@code id_area}).</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AreaTicketDTO {

    private Long idArea;
    private String nombre;

    public AreaTicketDTO() {
    }

    public AreaTicketDTO(Long idArea, String nombre) {
        this.idArea = idArea;
        this.nombre = nombre;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
