package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.usuario.area.*;

public interface AreaService {
    AreaResponseDTO crearArea(CreateAreaDTO dto);
    AreaResponseDTO actualizarArea(UpdateAreaDTO dto);
    AreaResponseDTO obtenerAreaPorId(Long idArea);
    List
    <AreaResponseDTO> listarAreas();
}
