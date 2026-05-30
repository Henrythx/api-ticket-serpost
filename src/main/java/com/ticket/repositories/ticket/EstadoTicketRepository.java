package com.ticket.repositories.ticket;

import com.ticket.model.ticket.EstadoTicketEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTicketRepository extends JpaRepository<EstadoTicketEntity, Long> {
    // Ejemplo: buscar estados terminales
    List
    <EstadoTicketEntity> findByEsTerminalTrue();
}
