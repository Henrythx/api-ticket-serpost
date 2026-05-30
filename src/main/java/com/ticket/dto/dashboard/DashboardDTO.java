package com.ticket.dto.dashboard;

import java.util.List;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * DTO consolidado de indicadores (KPIs) de la mesa de ayuda.
 *
 * <p>Reúne, en una única estructura optimizada de transferencia, las métricas del
 * dashboard administrativo: conteo por estado del ciclo de vida, tickets por
 * prioridad, vencidos, cumplimiento de SLA, tiempos promedio, carga de técnicos y
 * los rankings de técnicos (más atendidos) y clientes (más solicitudes).</p>
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DashboardDTO {

    private long total;
    private long abiertos;
    private long asignados;
    private long enProceso;
    private long pendientes;
    private long resueltos;
    private long cerrados;
    private long vencidos;

    /** Porcentaje de tickets resueltos dentro del plazo de SLA (0-100). */
    private double cumplimientoSlaPorcentaje;
    /** Tasa de resolución: terminados sobre el total (0-100). */
    private double tasaResolucionPorcentaje;
    private double tiempoPromedioAtencionHoras;
    private double tiempoPromedioResolucionHoras;

    private List<ConteoEstadoDTO> porEstado;
    private List<ConteoPrioridadDTO> porPrioridad;
    private List<CargaTecnicoDTO> cargaTecnicos;
    private List<RankingUsuarioDTO> topTecnicos;
    private List<RankingUsuarioDTO> topClientes;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getAbiertos() {
        return abiertos;
    }

    public void setAbiertos(long abiertos) {
        this.abiertos = abiertos;
    }

    public long getAsignados() {
        return asignados;
    }

    public void setAsignados(long asignados) {
        this.asignados = asignados;
    }

    public long getEnProceso() {
        return enProceso;
    }

    public void setEnProceso(long enProceso) {
        this.enProceso = enProceso;
    }

    public long getPendientes() {
        return pendientes;
    }

    public void setPendientes(long pendientes) {
        this.pendientes = pendientes;
    }

    public long getResueltos() {
        return resueltos;
    }

    public void setResueltos(long resueltos) {
        this.resueltos = resueltos;
    }

    public long getCerrados() {
        return cerrados;
    }

    public void setCerrados(long cerrados) {
        this.cerrados = cerrados;
    }

    public long getVencidos() {
        return vencidos;
    }

    public void setVencidos(long vencidos) {
        this.vencidos = vencidos;
    }

    public double getCumplimientoSlaPorcentaje() {
        return cumplimientoSlaPorcentaje;
    }

    public void setCumplimientoSlaPorcentaje(double cumplimientoSlaPorcentaje) {
        this.cumplimientoSlaPorcentaje = cumplimientoSlaPorcentaje;
    }

    public double getTasaResolucionPorcentaje() {
        return tasaResolucionPorcentaje;
    }

    public void setTasaResolucionPorcentaje(double tasaResolucionPorcentaje) {
        this.tasaResolucionPorcentaje = tasaResolucionPorcentaje;
    }

    public double getTiempoPromedioAtencionHoras() {
        return tiempoPromedioAtencionHoras;
    }

    public void setTiempoPromedioAtencionHoras(double tiempoPromedioAtencionHoras) {
        this.tiempoPromedioAtencionHoras = tiempoPromedioAtencionHoras;
    }

    public double getTiempoPromedioResolucionHoras() {
        return tiempoPromedioResolucionHoras;
    }

    public void setTiempoPromedioResolucionHoras(double tiempoPromedioResolucionHoras) {
        this.tiempoPromedioResolucionHoras = tiempoPromedioResolucionHoras;
    }

    public List<ConteoEstadoDTO> getPorEstado() {
        return porEstado;
    }

    public void setPorEstado(List<ConteoEstadoDTO> porEstado) {
        this.porEstado = porEstado;
    }

    public List<ConteoPrioridadDTO> getPorPrioridad() {
        return porPrioridad;
    }

    public void setPorPrioridad(List<ConteoPrioridadDTO> porPrioridad) {
        this.porPrioridad = porPrioridad;
    }

    public List<CargaTecnicoDTO> getCargaTecnicos() {
        return cargaTecnicos;
    }

    public void setCargaTecnicos(List<CargaTecnicoDTO> cargaTecnicos) {
        this.cargaTecnicos = cargaTecnicos;
    }

    public List<RankingUsuarioDTO> getTopTecnicos() {
        return topTecnicos;
    }

    public void setTopTecnicos(List<RankingUsuarioDTO> topTecnicos) {
        this.topTecnicos = topTecnicos;
    }

    public List<RankingUsuarioDTO> getTopClientes() {
        return topClientes;
    }

    public void setTopClientes(List<RankingUsuarioDTO> topClientes) {
        this.topClientes = topClientes;
    }
}
