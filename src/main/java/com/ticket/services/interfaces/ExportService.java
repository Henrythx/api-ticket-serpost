package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.TicketResponseDTO;

/**
 * Servicio de exportación de tickets a formatos ofimáticos.
 *
 * <p>Genera el contenido binario de un reporte de tickets en Excel (.xlsx) o PDF,
 * a partir de una lista ya filtrada por la capa de negocio.</p>
 */
public interface ExportService {

    /**
     * Genera un libro de Excel con la relación de tickets.
     *
     * @param tickets tickets a incluir.
     * @return contenido del archivo .xlsx.
     */
    byte[] exportarTicketsExcel(List<TicketResponseDTO> tickets);

    /**
     * Genera un documento PDF con la relación de tickets.
     *
     * @param tickets tickets a incluir.
     * @return contenido del archivo .pdf.
     */
    byte[] exportarTicketsPdf(List<TicketResponseDTO> tickets);
}
