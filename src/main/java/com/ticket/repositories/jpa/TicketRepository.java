package com.ticket.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.ticket.TicketEntity;

/**
 * Repositorio JPA de tickets.
 *
 * <p>Capa de persistencia del agregado principal del sistema. Expone consultas
 * derivadas para los filtros del frontend (por técnico, por solicitante, por
 * estado) y para la asignación automática (carga de trabajo por técnico).</p>
 */
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    /**
     * Tickets asignados a un técnico, ordenados por fecha de creación descendente.
     *
     * @param idTecnico identificador del técnico.
     * @return tickets del técnico.
     */
    List<TicketEntity> findByUsuarioTecnico_IdUsuarioOrderByFechaCreacionDesc(Long idTecnico);

    /**
     * Tickets registrados por un usuario solicitante (sus propios tickets).
     *
     * @param idSolicitante identificador del solicitante.
     * @return tickets del solicitante.
     */
    List<TicketEntity> findByUsuarioSolicitante_IdUsuarioOrderByFechaCreacionDesc(Long idSolicitante);

    /**
     * Tickets filtrados por código de estado.
     *
     * @param codigo código del estado (ej. {@code ABIERTO}).
     * @return tickets en ese estado.
     */
    List<TicketEntity> findByEstado_CodigoOrderByFechaCreacionDesc(String codigo);

    /**
     * Todos los tickets ordenados por fecha de creación descendente.
     *
     * @return todos los tickets.
     */
    List<TicketEntity> findAllByOrderByFechaCreacionDesc();

    /**
     * Cuenta los tickets activos (estado no terminal) de un técnico. Es la métrica
     * que usa la asignación automática para balancear la carga de trabajo.
     *
     * @param idTecnico identificador del técnico.
     * @return número de tickets abiertos/en curso del técnico.
     */
    long countByUsuarioTecnico_IdUsuarioAndEstado_EsTerminalFalse(Long idTecnico);
}
