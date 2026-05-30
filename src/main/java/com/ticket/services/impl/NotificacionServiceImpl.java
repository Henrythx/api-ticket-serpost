package com.ticket.services.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ticket.model.notificacion.NotificacionEntity;
import com.ticket.model.notificacion.TipoNotificacionEntity;
import com.ticket.model.ticket.TicketEntity;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.jpa.NotificacionRepository;
import com.ticket.repositories.jpa.TipoNotificacionRepository;
import com.ticket.services.interfaces.NotificacionService;

/**
 * Implementación del servicio de notificaciones automáticas.
 *
 * <p>Persiste cada notificación en la tabla {@code notificacion} con estado
 * "no enviado". El despacho efectivo por correo (SMTP) se realizará en una fase
 * posterior mediante un proceso asíncrono que lea estos registros pendientes.</p>
 */
@Service
public class NotificacionServiceImpl implements NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    private final NotificacionRepository notificacionRepository;
    private final TipoNotificacionRepository tipoNotificacionRepository;
    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${app.mail.from:no-reply@serpost.pe}")
    private String mailFrom;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository,
                                   TipoNotificacionRepository tipoNotificacionRepository,
                                   ObjectProvider<JavaMailSender> mailSenderProvider) {
        this.notificacionRepository = notificacionRepository;
        this.tipoNotificacionRepository = tipoNotificacionRepository;
        this.mailSenderProvider = mailSenderProvider;
    }

    @Override
    public void notificar(TicketEntity ticket, UsuarioEntity destino, String codigoTipo,
                          String asunto, String cuerpo) {
        // El bloque es tolerante a fallos: notificar nunca debe romper el flujo
        // de negocio principal (creación/actualización del ticket).
        try {
            if (destino == null) {
                return;
            }
            NotificacionEntity n = new NotificacionEntity();
            n.setTicket(ticket);
            n.setUsuarioDestino(destino);
            n.setTipoNotificacion(resolverTipo(codigoTipo));
            n.setEmailDestino(destino.getEmail());
            n.setAsunto(asunto);
            n.setCuerpo(cuerpo);
            n.setEnviado(false);
            n.setFechaCreacion(LocalDateTime.now());
            notificacionRepository.save(n);
            log.info("Notificación '{}' registrada para {} (ticket #{})",
                    codigoTipo, destino.getEmail(),
                    ticket != null ? ticket.getIdTicket() : null);

            // Envío real por SMTP solo si está habilitado y hay un sender configurado.
            enviarCorreo(n);
        } catch (Exception ex) {
            log.warn("No se pudo registrar la notificación '{}': {}", codigoTipo, ex.getMessage());
        }
    }

    /**
     * Intenta enviar la notificación por correo. Es tolerante a fallos: si el envío
     * está deshabilitado o falla, la notificación queda registrada como no enviada
     * (con el motivo del error) y el flujo continúa sin interrupción.
     *
     * @param n notificación ya persistida.
     */
    private void enviarCorreo(NotificacionEntity n) {
        if (!mailEnabled || n.getEmailDestino() == null || n.getEmailDestino().isBlank()) {
            return;
        }
        JavaMailSender sender = mailSenderProvider.getIfAvailable();
        if (sender == null) {
            return;
        }
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(mailFrom);
            msg.setTo(n.getEmailDestino());
            msg.setSubject(n.getAsunto());
            msg.setText(n.getCuerpo());
            sender.send(msg);
            n.setEnviado(true);
            n.setFechaEnvio(LocalDateTime.now());
            notificacionRepository.save(n);
            log.info("Correo enviado a {}", n.getEmailDestino());
        } catch (Exception ex) {
            n.setErrorMensaje(ex.getMessage());
            notificacionRepository.save(n);
            log.warn("Fallo al enviar correo a {}: {}", n.getEmailDestino(), ex.getMessage());
        }
    }

    /**
     * Obtiene el tipo de notificación por código; si no existe en el catálogo lo
     * crea sobre la marcha para no perder la traza del evento.
     *
     * @param codigo código del tipo de notificación.
     * @return entidad de tipo de notificación gestionada.
     */
    private TipoNotificacionEntity resolverTipo(String codigo) {
        return tipoNotificacionRepository.findByCodigo(codigo)
                .orElseGet(() -> {
                    TipoNotificacionEntity t = new TipoNotificacionEntity();
                    t.setCodigo(codigo);
                    t.setDescripcion(codigo);
                    return tipoNotificacionRepository.save(t);
                });
    }
}
