package com.ticket.dto.ticket;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de entrada para crear una regla de SLA.
 *
 * <p>Define el compromiso de tiempos (en horas) para una combinación de categoría
 * y prioridad.</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateSlaDTO {

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;

    @NotNull(message = "La prioridad es obligatoria")
    private Long idPrioridad;

    @NotNull(message = "El tiempo de resolución es obligatorio")
    @Min(value = 1, message = "El tiempo de resolución debe ser de al menos 1 hora")
    private Integer tiempoResolucionHoras;

    @Min(value = 1, message = "El tiempo de atención debe ser de al menos 1 hora")
    private Integer tiempoAtencionHoras;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

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
