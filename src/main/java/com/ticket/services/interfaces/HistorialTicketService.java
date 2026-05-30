package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.historialTicket.HistorialTicketDTO;

public interface HistorialTicketService {
    List<HistorialTicketDTO> listarPorTicket(Long idTicket);
    HistorialTicketDTO obtenerPorId(Long idHistorial);
    void eliminarHistorial(Long idHistorial);
}
