package com.ticket.model.ticket;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "estado_ticket")
public class EstadoTicketEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    private String codigo;
    private String nombre;
    private String descripcion;
    private Boolean esTerminal;

    @OneToMany(mappedBy = "estado")
    private List<TicketEntity> tickets;

    @OneToMany(mappedBy = "estadoAnterior")
    private List<HistorialTicketEntity> historialAnterior;

    @OneToMany(mappedBy = "estadoNuevo")
    private List<HistorialTicketEntity> historialNuevo;



    
    public EstadoTicketEntity() {
    }




    @Override
    public String toString() {
        return "EstadoTicketEntity [idEstado=" + idEstado + ", codigo=" + codigo + ", nombre=" + nombre
                + ", descripcion=" + descripcion + ", esTerminal=" + esTerminal + "]";
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

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public List<HistorialTicketEntity> getHistorialAnterior() {
        return historialAnterior;
    }

    public void setHistorialAnterior(List<HistorialTicketEntity> historialAnterior) {
        this.historialAnterior = historialAnterior;
    }

    public List<HistorialTicketEntity> getHistorialNuevo() {
        return historialNuevo;
    }

    public void setHistorialNuevo(List<HistorialTicketEntity> historialNuevo) {
        this.historialNuevo = historialNuevo;
    }
    
}