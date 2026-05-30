package com.ticket.services.impl.usuario;

import com.ticket.dto.usuario.rol.*;
import com.ticket.model.usuario.RolEntity;
import com.ticket.repositories.usuario.RolRepository;
import com.ticket.model.CustomError;
import com.ticket.services.interfaces.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;
    
    @Override
    public RolResponseDTO crearRol(CreateRolDTO dto) {
        RolEntity entity = new RolEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        RolEntity saved = rolRepository.save(entity);
        return toResponseDTO(saved);
    }

    @Override
    public RolResponseDTO actualizarRol(UpdateRolDTO dto) {
        RolEntity entity = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> CustomError.notFound("Rol no encontrado", "RolServiceImpl"));
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        RolEntity updated = rolRepository.save(entity);
        return toResponseDTO(updated);
    }

    @Override
    public RolResponseDTO obtenerRolPorId(Long idRol) {
        RolEntity entity = rolRepository.findById(idRol)
                .orElseThrow(() -> CustomError.notFound("Rol no encontrado", "RolServiceImpl"));
        return toResponseDTO(entity);
    }

    @Override
    public List<RolResponseDTO> listarRoles() {
        return rolRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private RolResponseDTO toResponseDTO(RolEntity entity) {
        RolResponseDTO dto = new RolResponseDTO();
        dto.setIdRol(entity.getIdRol());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }
}

