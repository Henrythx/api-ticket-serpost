package com.ticket.services.interfaces;

import com.ticket.dto.dashboard.DashboardDTO;

/**
 * Servicio analítico del dashboard.
 *
 * <p>Orquesta las consultas de agregación necesarias para construir los paneles de
 * indicadores del jefe de área (KPIs), consolidando los resultados en un único DTO
 * de transferencia, tal como describe el diagrama de secuencia del dashboard.</p>
 */
public interface DashboardService {

    /**
     * Calcula y devuelve el resumen de indicadores de la mesa de ayuda.
     *
     * @return KPIs consolidados.
     */
    DashboardDTO obtenerResumen();
}
