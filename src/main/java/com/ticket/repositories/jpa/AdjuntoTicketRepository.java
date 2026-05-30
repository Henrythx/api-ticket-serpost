package com.ticket.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.ticket.AdjuntoTicketEntity;

/**
 * Repositorio JPA de adjuntos de tickets.
 */
public interface AdjuntoTicketRepository extends JpaRepository<AdjuntoTicketEntity, Long> {

    /**
     * Lista los adjuntos de un ticket, del más reciente al más antiguo.
     *
     * @param idTicket identificador del ticket.
     * @return adjuntos del ticket.
     */
    List<AdjuntoTicketEntity> findByTicket_IdTicketOrderByFechaSubidaDesc(Long idTicket);
}
