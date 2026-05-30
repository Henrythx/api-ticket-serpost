package com.ticket.services.interfaces;

import com.ticket.dto.usuario.usuario.*;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(CreateUsuarioDTO dto);
    UsuarioResponseDTO actualizarUsuario(UpdateUsuarioDTO dto);
    void cambiarEstadoUsuario(ChangeEstadoUsuarioDTO dto);
    UsuarioResponseDTO obtenerUsuarioPorId(Long idUsuario);
    List<UsuarioListDTO> listarUsuarios();
    List<UsuarioListDTO> buscarPorArea(Long idArea);
    List<UsuarioListDTO> buscarPorRol(Long idRol);
    List<UsuarioListDTO> buscarPorNombreApellido(String nombre, String apellido);
}

