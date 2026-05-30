package com.ticket.model.ticket;

import java.time.LocalDateTime;
import java.util.List;

import com.ticket.model.notificacion.NotificacionEntity;
import com.ticket.model.usuario.AreaEntity;
import com.ticket.model.usuario.UsuarioEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket")
public class TicketEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @ManyToOne @JoinColumn(name = "id_usuario_solicitante")
    private UsuarioEntity usuarioSolicitante;

    @ManyToOne @JoinColumn(name = "id_usuario_tecnico")
    private UsuarioEntity usuarioTecnico;

    @ManyToOne @JoinColumn(name = "id_area")
    private AreaEntity area;

    @ManyToOne @JoinColumn(name = "id_categoria")
    private CategoriaTicketEntity categoria;

    @ManyToOne @JoinColumn(name = "id_prioridad")
    private PrioridadTicketEntity prioridad;

    @ManyToOne @JoinColumn(name = "id_estado")
    private EstadoTicketEntity estado;

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaAtencion;
    private LocalDateTime fechaResolucion;
    private LocalDateTime slaVencimiento;

    @OneToMany(mappedBy = "ticket")
    private List<NotificacionEntity> notificaciones;

    @OneToMany(mappedBy = "ticket")
    private List<HistorialTicketEntity> historial;




    public TicketEntity() {
    }



    
    @Override
    public String toString() {
        return "TicketEntity [idTicket=" + idTicket + ", usuarioSolicitante=" + usuarioSolicitante + ", usuarioTecnico="
                + usuarioTecnico + ", categoria=" + categoria + ", prioridad=" + prioridad + ", estado=" + estado
                + ", titulo=" + titulo + ", descripcion=" + descripcion + ", fechaCreacion=" + fechaCreacion
                + ", fechaAtencion=" + fechaAtencion + ", fechaResolucion=" + fechaResolucion + ", slaVencimiento="
                + slaVencimiento + "]";
    }




    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public UsuarioEntity getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(UsuarioEntity usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public UsuarioEntity getUsuarioTecnico() {
        return usuarioTecnico;
    }

    public void setUsuarioTecnico(UsuarioEntity usuarioTecnico) {
        this.usuarioTecnico = usuarioTecnico;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
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

    public EstadoTicketEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicketEntity estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDateTime fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDateTime getSlaVencimiento() {
        return slaVencimiento;
    }

    public void setSlaVencimiento(LocalDateTime slaVencimiento) {
        this.slaVencimiento = slaVencimiento;
    }

    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<HistorialTicketEntity> getHistorial() {
        return historial;
    }

    public void setHistorial(List<HistorialTicketEntity> historial) {
        this.historial = historial;
    }




    
}