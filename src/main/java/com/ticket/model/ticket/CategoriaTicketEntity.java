package com.ticket.model.ticket;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "categoria_ticket")
public class CategoriaTicketEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    private String nombre;
    private String tipo;
    private Boolean activo;

    @OneToMany(mappedBy = "categoria")
    private List<TicketEntity> tickets;

    @OneToMany(mappedBy = "categoria")
    private List<SlaEntity> slas;


    
    
    public CategoriaTicketEntity() {
    }




    @Override
    public String toString() {
        return "CategoriaTicketEntity [idCategoria=" + idCategoria + ", nombre=" + nombre + ", tipo=" + tipo
                + ", activo=" + activo + "]";
    }




    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
