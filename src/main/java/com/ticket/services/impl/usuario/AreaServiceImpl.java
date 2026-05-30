package com.ticket.services.impl.usuario;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.dto.usuario.area.*;
import com.ticket.model.CustomError;
import com.ticket.model.usuario.AreaEntity;
import com.ticket.repositories.usuario.AreaRepository;
import com.ticket.services.interfaces.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public AreaResponseDTO crearArea(CreateAreaDTO dto) {
        AreaEntity entity = new AreaEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setActivo(dto.getActivo());
        return toResponseDTO(areaRepository.save(entity));
    }

    @Override
    public AreaResponseDTO actualizarArea(UpdateAreaDTO dto) {
        AreaEntity entity = areaRepository.findById(dto.getIdArea())
                .orElseThrow(() -> CustomError.notFound("Área no encontrada", "AreaServiceImpl"));
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setActivo(dto.getActivo());
        return toResponseDTO(areaRepository.save(entity));
    }

    @Override
    public AreaResponseDTO obtenerAreaPorId(Long idArea) {
        AreaEntity entity = areaRepository.findById(idArea)
                .orElseThrow(() -> CustomError.notFound("Área no encontrada", "AreaServiceImpl"));
        return toResponseDTO(entity);
    }

    @Override
    public List<AreaResponseDTO> listarAreas() {
        return areaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private AreaResponseDTO toResponseDTO(AreaEntity entity) {
        AreaResponseDTO dto = new AreaResponseDTO();
        dto.setIdArea(entity.getIdArea());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}
