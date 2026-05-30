package com.ticket.dto.ticket;

import java.time.LocalDateTime;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO de salida que representa un ticket completo.
 *
 * <p>Incluye tanto las claves foráneas planas ({@code id_estado},
 * {@code id_categoria}...) como los objetos anidados ({@code estado},
 * {@code categoria}, {@code prioridad}, {@code usuario_solicitante},
 * {@code usuario_tecnico}), de modo que el frontend pueda renderizar el detalle
 * sin llamadas adicionales. Todo el objeto se serializa en {@code snake_case}.</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketResponseDTO {

    private Long idTicket;
    private Long idUsuarioSolicitante;
    private Long idUsuarioTecnico;
    private Long idArea;
    private Long idCategoria;
    private Long idPrioridad;
    private Long idEstado;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaAtencion;
    private LocalDateTime fechaResolucion;
    private LocalDateTime slaVencimiento;

    private UsuarioMiniDTO usuarioSolicitante;
    private UsuarioMiniDTO usuarioTecnico;
    private AreaTicketDTO area;
    private CategoriaTicketDTO categoria;
    private PrioridadTicketDTO prioridad;
    private EstadoTicketDTO estado;

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public Long getIdUsuarioSolicitante() {
        return idUsuarioSolicitante;
    }

    public void setIdUsuarioSolicitante(Long idUsuarioSolicitante) {
        this.idUsuarioSolicitante = idUsuarioSolicitante;
    }

    public Long getIdUsuarioTecnico() {
        return idUsuarioTecnico;
    }

    public void setIdUsuarioTecnico(Long idUsuarioTecnico) {
        this.idUsuarioTecnico = idUsuarioTecnico;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDateTime fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDateTime getSlaVencimiento() {
        return slaVencimiento;
    }

    public void setSlaVencimiento(LocalDateTime slaVencimiento) {
        this.slaVencimiento = slaVencimiento;
    }

    public UsuarioMiniDTO getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(UsuarioMiniDTO usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public UsuarioMiniDTO getUsuarioTecnico() {
        return usuarioTecnico;
    }

    public void setUsuarioTecnico(UsuarioMiniDTO usuarioTecnico) {
        this.usuarioTecnico = usuarioTecnico;
    }

    public AreaTicketDTO getArea() {
        return area;
    }

    public void setArea(AreaTicketDTO area) {
        this.area = area;
    }

    public CategoriaTicketDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaTicketDTO categoria) {
        this.categoria = categoria;
    }

    public PrioridadTicketDTO getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadTicketDTO prioridad) {
        this.prioridad = prioridad;
    }

    public EstadoTicketDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicketDTO estado) {
        this.estado = estado;
    }
}
