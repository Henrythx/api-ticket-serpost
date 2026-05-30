package com.ticket.repositories.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.notificacion.TipoNotificacionEntity;

/**
 * Repositorio JPA del catálogo de tipos de notificación.
 */
public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacionEntity, Long> {

    /**
     * Busca un tipo de notificación por su código (ej. {@code CAMBIO_ESTADO}).
     *
     * @param codigo código del tipo.
     * @return el tipo si existe.
     */
    Optional<TipoNotificacionEntity> findByCodigo(String codigo);
}
