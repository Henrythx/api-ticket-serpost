package com.ticket.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.auditoria.AuditoriaEntity;

/**
 * Repositorio JPA de la bitácora de auditoría.
 */
public interface AuditoriaRepository extends JpaRepository<AuditoriaEntity, Long> {

    /**
     * Devuelve los eventos de auditoría del más reciente al más antiguo.
     *
     * @return eventos ordenados por fecha descendente.
     */
    List<AuditoriaEntity> findTop500ByOrderByFechaDesc();
}
