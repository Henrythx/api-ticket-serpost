package com.ticket.repositories.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.ticket.CategoriaTicketEntity;

/**
 * Repositorio JPA del catálogo de categorías de ticket.
 *
 * <p>Capa de datos sobre la tabla {@code categoria_ticket}.</p>
 */
public interface CategoriaTicketRepository extends JpaRepository<CategoriaTicketEntity, Long> {

    /**
     * Lista únicamente las categorías activas (las que se pueden seleccionar al
     * registrar un ticket).
     *
     * @return categorías con {@code activo = true}.
     */
    List<CategoriaTicketEntity> findByActivoTrue();

    /**
     * Busca una categoría por su nombre exacto.
     *
     * @param nombre nombre de la categoría.
     * @return la categoría si existe.
     */
    Optional<CategoriaTicketEntity> findByNombre(String nombre);
}
