package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.otros.SlaDTO;

public interface SlaService {
    SlaDTO crearSla(SlaDTO dto);
    SlaDTO actualizarSla(Long id, SlaDTO dto);
    SlaDTO obtenerSla(Long id);
    List<SlaDTO> listarSla();
    void eliminarSla(Long id);
}

