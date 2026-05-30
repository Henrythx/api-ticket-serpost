package com.ticket.services.interfaces;

import com.ticket.model.ticket.TicketEntity;
import com.ticket.model.usuario.UsuarioEntity;

/**
 * Servicio de notificaciones automáticas.
 *
 * <p>Genera y persiste alertas ante eventos del ticket (creación, cambio de
 * estado, cierre). Está diseñado para funcionar de forma desacoplada del flujo
 * principal: un fallo al notificar nunca debe interrumpir la operación de negocio.</p>
 */
public interface NotificacionService {

    /**
     * Registra una notificación dirigida al usuario indicado.
     *
     * <p>En esta fase la notificación se persiste con {@code enviado = false} (el
     * envío real por correo es una integración posterior). La operación es
     * tolerante a fallos: cualquier excepción se captura internamente.</p>
     *
     * @param ticket      ticket relacionado con el evento.
     * @param destino     usuario que debe recibir la notificación.
     * @param codigoTipo  código del tipo de notificación (ej. {@code CAMBIO_ESTADO}).
     * @param asunto      asunto de la notificación.
     * @param cuerpo      cuerpo/mensaje de la notificación.
     */
    void notificar(TicketEntity ticket, UsuarioEntity destino, String codigoTipo, String asunto, String cuerpo);
}
