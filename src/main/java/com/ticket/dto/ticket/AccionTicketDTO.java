package com.ticket.dto.ticket;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de entrada para acciones sobre un ticket que llevan un comentario:
 * cierre/resolución y registro de comentarios o eventos en el historial.
 *
 * <p>Campos (snake_case):</p>
 * <ul>
 *   <li>{@code comentario}: nota descriptiva de la acción.</li>
 *   <li>{@code tipo_evento}: opcional; clasifica el evento (ej. {@code COMENTARIO},
 *       {@code CONFORMIDAD}). Si es nulo, el servicio asigna uno por defecto.</li>
 *   <li>{@code id_usuario}: opcional; usuario que realiza la acción. Mientras no
 *       exista JWT, el frontend lo envía explícitamente.</li>
 * </ul>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccionTicketDTO {

    private String comentario;
    private String tipoEvento;
    private Long idUsuario;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
