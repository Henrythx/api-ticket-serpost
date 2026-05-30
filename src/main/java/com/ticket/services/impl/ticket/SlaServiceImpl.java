package com.ticket.services.impl.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticket.dto.ticket.otros.SlaDTO;
import com.ticket.model.CustomError;
import com.ticket.model.ticket.SlaEntity;
import com.ticket.repositories.ticket.SlaRepository;
import com.ticket.services.interfaces.SlaService;

@Service
public class SlaServiceImpl implements SlaService {

    @Autowired
    private SlaRepository slaRepository;

    @Transactional
    @Override
    public SlaDTO crearSla(SlaDTO dto) {
        SlaEntity entity = new SlaEntity();
        entity.setTiempoAtencion(dto.getTiempoAtencion());
        entity.setTiempoResolucion(dto.getTiempoResolucion());
        return SlaDTO.fromEntity(slaRepository.save(entity));
    }

    @Transactional
    @Override
    public SlaDTO actualizarSla(Long id, SlaDTO dto) {
        SlaEntity entity = slaRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("SLA no encontrado", "SlaServiceImpl"));
        entity.setTiempoAtencion(dto.getTiempoAtencion());
        entity.setTiempoResolucion(dto.getTiempoResolucion());
        return SlaDTO.fromEntity(slaRepository.save(entity));
    }

    @Transactional(readOnly = true)
    @Override
    public SlaDTO obtenerSla(Long id) {
        return SlaDTO.fromEntity(
                slaRepository.findById(id)
                        .orElseThrow(() -> CustomError.notFound("SLA no encontrado", "SlaServiceImpl"))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<SlaDTO> listarSla() {
        return slaRepository.findAll().stream()
                .map(SlaDTO::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public void eliminarSla(Long id) {
        SlaEntity entity = slaRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("SLA no encontrado", "SlaServiceImpl"));
        slaRepository.delete(entity);
    }
}
