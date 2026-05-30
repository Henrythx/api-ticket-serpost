package com.ticket.services.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ticket.dto.ticket.AdjuntoTicketDTO;

/**
 * Servicio de gestión de adjuntos (evidencias) de tickets.
 */
public interface AdjuntoService {

    /**
     * Resultado de una descarga: nombre original, tipo de contenido y bytes.
     */
    record ArchivoDescarga(String nombre, String tipoContenido, byte[] contenido) {
    }

    /**
     * Almacena un archivo asociado a un ticket.
     *
     * @param idTicket  ticket al que se adjunta.
     * @param archivo   archivo subido.
     * @param idUsuario usuario que sube el archivo.
     * @return metadatos del adjunto creado.
     */
    AdjuntoTicketDTO subir(Long idTicket, MultipartFile archivo, Long idUsuario);

    /**
     * Lista los adjuntos de un ticket.
     *
     * @param idTicket identificador del ticket.
     * @return metadatos de los adjuntos.
     */
    List<AdjuntoTicketDTO> listar(Long idTicket);

    /**
     * Recupera el contenido de un adjunto para su descarga.
     *
     * @param idAdjunto identificador del adjunto.
     * @return nombre, tipo y bytes del archivo.
     */
    ArchivoDescarga descargar(Long idAdjunto);
}
