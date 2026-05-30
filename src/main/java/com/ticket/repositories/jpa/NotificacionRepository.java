package com.ticket.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.notificacion.NotificacionEntity;

/**
 * Repositorio JPA de notificaciones.
 *
 * <p>Persiste las alertas generadas automáticamente ante eventos del ticket
 * (creación, cambio de estado, cierre), que constituyen el registro del servicio
 * de notificaciones del sistema.</p>
 */
public interface NotificacionRepository extends JpaRepository<NotificacionEntity, Long> {

    /**
     * Notificaciones dirigidas a un usuario, de la más reciente a la más antigua.
     *
     * @param idUsuario identificador del usuario destino.
     * @return notificaciones del usuario.
     */
    List<NotificacionEntity> findByUsuarioDestino_IdUsuarioOrderByFechaCreacionDesc(Long idUsuario);
}
