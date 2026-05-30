package com.ticket.repositories.ticket;

import com.ticket.model.ticket.HistorialTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialTicketRepository extends JpaRepository<HistorialTicketEntity, Long> {
    List<HistorialTicketEntity> findByTicket_IdTicket(Long idTicket);
}

