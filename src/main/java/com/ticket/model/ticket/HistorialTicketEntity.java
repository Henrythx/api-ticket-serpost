package com.ticket.model.ticket;

import java.time.LocalDateTime;

import com.ticket.model.usuario.UsuarioEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_ticket")
public class HistorialTicketEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @ManyToOne @JoinColumn(name = "id_ticket")
    private TicketEntity ticket;

    @ManyToOne @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne @JoinColumn(name = "id_estado_anterior")
    private EstadoTicketEntity estadoAnterior;

    @ManyToOne @JoinColumn(name = "id_estado_nuevo")
    private EstadoTicketEntity estadoNuevo;

    private String tipoEvento;
    private String comentario;
    private LocalDateTime fechaCambio;



    
    public HistorialTicketEntity() {
    }
    


    @Override
    public String toString() {
        return "HistorialTicketEntity [idHistorial=" + idHistorial + ", ticket=" + ticket + ", usuario=" + usuario
                + ", estadoAnterior=" + estadoAnterior + ", estadoNuevo=" + estadoNuevo + ", tipoEvento=" + tipoEvento
                + ", comentario=" + comentario + ", fechaCambio=" + fechaCambio + "]";
    }




    public Long getIdHistorial() {
        return idHistorial;
    }
    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }
    public TicketEntity getTicket() {
        return ticket;
    }
    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
    }
    public UsuarioEntity getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    public EstadoTicketEntity getEstadoAnterior() {
        return estadoAnterior;
    }
    public void setEstadoAnterior(EstadoTicketEntity estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }
    public EstadoTicketEntity getEstadoNuevo() {
        return estadoNuevo;
    }
    public void setEstadoNuevo(EstadoTicketEntity estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
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
    
}