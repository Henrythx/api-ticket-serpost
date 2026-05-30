package com.ticket.repositories.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.ticket.EstadoTicketEntity;

/**
 * Repositorio JPA del catálogo de estados de ticket.
 *
 * <p>Capa de datos: aísla el acceso a la tabla {@code estado_ticket}, evitando que
 * la lógica de negocio interactúe directamente con la base de datos.</p>
 */
public interface EstadoTicketRepository extends JpaRepository<EstadoTicketEntity, Long> {

    /**
     * Busca un estado por su código lógico (ej. {@code ABIERTO}, {@code EN_ATENCION}).
     *
     * @param codigo código único del estado.
     * @return el estado si existe.
     */
    Optional<EstadoTicketEntity> findByCodigo(String codigo);
}
