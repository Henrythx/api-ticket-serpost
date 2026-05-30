package com.ticket.dto.ticket;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de salida del catálogo de estados.
 *
 * <p>Se serializa en {@code snake_case} ({@code id_estado}, {@code es_terminal})
 * para coincidir con el contrato consumido por el frontend.</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EstadoTicketDTO {

    private Long idEstado;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Boolean esTerminal;

    public EstadoTicketDTO() {
    }

    public EstadoTicketDTO(Long idEstado, String codigo, String nombre, String descripcion, Boolean esTerminal) {
        this.idEstado = idEstado;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esTerminal = esTerminal;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEsTerminal() {
        return esTerminal;
    }

    public void setEsTerminal(Boolean esTerminal) {
        this.esTerminal = esTerminal;
    }
}
