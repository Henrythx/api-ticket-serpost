package com.ticket.services.interfaces;

import com.ticket.dto.usuario.rol.*;
import java.util.List;

public interface RolService {
    RolResponseDTO crearRol(CreateRolDTO dto);
    RolResponseDTO actualizarRol(UpdateRolDTO dto);
    RolResponseDTO obtenerRolPorId(Long idRol);
    List<RolResponseDTO> listarRoles();
}
