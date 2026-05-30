package com.ticket.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ticket.dto.ticket.CategoriaTicketDTO;
import com.ticket.dto.ticket.EstadoTicketDTO;
import com.ticket.dto.ticket.PrioridadTicketDTO;
import com.ticket.mapper.TicketMapper;
import com.ticket.repositories.jpa.CategoriaTicketRepository;
import com.ticket.repositories.jpa.EstadoTicketRepository;
import com.ticket.repositories.jpa.PrioridadTicketRepository;
import com.ticket.services.interfaces.CatalogoService;

/**
 * Implementación del servicio de catálogos.
 *
 * <p>Recupera los catálogos maestros desde la capa de datos y los transforma a
 * DTOs mediante {@link TicketMapper}.</p>
 */
@Service
public class CatalogoServiceImpl implements CatalogoService {

    private final EstadoTicketRepository estadoRepository;
    private final PrioridadTicketRepository prioridadRepository;
    private final CategoriaTicketRepository categoriaRepository;

    public CatalogoServiceImpl(EstadoTicketRepository estadoRepository,
                               PrioridadTicketRepository prioridadRepository,
                               CategoriaTicketRepository categoriaRepository) {
        this.estadoRepository = estadoRepository;
        this.prioridadRepository = prioridadRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<EstadoTicketDTO> listarEstados() {
        return estadoRepository.findAll().stream()
                .map(TicketMapper::toEstadoDTO)
                .toList();
    }

    @Override
    public List<PrioridadTicketDTO> listarPrioridades() {
        return prioridadRepository.findAll().stream()
                .map(TicketMapper::toPrioridadDTO)
                .toList();
    }

    @Override
    public List<CategoriaTicketDTO> listarCategorias() {
        return categoriaRepository.findByActivoTrue().stream()
                .map(TicketMapper::toCategoriaDTO)
                .toList();
    }
}
