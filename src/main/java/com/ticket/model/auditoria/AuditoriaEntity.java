package com.ticket.model.auditoria;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Registro de auditoría (bitácora de seguridad) del sistema.
 *
 * <p>Almacena, de forma inmutable, cada evento relevante: inicios y cierres de
 * sesión, creación de tickets, cambios de estado, reasignaciones, eliminaciones y
 * modificaciones. Guarda una instantánea del actor (id y nombre) para conservar la
 * traza aunque el usuario sea eliminado posteriormente, junto con la dirección IP
 * y la marca de tiempo.</p>
 */
@Entity
@Table(name = "auditoria")
public class AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAuditoria;

    /** Identificador del usuario que ejecutó la acción (puede ser nulo). */
    private Long idUsuario;

    /** Instantánea del nombre/correo del actor. */
    private String usuario;

    /** Acción ejecutada: LOGIN, LOGOUT, CREAR_TICKET, CAMBIO_ESTADO, etc. */
    private String accion;

    /** Entidad afectada (ej. "Ticket #15", "Usuario #4"). */
    private String entidad;

    /** Descripción detallada del evento. */
    @Column(length = 1000)
    private String detalle;

    /** Dirección IP de origen de la petición. */
    private String ip;

    /** Fecha y hora exactas del evento. */
    private LocalDateTime fecha;

    public AuditoriaEntity() {
    }

    public Long getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Long idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
