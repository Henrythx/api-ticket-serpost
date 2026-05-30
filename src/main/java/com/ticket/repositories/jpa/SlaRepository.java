package com.ticket.repositories.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.ticket.SlaEntity;

/**
 * Repositorio JPA de las reglas de SLA (Acuerdos de Nivel de Servicio).
 *
 * <p>Soporta el motor de reglas que calcula los tiempos de atención y resolución
 * de un ticket en función de su categoría y prioridad.</p>
 */
public interface SlaRepository extends JpaRepository<SlaEntity, Long> {

    /**
     * Busca la regla de SLA aplicable a una combinación categoría + prioridad.
     *
     * @param idCategoria identificador de la categoría.
     * @param idPrioridad identificador de la prioridad.
     * @return la regla de SLA si existe.
     */
    Optional<SlaEntity> findByCategoria_IdCategoriaAndPrioridad_IdPrioridad(Long idCategoria, Long idPrioridad);

    /**
     * Reglas de SLA definidas para una prioridad (respaldo cuando no hay una regla
     * específica de la categoría).
     *
     * @param idPrioridad identificador de la prioridad.
     * @return reglas de SLA de esa prioridad.
     */
    List<SlaEntity> findByPrioridad_IdPrioridad(Long idPrioridad);
}
