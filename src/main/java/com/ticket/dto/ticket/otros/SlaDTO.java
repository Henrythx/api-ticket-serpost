package com.ticket.dto.ticket.otros;

import com.ticket.model.ticket.SlaEntity;

public class SlaDTO {
    private Long idSla;
    private Long idCategoria;
    private Long idPrioridad;
    private Integer tiempoAtencion;
    private Integer tiempoResolucion;

    public static SlaDTO fromEntity(SlaEntity entity) {
        SlaDTO dto = new SlaDTO();
        dto.setIdSla(entity.getIdSla());
        dto.setIdCategoria(entity.getCategoria() != null ? entity.getCategoria().getIdCategoria() : null);
        dto.setIdPrioridad(entity.getPrioridad() != null ? entity.getPrioridad().getIdPrioridad() : null);
        dto.setTiempoAtencion(entity.getTiempoAtencion());
        dto.setTiempoResolucion(entity.getTiempoResolucion());
        return dto;
    }



    
    @Override
    public String toString() {
        return "SlaDTO [idSla=" + idSla + ", idCategoria=" + idCategoria + ", idPrioridad=" + idPrioridad
                + ", tiempoAtencion=" + tiempoAtencion + ", tiempoResolucion=" + tiempoResolucion + "]";
    }




    public Long getIdSla() {
        return idSla;
    }

    public void setIdSla(Long idSla) {
        this.idSla = idSla;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
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

    
}
