package com.ticket.repositories.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.ticket.PrioridadTicketEntity;

/**
 * Repositorio JPA del catálogo de prioridades de ticket.
 *
 * <p>Capa de datos sobre la tabla {@code prioridad_ticket}.</p>
 */
public interface PrioridadTicketRepository extends JpaRepository<PrioridadTicketEntity, Long> {

    /**
     * Busca una prioridad por su nivel (ej. {@code CRÍTICA}, {@code ALTA}).
     *
     * @param nivel nombre del nivel de prioridad.
     * @return la prioridad si existe.
     */
    Optional<PrioridadTicketEntity> findByNivel(String nivel);
}
