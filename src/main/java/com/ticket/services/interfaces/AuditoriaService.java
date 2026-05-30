package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.auditoria.AuditoriaResponseDTO;

/**
 * Servicio de auditoría (bitácora de seguridad).
 *
 * <p>Registra de forma transversal y tolerante a fallos los eventos relevantes del
 * sistema, capturando el actor, la acción, la entidad afectada, la IP y la marca de
 * tiempo. Un fallo al auditar nunca debe interrumpir la operación de negocio.</p>
 */
public interface AuditoriaService {

    // Catálogo de acciones auditables.
    String LOGIN = "LOGIN";
    String LOGOUT = "LOGOUT";
    String LOGIN_FALLIDO = "LOGIN_FALLIDO";
    String CREAR_TICKET = "CREAR_TICKET";
    String CAMBIO_ESTADO = "CAMBIO_ESTADO";
    String REASIGNACION = "REASIGNACION";
    String CAMBIO_PRIORIDAD = "CAMBIO_PRIORIDAD";
    String CREAR_USUARIO = "CREAR_USUARIO";
    String MODIFICACION = "MODIFICACION";
    String ELIMINACION = "ELIMINACION";

    /**
     * Registra un evento de auditoría. La IP se obtiene de la petición en curso.
     *
     * @param idUsuario id del actor (puede ser nulo).
     * @param usuario   nombre o correo del actor.
     * @param accion    acción ejecutada (ver constantes).
     * @param entidad   entidad afectada (ej. "Ticket #15").
     * @param detalle   descripción del evento.
     */
    void registrar(Long idUsuario, String usuario, String accion, String entidad, String detalle);

    /**
     * Registra un evento tomando como actor al usuario autenticado en el contexto
     * de seguridad actual (su correo). Útil en operaciones administrativas.
     *
     * @param accion  acción ejecutada.
     * @param entidad entidad afectada.
     * @param detalle descripción del evento.
     */
    void registrarActorActual(String accion, String entidad, String detalle);

    /**
     * Lista los eventos de auditoría más recientes, con filtros opcionales.
     *
     * @param accion    filtro por acción (opcional).
     * @param idUsuario filtro por actor (opcional).
     * @return eventos de auditoría que cumplen los filtros.
     */
    List<AuditoriaResponseDTO> listar(String accion, Long idUsuario);
}
