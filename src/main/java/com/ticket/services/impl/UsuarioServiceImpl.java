package com.ticket.services.impl;

import com.ticket.dto.usuario.usuario.*;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.jpa.UsuarioRepository;
import com.ticket.model.CustomError;
import com.ticket.services.interfaces.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDTO crearUsuario(CreateUsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw CustomError.conflict("Email ya registrado", "UsuarioServiceImpl", "Duplicado");
        }
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        // set area y rol con repositorios si corresponde
        UsuarioEntity saved = usuarioRepository.save(entity);
        return toResponseDTO(saved);
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(UpdateUsuarioDTO dto) {
        UsuarioEntity entity = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> CustomError.notFound("Usuario no encontrado", "UsuarioServiceImpl"));
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());
        // actualizar área y rol
        UsuarioEntity updated = usuarioRepository.save(entity);
        return toResponseDTO(updated);
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

    private UsuarioResponseDTO toResponseDTO(UsuarioEntity entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setActivo(entity.getActivo());
        // set areaNombre y rolNombre si corresponde
        return dto;
    }

    private UsuarioListDTO toListDTO(UsuarioEntity entity) {
        UsuarioListDTO dto = new UsuarioListDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}
