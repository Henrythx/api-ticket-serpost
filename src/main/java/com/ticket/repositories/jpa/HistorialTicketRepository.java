package com.ticket.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.ticket.HistorialTicketEntity;

/**
 * Repositorio JPA del historial (bitácora de auditoría) de tickets.
 *
 * <p>Permite registrar y consultar la trazabilidad de cambios de cada ticket, tal
 * como exige el diseño ("Registro de historial de acciones para cada ticket").</p>
 */
public interface HistorialTicketRepository extends JpaRepository<HistorialTicketEntity, Long> {

    /**
     * Devuelve la línea de tiempo de un ticket en orden cronológico ascendente.
     *
     * @param idTicket identificador del ticket.
     * @return eventos del historial del ticket.
     */
    List<HistorialTicketEntity> findByTicket_IdTicketOrderByFechaCambioAsc(Long idTicket);
}
