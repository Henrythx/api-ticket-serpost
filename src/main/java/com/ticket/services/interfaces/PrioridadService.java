package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.otros.PrioridadDTO;

public interface PrioridadService {
    PrioridadDTO crearPrioridad(PrioridadDTO dto);
    PrioridadDTO actualizarPrioridad(Long id, PrioridadDTO dto);
    PrioridadDTO obtenerPrioridad(Long id);
    List<PrioridadDTO> listarPrioridades();
    void eliminarPrioridad(Long id);
}
