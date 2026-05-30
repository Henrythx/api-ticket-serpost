package com.ticket.services.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ticket.dto.ticket.TicketResponseDTO;
import com.ticket.model.CustomError;
import com.ticket.services.interfaces.ExportService;

/**
 * Implementación de la exportación de tickets a Excel y PDF.
 *
 * <p>Usa Apache POI para el libro {@code .xlsx} y OpenPDF para el documento PDF.
 * El reporte comparte las mismas columnas en ambos formatos.</p>
 */
@Service
public class ExportServiceImpl implements ExportService {

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String[] COLUMNAS = {
            "N°", "Título", "Estado", "Prioridad", "Área", "Categoría",
            "Solicitante", "Técnico", "Creación", "Vence SLA"
    };

    @Override
    public byte[] exportarTicketsExcel(List<TicketResponseDTO> tickets) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Tickets");

            // Estilo de encabezado.
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row header = sheet.createRow(0);
            for (int i = 0; i < COLUMNAS.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(COLUMNAS[i]);
                cell.setCellStyle(headerStyle);
            }

            int fila = 1;
            for (TicketResponseDTO t : tickets) {
                Row row = sheet.createRow(fila++);
                row.createCell(0).setCellValue(valor(t.getIdTicket()));
                row.createCell(1).setCellValue(nullSafe(t.getTitulo()));
                row.createCell(2).setCellValue(t.getEstado() != null ? t.getEstado().getNombre() : "");
                row.createCell(3).setCellValue(t.getPrioridad() != null ? t.getPrioridad().getNivel() : "");
                row.createCell(4).setCellValue(t.getArea() != null ? t.getArea().getNombre() : "");
                row.createCell(5).setCellValue(t.getCategoria() != null ? t.getCategoria().getNombre() : "");
                row.createCell(6).setCellValue(nombre(t.getUsuarioSolicitante() != null
                        ? t.getUsuarioSolicitante().getNombre() : null,
                        t.getUsuarioSolicitante() != null ? t.getUsuarioSolicitante().getApellido() : null));
                row.createCell(7).setCellValue(nombre(t.getUsuarioTecnico() != null
                        ? t.getUsuarioTecnico().getNombre() : null,
                        t.getUsuarioTecnico() != null ? t.getUsuarioTecnico().getApellido() : null));
                row.createCell(8).setCellValue(fecha(t.getFechaCreacion()));
                row.createCell(9).setCellValue(fecha(t.getSlaVencimiento()));
            }

            for (int i = 0; i < COLUMNAS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception ex) {
            throw CustomError.internalServer("No se pudo generar el Excel", "ExportServiceImpl", ex.getMessage());
        }
    }

    @Override
    public byte[] exportarTicketsPdf(List<TicketResponseDTO> tickets) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate(), 24, 24, 30, 24);
            PdfWriter.getInstance(document, out);
            document.open();

            com.lowagie.text.Font tituloFont =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 16, com.lowagie.text.Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte de Tickets - SERPOST", tituloFont);
            titulo.setSpacingAfter(12f);
            document.add(titulo);

            Paragraph subtitulo = new Paragraph(
                    "Generado: " + LocalDateTime.now().format(FECHA) + " · Total: " + tickets.size());
            subtitulo.setSpacingAfter(10f);
            document.add(subtitulo);

            PdfPTable tabla = new PdfPTable(COLUMNAS.length);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{4, 22, 10, 9, 14, 11, 14, 14, 12, 12});

            com.lowagie.text.Font headFont =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 9, com.lowagie.text.Font.BOLD,
                            java.awt.Color.WHITE);
            for (String col : COLUMNAS) {
                PdfPCell cell = new PdfPCell(new Phrase(col, headFont));
                cell.setBackgroundColor(new java.awt.Color(30, 58, 138));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(4f);
                tabla.addCell(cell);
            }

            com.lowagie.text.Font cellFont =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 8);
            for (TicketResponseDTO t : tickets) {
                celda(tabla, valor(t.getIdTicket()), cellFont);
                celda(tabla, nullSafe(t.getTitulo()), cellFont);
                celda(tabla, t.getEstado() != null ? t.getEstado().getNombre() : "", cellFont);
                celda(tabla, t.getPrioridad() != null ? t.getPrioridad().getNivel() : "", cellFont);
                celda(tabla, t.getArea() != null ? t.getArea().getNombre() : "", cellFont);
                celda(tabla, t.getCategoria() != null ? t.getCategoria().getNombre() : "", cellFont);
                celda(tabla, nombre(t.getUsuarioSolicitante() != null ? t.getUsuarioSolicitante().getNombre() : null,
                        t.getUsuarioSolicitante() != null ? t.getUsuarioSolicitante().getApellido() : null), cellFont);
                celda(tabla, nombre(t.getUsuarioTecnico() != null ? t.getUsuarioTecnico().getNombre() : null,
                        t.getUsuarioTecnico() != null ? t.getUsuarioTecnico().getApellido() : null), cellFont);
                celda(tabla, fecha(t.getFechaCreacion()), cellFont);
                celda(tabla, fecha(t.getSlaVencimiento()), cellFont);
            }

            document.add(tabla);
            document.close();
            return out.toByteArray();
        } catch (Exception ex) {
            throw CustomError.internalServer("No se pudo generar el PDF", "ExportServiceImpl", ex.getMessage());
        }
    }

    // ─────────────────────────── Helpers ────────────────────────────────────

    private void celda(PdfPTable tabla, String texto, com.lowagie.text.Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(3f);
        tabla.addCell(cell);
    }

    private String valor(Long v) {
        return v == null ? "" : String.valueOf(v);
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private String nombre(String nombre, String apellido) {
        String n = (nullSafe(nombre) + " " + nullSafe(apellido)).trim();
        return n.isEmpty() ? "Sin asignar" : n;
    }

    private String fecha(LocalDateTime f) {
        return f == null ? "" : f.format(FECHA);
    }
}
