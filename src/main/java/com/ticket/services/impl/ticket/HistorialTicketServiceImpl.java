package com.ticket.services.impl.ticket;

import com.ticket.model.ticket.HistorialTicketEntity;
import com.ticket.repositories.ticket.HistorialTicketRepository;
import com.ticket.dto.ticket.historialTicket.HistorialTicketDTO;
import com.ticket.model.CustomError;
import com.ticket.services.interfaces.HistorialTicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialTicketServiceImpl implements HistorialTicketService {

    @Autowired
    private HistorialTicketRepository historialRepository;

    @Transactional(readOnly = true)
    @Override
    public List<HistorialTicketDTO> listarPorTicket(Long idTicket) {
        List<HistorialTicketEntity> historial = historialRepository.findByTicket_IdTicket(idTicket);
        if (historial.isEmpty()) {
            throw CustomError.notFound("No se encontró historial para el ticket", "HistorialTicketServiceImpl");
        }
        return historial.stream()
                .map(HistorialTicketDTO::fromEntity)
                .collect(Collectors.toList());
    }

    
    @Transactional(readOnly = true)
    @Override
    public HistorialTicketDTO obtenerPorId(Long idHistorial) {
        HistorialTicketEntity entity = historialRepository.findById(idHistorial)
                .orElseThrow(() -> CustomError.notFound("Historial no encontrado", "HistorialTicketServiceImpl"));
        return HistorialTicketDTO.fromEntity(entity);
    }

    @Transactional
    @Override
    public void eliminarHistorial(Long idHistorial) {
        HistorialTicketEntity entity = historialRepository.findById(idHistorial)
                .orElseThrow(() -> CustomError.notFound("Historial no encontrado", "HistorialTicketServiceImpl"));
        historialRepository.delete(entity);
    }
}
