package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.CategoriaTicketDTO;
import com.ticket.dto.ticket.EstadoTicketDTO;
import com.ticket.dto.ticket.PrioridadTicketDTO;

/**
 * Servicio de lectura de los catálogos del módulo de tickets.
 *
 * <p>Agrupa el acceso a los catálogos maestros (estados, prioridades y
 * categorías) que alimentan los formularios del frontend y la lógica de negocio
 * del ticket.</p>
 */
public interface CatalogoService {

    /**
     * @return todos los estados de ticket ordenados por su identificador.
     */
    List<EstadoTicketDTO> listarEstados();

    /**
     * @return todas las prioridades de ticket.
     */
    List<PrioridadTicketDTO> listarPrioridades();

    /**
     * @return únicamente las categorías activas (seleccionables al crear un ticket).
     */
    List<CategoriaTicketDTO> listarCategorias();
}
