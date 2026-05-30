package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.otros.CategoriaTicketDTO;

public interface CategoriaTicketService {
    CategoriaTicketDTO crearCategoria(CategoriaTicketDTO dto);
    CategoriaTicketDTO actualizarCategoria(Long id, CategoriaTicketDTO dto);
    CategoriaTicketDTO obtenerCategoria(Long id);
    List<CategoriaTicketDTO> listarCategorias();
    void eliminarCategoria(Long id);
}

