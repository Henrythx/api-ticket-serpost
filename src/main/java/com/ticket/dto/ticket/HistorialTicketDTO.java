package com.ticket.dto.ticket;

import java.time.LocalDateTime;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de salida de un evento del historial de un ticket.
 *
 * <p>Se serializa en {@code snake_case} ({@code id_historial}, {@code tipo_evento},
 * {@code fecha_cambio}) e incluye el usuario responsable del evento, para que el
 * frontend muestre la línea de tiempo del ticket.</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HistorialTicketDTO {

    private Long idHistorial;
    private Long idTicket;
    private Long idUsuario;
    private String tipoEvento;
    private String comentario;
    private LocalDateTime fechaCambio;
    private String estadoAnterior;
    private String estadoNuevo;
    private UsuarioMiniDTO usuario;

    public Long getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public UsuarioMiniDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioMiniDTO usuario) {
        this.usuario = usuario;
    }
}
