package com.ticket.model.usuario;

import java.time.LocalDateTime;
import java.util.List;

import com.ticket.model.notificacion.NotificacionEntity;
import com.ticket.model.ticket.HistorialTicketEntity;
import com.ticket.model.ticket.TicketEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "usuario")
public class UsuarioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @ManyToOne @JoinColumn(name = "id_area")
    private AreaEntity area;

    @ManyToOne @JoinColumn(name = "id_rol")
    private RolEntity rol;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Boolean activo;
    private LocalDateTime creadoEn;
    private LocalDateTime ultimoAcceso;

    @OneToMany(mappedBy = "usuarioSolicitante")
    private List<TicketEntity> ticketsSolicitados;

    @OneToMany(mappedBy = "usuarioTecnico")
    private List<TicketEntity> ticketsAtendidos;

    @OneToMany(mappedBy = "usuario")
    private List<HistorialTicketEntity> historialTickets;

    @OneToMany(mappedBy = "usuarioDestino")
    private List<NotificacionEntity> notificaciones;



    
    public UsuarioEntity() {
    }




    @Override
    public String toString() {
        return "UsuarioEntity [idUsuario=" + idUsuario + ", area=" + area + ", rol=" + rol + ", nombre=" + nombre
                + ", apellido=" + apellido + ", email=" + email + ", password=" + password + ", activo=" + activo
                + ", creadoEn=" + creadoEn + ", ultimoAcceso=" + ultimoAcceso + "]";
    }




    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public RolEntity getRol() {
        return rol;
    }

    public void setRol(RolEntity rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public List<TicketEntity> getTicketsSolicitados() {
        return ticketsSolicitados;
    }

    public void setTicketsSolicitados(List<TicketEntity> ticketsSolicitados) {
        this.ticketsSolicitados = ticketsSolicitados;
    }

    public List<TicketEntity> getTicketsAtendidos() {
        return ticketsAtendidos;
    }

    public void setTicketsAtendidos(List<TicketEntity> ticketsAtendidos) {
        this.ticketsAtendidos = ticketsAtendidos;
    }

    public List<HistorialTicketEntity> getHistorialTickets() {
        return historialTickets;
    }

    public void setHistorialTickets(List<HistorialTicketEntity> historialTickets) {
        this.historialTickets = historialTickets;
    }

    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }



    
}