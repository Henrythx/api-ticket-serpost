package com.ticket.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de entrada para el registro de un ticket por parte del usuario solicitante.
 *
 * <p>Se deserializa desde JSON en {@code snake_case} ({@code id_usuario_solicitante},
 * {@code id_categoria}...), tal como lo envía el frontend. La prioridad es opcional:
 * si no se indica, el motor de reglas la deduce/usa el valor por defecto.</p>
 *
 * <p>Las validaciones implementan la regla de negocio "verificar que los campos
 * obligatorios del ticket estén completos".</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateTicketDTO {

    @NotNull(message = "El usuario solicitante es obligatorio")
    private Long idUsuarioSolicitante;

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;

    /** Área a la que pertenece el requerimiento (opcional: si es nula se toma la
     *  del usuario solicitante). */
    private Long idArea;

    /** Prioridad opcional; si es nula se asigna una prioridad por defecto. */
    private Long idPrioridad;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede exceder 150 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    public Long getIdUsuarioSolicitante() {
        return idUsuarioSolicitante;
    }

    public void setIdUsuarioSolicitante(Long idUsuarioSolicitante) {
        this.idUsuarioSolicitante = idUsuarioSolicitante;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
