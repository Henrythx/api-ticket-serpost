package com.ticket.services.impl.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticket.dto.ticket.otros.EstadoTicketDTO;
import com.ticket.model.CustomError;
import com.ticket.model.ticket.EstadoTicketEntity;
import com.ticket.repositories.ticket.EstadoTicketRepository;
import com.ticket.services.interfaces.EstadoTicketService;

@Service
public class EstadoTicketServiceImpl implements EstadoTicketService {

    @Autowired
    private EstadoTicketRepository estadoRepository;

    @Transactional
    @Override
    public EstadoTicketDTO crearEstado(EstadoTicketDTO dto) {
        EstadoTicketEntity entity = new EstadoTicketEntity();
        entity.setCodigo(dto.getCodigo());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEsTerminal(dto.getEsTerminal());
        return EstadoTicketDTO.fromEntity(estadoRepository.save(entity));
    }

    @Transactional
    @Override
    public EstadoTicketDTO actualizarEstado(Long id, EstadoTicketDTO dto) {
        EstadoTicketEntity entity = estadoRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("Estado no encontrado", "EstadoTicketServiceImpl"));
        entity.setCodigo(dto.getCodigo());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEsTerminal(dto.getEsTerminal());
        return EstadoTicketDTO.fromEntity(estadoRepository.save(entity));
    }

    @Transactional(readOnly = true)
    @Override
    public EstadoTicketDTO obtenerEstado(Long id) {
        return EstadoTicketDTO.fromEntity(
                estadoRepository.findById(id)
                        .orElseThrow(() -> CustomError.notFound("Estado no encontrado", "EstadoTicketServiceImpl"))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<EstadoTicketDTO> listarEstados() {
        return estadoRepository.findAll().stream()
                .map(EstadoTicketDTO::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public void eliminarEstado(Long id) {
        EstadoTicketEntity entity = estadoRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("Estado no encontrado", "EstadoTicketServiceImpl"));
        estadoRepository.delete(entity);
    }
}
