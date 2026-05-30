package com.ticket.services.impl;

import com.ticket.dto.usuario.usuario.*;
import com.ticket.model.usuario.AreaEntity;
import com.ticket.model.usuario.RolEntity;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.jpa.AreaRepository;
import com.ticket.repositories.jpa.RolRepository;
import com.ticket.repositories.jpa.UsuarioRepository;
import com.ticket.model.CustomError;
import com.ticket.services.interfaces.UsuarioService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    @Transactional
    public UsuarioResponseDTO crearUsuario(CreateUsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw CustomError.conflict("Email ya registrado", "UsuarioServiceImpl", "Duplicado");
        }
        
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setActivo(true);
        entity.setCreadoEn(LocalDateTime.now()); // Corregido: Se asigna ANTES de guardar

        this.asignarRelaciones(entity, dto.getIdArea(), dto.getIdRol());

        return toResponseDTO(usuarioRepository.save(entity));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(UpdateUsuarioDTO dto) {
        UsuarioEntity entity = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> CustomError.notFound("Usuario no encontrado", "UsuarioServiceImpl"));
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());
        
        this.asignarRelaciones(entity, dto.getIdArea(), dto.getIdRol());
        
        return toResponseDTO(usuarioRepository.save(entity));
    }










    @Override
    public void cambiarEstadoUsuario(ChangeEstadoUsuarioDTO dto) {
        UsuarioEntity entity = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> CustomError.notFound("Usuario no encontrado", "UsuarioServiceImpl"));
        entity.setActivo(dto.getActivo());
        usuarioRepository.save(entity);
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Long idUsuario) {
        UsuarioEntity entity = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> CustomError.notFound("Usuario no encontrado", "UsuarioServiceImpl"));
        return toResponseDTO(entity);
    }









    @Override
    public List<UsuarioListDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioListDTO> buscarPorArea(Long idArea) {
        return usuarioRepository.findByArea_IdArea(idArea).stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioListDTO> buscarPorRol(Long idRol) {
        return usuarioRepository.findByRol_IdRol(idRol).stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioListDTO> buscarPorNombreApellido(String nombre, String apellido) {
        return usuarioRepository.findByNombreContainingOrApellidoContaining(nombre, apellido).stream()
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }








    // Método privado auxiliar para evitar duplicar código de asignación
    private void asignarRelaciones(UsuarioEntity entity, Long idArea, Long idRol) {
        if (idArea != null && idArea > 0) {
            AreaEntity area = areaRepository.findById(idArea).orElse(null); // Corregido orElseGet
            entity.setArea(area);
        }
        if (idRol != null && idRol > 0) {
            RolEntity rol = rolRepository.findById(idRol).orElse(null); // Corregido orElseGet
            entity.setRol(rol);
        }
    }

    private UsuarioResponseDTO toResponseDTO(UsuarioEntity entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setActivo(entity.getActivo());
        dto.setCreadoEn(entity.getCreadoEn());

        if(entity.getArea() != null) dto.setAreaNombre(entity.getArea().getNombre());
        if(entity.getRol() != null) dto.setRolNombre(entity.getRol().getNombre());
        return dto;
    }

    private UsuarioListDTO toListDTO(UsuarioEntity entity) {
        UsuarioListDTO dto = new UsuarioListDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setActivo(entity.getActivo());

        if(entity.getArea() != null) dto.setAreaNombre(entity.getArea().getNombre());
        if(entity.getRol() != null) dto.setRolNombre(entity.getRol().getNombre());

        return dto;
    }
}
