package com.ticket.dto.ticket;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de salida del catálogo de categorías.
 *
 * <p>Se serializa en {@code snake_case} ({@code id_categoria}).</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoriaTicketDTO {

    private Long idCategoria;
    private String nombre;
    private String tipo;
    private Boolean activo;

    public CategoriaTicketDTO() {
    }

    public CategoriaTicketDTO(Long idCategoria, String nombre, String tipo, Boolean activo) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.tipo = tipo;
        this.activo = activo;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
