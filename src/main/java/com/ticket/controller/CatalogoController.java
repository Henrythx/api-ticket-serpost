package com.ticket.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.ticket.CategoriaTicketDTO;
import com.ticket.dto.ticket.EstadoTicketDTO;
import com.ticket.dto.ticket.PrioridadTicketDTO;
import com.ticket.services.interfaces.CatalogoService;

/**
 * Controlador REST de catálogos del módulo de tickets.
 *
 * <p>Expone, en modo solo lectura, los catálogos que el frontend necesita para
 * poblar los formularios (crear ticket, filtros, edición de SLA). Rutas efectivas
 * bajo el contexto {@code /api}:</p>
 * <ul>
 *   <li>{@code GET /api/estados}</li>
 *   <li>{@code GET /api/prioridades}</li>
 *   <li>{@code GET /api/categorias}</li>
 * </ul>
 */
@RestController
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    /**
     * Lista todos los estados posibles de un ticket.
     *
     * @return colección de estados.
     */
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoTicketDTO>> listarEstados() {
        return ResponseEntity.ok(catalogoService.listarEstados());
    }

    /**
     * Lista todas las prioridades configuradas.
     *
     * @return colección de prioridades.
     */
    @GetMapping("/prioridades")
    public ResponseEntity<List<PrioridadTicketDTO>> listarPrioridades() {
        return ResponseEntity.ok(catalogoService.listarPrioridades());
    }

    /**
     * Lista las categorías activas disponibles para registrar tickets.
     *
     * @return colección de categorías activas.
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaTicketDTO>> listarCategorias() {
        return ResponseEntity.ok(catalogoService.listarCategorias());
    }
}
