package com.ticket.repositories.ticket;

import com.ticket.model.ticket.PrioridadTicketEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrioridadTicketRepository extends JpaRepository<PrioridadTicketEntity, Long> {
    // Ejemplo: buscar por nivel
    Optional<PrioridadTicketEntity> findByNivel(String nivel);
}
