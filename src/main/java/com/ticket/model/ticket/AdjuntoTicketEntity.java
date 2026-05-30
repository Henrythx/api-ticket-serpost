package com.ticket.model.ticket;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Archivo adjunto (evidencia) asociado a un ticket.
 *
 * <p>Guarda los metadatos del archivo; el contenido binario se almacena en el
 * sistema de archivos del servidor (ruta en {@code rutaAlmacenamiento}).</p>
 */
@Entity
@Table(name = "adjunto_ticket")
public class AdjuntoTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdjunto;

    @ManyToOne
    @JoinColumn(name = "id_ticket")
    private TicketEntity ticket;

    private String nombreArchivo;
    private String tipoContenido;
    private Long tamanioBytes;
    private String rutaAlmacenamiento;
    private Long idUsuarioSubida;
    private LocalDateTime fechaSubida;

    public AdjuntoTicketEntity() {
    }

    public Long getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Long idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public TicketEntity getTicket() {
        return ticket;
    }

    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
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

    public String getRutaAlmacenamiento() {
        return rutaAlmacenamiento;
    }

    public void setRutaAlmacenamiento(String rutaAlmacenamiento) {
        this.rutaAlmacenamiento = rutaAlmacenamiento;
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
