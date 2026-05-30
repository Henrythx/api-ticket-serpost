package com.ticket.dto.ticket;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de entrada para actualizar los tiempos de una regla de SLA.
 *
 * <p>El frontend edita el campo {@code tiempo_resolucion_horas}; el tiempo de
 * atención es opcional.</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateSlaDTO {

    @NotNull(message = "El tiempo de resolución es obligatorio")
    @Min(value = 1, message = "El tiempo de resolución debe ser de al menos 1 hora")
    private Integer tiempoResolucionHoras;

    @Min(value = 1, message = "El tiempo de atención debe ser de al menos 1 hora")
    private Integer tiempoAtencionHoras;

    public Integer getTiempoResolucionHoras() {
        return tiempoResolucionHoras;
    }

    public void setTiempoResolucionHoras(Integer tiempoResolucionHoras) {
        this.tiempoResolucionHoras = tiempoResolucionHoras;
    }

    public Integer getTiempoAtencionHoras() {
        return tiempoAtencionHoras;
    }

    public void setTiempoAtencionHoras(Integer tiempoAtencionHoras) {
        this.tiempoAtencionHoras = tiempoAtencionHoras;
    }
}
