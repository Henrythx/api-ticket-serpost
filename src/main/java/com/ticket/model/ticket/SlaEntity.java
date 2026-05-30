package com.ticket.model.ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "sla")
public class SlaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSla;

    @ManyToOne @JoinColumn(name = "id_categoria")
    private CategoriaTicketEntity categoria;

    @ManyToOne @JoinColumn(name = "id_prioridad")
    private PrioridadTicketEntity prioridad;

    private Integer tiempoAtencion;
    private Integer tiempoResolucion;
    private Boolean activo;




    public SlaEntity() {
    }



    
    @Override
    public String toString() {
        return "SlaEntity [idSla=" + idSla + ", categoria=" + categoria + ", prioridad=" + prioridad
                + ", tiempoAtencion=" + tiempoAtencion + ", tiempoResolucion=" + tiempoResolucion + "]";
    }




    public Long getIdSla() {
        return idSla;
    }
    public void setIdSla(Long idSla) {
        this.idSla = idSla;
    }
    public CategoriaTicketEntity getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaTicketEntity categoria) {
        this.categoria = categoria;
    }
    public PrioridadTicketEntity getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(PrioridadTicketEntity prioridad) {
        this.prioridad = prioridad;
    }
    public Integer getTiempoAtencion() {
        return tiempoAtencion;
    }
    public void setTiempoAtencion(Integer tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }
    public Integer getTiempoResolucion() {
        return tiempoResolucion;
    }
    public void setTiempoResolucion(Integer tiempoResolucion) {
        this.tiempoResolucion = tiempoResolucion;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    
}