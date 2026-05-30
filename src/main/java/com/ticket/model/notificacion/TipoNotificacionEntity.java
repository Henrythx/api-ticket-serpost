package com.ticket.model.notificacion;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "tipo_notificacion")
public class TipoNotificacionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoNotificacion;

    private String codigo;
    private String descripcion;

    @OneToMany(mappedBy = "tipoNotificacion")
    private List<NotificacionEntity> notificaciones;



    
    public TipoNotificacionEntity() {
    }




    public Long getIdTipoNotificacion() {
        return idTipoNotificacion;
    }

    public void setIdTipoNotificacion(Long idTipoNotificacion) {
        this.idTipoNotificacion = idTipoNotificacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<NotificacionEntity> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionEntity> notificaciones) {
        this.notificaciones = notificaciones;
    }

    
}
