package com.ticket.dto.ticket.otros;

import com.ticket.model.ticket.PrioridadTicketEntity;

public class PrioridadDTO {
    private Long idPrioridad;
    private String nivel;
    private String colorHex;

    public static PrioridadDTO fromEntity(PrioridadTicketEntity entity) {
        PrioridadDTO dto = new PrioridadDTO();
        dto.setIdPrioridad(entity.getIdPrioridad());
        dto.setNivel(entity.getNivel());
        dto.setColorHex(entity.getColorHex());
        return dto;
    }

    


    @Override
    public String toString() {
        return "PrioridadDTO [idPrioridad=" + idPrioridad + ", nivel=" + nivel + ", colorHex=" + colorHex + "]";
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

    
}
