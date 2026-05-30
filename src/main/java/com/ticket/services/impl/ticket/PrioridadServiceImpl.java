package com.ticket.services.impl.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticket.dto.ticket.otros.PrioridadDTO;
import com.ticket.model.CustomError;
import com.ticket.model.ticket.PrioridadTicketEntity;
import com.ticket.repositories.ticket.PrioridadTicketRepository;
import com.ticket.services.interfaces.PrioridadService;

@Service
public class PrioridadServiceImpl implements PrioridadService{

    @Autowired
    private PrioridadTicketRepository prioridadRepository;

    @Transactional
    @Override
    public PrioridadDTO crearPrioridad(PrioridadDTO dto) {
        PrioridadTicketEntity entity = new PrioridadTicketEntity();
        entity.setNivel(dto.getNivel());
        entity.setColorHex(dto.getColorHex());
        return PrioridadDTO.fromEntity(prioridadRepository.save(entity));
    }

    @Transactional
    @Override
    public PrioridadDTO actualizarPrioridad(Long id, PrioridadDTO dto) {
        PrioridadTicketEntity entity = prioridadRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("Prioridad no encontrada", "PrioridadServiceImpl"));
        entity.setNivel(dto.getNivel());
        entity.setColorHex(dto.getColorHex());
        return PrioridadDTO.fromEntity(prioridadRepository.save(entity));
    }

    @Transactional(readOnly = true)
    @Override
    public PrioridadDTO obtenerPrioridad(Long id) {
        return PrioridadDTO.fromEntity(
                prioridadRepository.findById(id)
                        .orElseThrow(() -> CustomError.notFound("Prioridad no encontrada", "PrioridadServiceImpl"))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrioridadDTO> listarPrioridades() {
        return prioridadRepository.findAll().stream()
                .map(PrioridadDTO::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public void eliminarPrioridad(Long id) {
        PrioridadTicketEntity entity = prioridadRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("Prioridad no encontrada", "PrioridadServiceImpl"));
        prioridadRepository.delete(entity);
    }
}
