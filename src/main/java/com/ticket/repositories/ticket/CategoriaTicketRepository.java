package com.ticket.repositories.ticket;

import com.ticket.model.ticket.CategoriaTicketEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaTicketRepository extends JpaRepository<CategoriaTicketEntity, Long> {
    // Ejemplo: buscar categorías activas
    List<CategoriaTicketEntity> findByActivoTrue();
}
