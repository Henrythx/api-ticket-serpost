package com.ticket.dto.dashboard;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * Carga de trabajo de un técnico: número de tickets activos asignados.
 * Alimenta el indicador "Carga de trabajo de técnicos" del dashboard.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CargaTecnicoDTO {

    private Long idTecnico;
    private String nombre;
    private long ticketsActivos;

    public CargaTecnicoDTO() {
    }

    public CargaTecnicoDTO(Long idTecnico, String nombre, long ticketsActivos) {
        this.idTecnico = idTecnico;
        this.nombre = nombre;
        this.ticketsActivos = ticketsActivos;
    }

    public Long getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Long idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getTicketsActivos() {
        return ticketsActivos;
    }

    public void setTicketsActivos(long ticketsActivos) {
        this.ticketsActivos = ticketsActivos;
    }
}
