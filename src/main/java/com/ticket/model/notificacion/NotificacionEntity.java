package com.ticket.model.notificacion;

import java.time.LocalDateTime;

import com.ticket.model.ticket.TicketEntity;
import com.ticket.model.usuario.UsuarioEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notificacion")
public class NotificacionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @ManyToOne @JoinColumn(name = "id_ticket")
    private TicketEntity ticket;

    @ManyToOne @JoinColumn(name = "id_usuario_destino")
    private UsuarioEntity usuarioDestino;

    @ManyToOne @JoinColumn(name = "id_tipo_notificacion")
    private TipoNotificacionEntity tipoNotificacion;

    private String emailDestino;
    private String asunto;
    private String cuerpo;
    private Boolean enviado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvio;
    private String errorMensaje;




    public NotificacionEntity() {
    }



    
    public Long getIdNotificacion() {
        return idNotificacion;
    }
    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    public TicketEntity getTicket() {
        return ticket;
    }
    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
    }
    public UsuarioEntity getUsuarioDestino() {
        return usuarioDestino;
    }
    public void setUsuarioDestino(UsuarioEntity usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }
    public TipoNotificacionEntity getTipoNotificacion() {
        return tipoNotificacion;
    }
    public void setTipoNotificacion(TipoNotificacionEntity tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }
    public String getEmailDestino() {
        return emailDestino;
    }
    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }
    public String getAsunto() {
        return asunto;
    }
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    public String getCuerpo() {
        return cuerpo;
    }
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
    public Boolean getEnviado() {
        return enviado;
    }
    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    public String getErrorMensaje() {
        return errorMensaje;
    }
    public void setErrorMensaje(String errorMensaje) {
        this.errorMensaje = errorMensaje;
    }


    
}