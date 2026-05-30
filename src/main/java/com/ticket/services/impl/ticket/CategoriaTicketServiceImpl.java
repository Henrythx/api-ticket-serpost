package com.ticket.services.impl.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticket.dto.ticket.otros.CategoriaTicketDTO;
import com.ticket.model.CustomError;
import com.ticket.model.ticket.CategoriaTicketEntity;
import com.ticket.repositories.ticket.CategoriaTicketRepository;
import com.ticket.services.interfaces.CategoriaTicketService;

@Service
public class CategoriaTicketServiceImpl implements CategoriaTicketService {

    @Autowired
    private CategoriaTicketRepository categoriaRepository;

    @Transactional
    @Override
    public CategoriaTicketDTO crearCategoria(CategoriaTicketDTO dto) {
        CategoriaTicketEntity entity = new CategoriaTicketEntity();
        entity.setNombre(dto.getNombre());
        entity.setTipo(dto.getTipo());
        entity.setActivo(dto.getActivo());
        return CategoriaTicketDTO.fromEntity(categoriaRepository.save(entity));
    }

    @Transactional
    @Override
    public CategoriaTicketDTO actualizarCategoria(Long id, CategoriaTicketDTO dto) {
        CategoriaTicketEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("Categoría no encontrada", "CategoriaTicketServiceImpl"));
        entity.setNombre(dto.getNombre());
        entity.setTipo(dto.getTipo());
        entity.setActivo(dto.getActivo());
        return CategoriaTicketDTO.fromEntity(categoriaRepository.save(entity));
    }

    @Transactional(readOnly = true)
    @Override
    public CategoriaTicketDTO obtenerCategoria(Long id) {
        return CategoriaTicketDTO.fromEntity(
                categoriaRepository.findById(id)
                        .orElseThrow(() -> CustomError.notFound("Categoría no encontrada", "CategoriaTicketServiceImpl"))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaTicketDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaTicketDTO::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public void eliminarCategoria(Long id) {
        CategoriaTicketEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> CustomError.notFound("Categoría no encontrada", "CategoriaTicketServiceImpl"));
        categoriaRepository.delete(entity);
    }
}

