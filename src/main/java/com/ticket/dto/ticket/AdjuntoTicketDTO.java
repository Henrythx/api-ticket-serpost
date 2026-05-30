package com.ticket.dto.ticket;

import java.time.LocalDateTime;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de salida con los metadatos de un adjunto (snake_case).
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AdjuntoTicketDTO {

    private Long idAdjunto;
    private Long idTicket;
    private String nombreArchivo;
    private String tipoContenido;
    private Long tamanioBytes;
    private Long idUsuarioSubida;
    private LocalDateTime fechaSubida;

    public Long getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Long idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public Long getTamanioBytes() {
        return tamanioBytes;
    }

    public void setTamanioBytes(Long tamanioBytes) {
        this.tamanioBytes = tamanioBytes;
    }

    public Long getIdUsuarioSubida() {
        return idUsuarioSubida;
    }

    public void setIdUsuarioSubida(Long idUsuarioSubida) {
        this.idUsuarioSubida = idUsuarioSubida;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
}
