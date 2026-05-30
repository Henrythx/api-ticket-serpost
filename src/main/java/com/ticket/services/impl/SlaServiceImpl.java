package com.ticket.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ticket.dto.ticket.CreateSlaDTO;
import com.ticket.dto.ticket.SlaResponseDTO;
import com.ticket.dto.ticket.UpdateSlaDTO;
import com.ticket.mapper.TicketMapper;
import com.ticket.model.CustomError;
import com.ticket.model.ticket.CategoriaTicketEntity;
import com.ticket.model.ticket.PrioridadTicketEntity;
import com.ticket.model.ticket.SlaEntity;
import com.ticket.repositories.jpa.CategoriaTicketRepository;
import com.ticket.repositories.jpa.PrioridadTicketRepository;
import com.ticket.repositories.jpa.SlaRepository;
import com.ticket.services.interfaces.SlaService;

import jakarta.transaction.Transactional;

/**
 * Implementación del servicio de gestión de SLA (exclusivo del Administrador).
 */
@Service
public class SlaServiceImpl implements SlaService {

    private final SlaRepository slaRepository;
    private final CategoriaTicketRepository categoriaRepository;
    private final PrioridadTicketRepository prioridadRepository;

    public SlaServiceImpl(SlaRepository slaRepository,
                          CategoriaTicketRepository categoriaRepository,
                          PrioridadTicketRepository prioridadRepository) {
        this.slaRepository = slaRepository;
        this.categoriaRepository = categoriaRepository;
        this.prioridadRepository = prioridadRepository;
    }

    @Override
    public List<SlaResponseDTO> listar() {
        return slaRepository.findAll().stream()
                .map(TicketMapper::toSlaDTO)
                .toList();
    }

    @Override
    @Transactional
    public SlaResponseDTO crear(CreateSlaDTO dto) {
        CategoriaTicketEntity categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> CustomError.badRequest(
                        "La categoría indicada no existe", "SlaServiceImpl", "idCategoria"));
        PrioridadTicketEntity prioridad = prioridadRepository.findById(dto.getIdPrioridad())
                .orElseThrow(() -> CustomError.badRequest(
                        "La prioridad indicada no existe", "SlaServiceImpl", "idPrioridad"));

        // Evitar reglas duplicadas para la misma combinación.
        slaRepository.findByCategoria_IdCategoriaAndPrioridad_IdPrioridad(
                dto.getIdCategoria(), dto.getIdPrioridad()).ifPresent(s -> {
            throw CustomError.conflict("Ya existe una regla de SLA para esa categoría y prioridad",
                    "SlaServiceImpl", "duplicado");
        });

        SlaEntity sla = new SlaEntity();
        sla.setCategoria(categoria);
        sla.setPrioridad(prioridad);
        sla.setTiempoResolucion(dto.getTiempoResolucionHoras());
        sla.setTiempoAtencion(dto.getTiempoAtencionHoras() != null
                ? dto.getTiempoAtencionHoras()
                : Math.max(1, dto.getTiempoResolucionHoras() / 4));
        sla.setActivo(true);
        return TicketMapper.toSlaDTO(slaRepository.save(sla));
    }

    @Override
    @Transactional
    public SlaResponseDTO actualizar(Long id, UpdateSlaDTO dto) {
        SlaEntity sla = buscar(id);
        sla.setTiempoResolucion(dto.getTiempoResolucionHoras());
        if (dto.getTiempoAtencionHoras() != null) {
            sla.setTiempoAtencion(dto.getTiempoAtencionHoras());
        }
        return TicketMapper.toSlaDTO(slaRepository.save(sla));
    }

    @Override
    @Transactional
    public SlaResponseDTO cambiarEstado(Long id, boolean activo) {
        SlaEntity sla = buscar(id);
        sla.setActivo(activo);
        return TicketMapper.toSlaDTO(slaRepository.save(sla));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!slaRepository.existsById(id)) {
            throw CustomError.notFound("Regla de SLA no encontrada", "SlaServiceImpl");
        }
        slaRepository.deleteById(id);
    }

    private SlaEntity buscar(Long id) {
        return slaRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("Regla de SLA no encontrada", "SlaServiceImpl"));
    }
}
