package com.ticket.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de entrada para el cambio de estado genérico de un ticket
 * ({@code PUT /api/tickets/{id}}).
 *
 * <p>Permite que un técnico o administrador mueva el ticket a un nuevo estado
 * (identificado por su {@code codigo}, ej. {@code RESUELTO}), adjuntando una nota
 * que quedará registrada en el historial. El servicio valida que la transición
 * respete el flujo del ciclo de vida del ticket.</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CambiarEstadoTicketDTO {

    @NotBlank(message = "El código de estado destino es obligatorio")
    private String codigo;

    private String comentario;

    /** Usuario que realiza el cambio (responsable del evento de historial). */
    private Long idUsuario;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
