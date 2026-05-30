package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.ticket.CreateTicketDTO;
import com.ticket.dto.ticket.ticket.TicketListDTO;
import com.ticket.dto.ticket.ticket.TicketResponseDTO;
import com.ticket.dto.ticket.ticket.UpdateTicketDTO;

public interface TicketService {
    TicketResponseDTO crearTicket(CreateTicketDTO dto);
    TicketResponseDTO actualizarTicket(Long idTicket, UpdateTicketDTO dto);
    void cambiarEstado(Long idTicket, Long idEstado);
    TicketResponseDTO obtenerTicketPorId(Long idTicket);
    List<TicketListDTO> listarTickets();
    void eliminarTicket(Long idTicket);
}
