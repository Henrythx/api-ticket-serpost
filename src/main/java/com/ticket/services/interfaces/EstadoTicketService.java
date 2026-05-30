package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.otros.EstadoTicketDTO;

public interface EstadoTicketService {
    EstadoTicketDTO crearEstado(EstadoTicketDTO dto);
    EstadoTicketDTO actualizarEstado(Long id, EstadoTicketDTO dto);
    EstadoTicketDTO obtenerEstado(Long id);
    List<EstadoTicketDTO> listarEstados();
    void eliminarEstado(Long id);
}
