package com.ticket.dto.ticket;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de salida de una regla de SLA.
 *
 * <p>Se serializa en {@code snake_case} ({@code id_sla}, {@code id_categoria},
 * {@code tiempo_resolucion_horas}) e incluye las entidades anidadas de categoría y
 * prioridad para que el frontend muestre la tabla de SLA sin llamadas extra.</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SlaResponseDTO {

    private Long idSla;
    private Long idCategoria;
    private Long idPrioridad;
    private Integer tiempoAtencionHoras;
    private Integer tiempoResolucionHoras;
    private Boolean activo;
    private CategoriaTicketDTO categoria;
    private PrioridadTicketDTO prioridad;

    public Long getIdSla() {
        return idSla;
    }

    public void setIdSla(Long idSla) {
        this.idSla = idSla;
    }

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

    public Integer getTiempoAtencionHoras() {
        return tiempoAtencionHoras;
    }

    public void setTiempoAtencionHoras(Integer tiempoAtencionHoras) {
        this.tiempoAtencionHoras = tiempoAtencionHoras;
    }

    public Integer getTiempoResolucionHoras() {
        return tiempoResolucionHoras;
    }

    public void setTiempoResolucionHoras(Integer tiempoResolucionHoras) {
        this.tiempoResolucionHoras = tiempoResolucionHoras;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public CategoriaTicketDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaTicketDTO categoria) {
        this.categoria = categoria;
    }

    public PrioridadTicketDTO getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadTicketDTO prioridad) {
        this.prioridad = prioridad;
    }
}
