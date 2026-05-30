package com.ticket.model.ticket;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "prioridad_ticket")
public class PrioridadTicketEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrioridad;

    private String nivel;
    private String colorHex;

    @OneToMany(mappedBy = "prioridad")
    private List<TicketEntity> tickets;

    @OneToMany(mappedBy = "prioridad")
    private List<SlaEntity> slas;



    
    public PrioridadTicketEntity() {
    }




    @Override
    public String toString() {
        return "PrioridadTicketEntity [idPrioridad=" + idPrioridad + ", nivel=" + nivel + ", colorHex=" + colorHex
                + "]";
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public List<SlaEntity> getSlas() {
        return slas;
    }

    public void setSlas(List<SlaEntity> slas) {
        this.slas = slas;
    }


    
}