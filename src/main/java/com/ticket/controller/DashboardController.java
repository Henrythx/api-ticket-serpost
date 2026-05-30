package com.ticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.dashboard.DashboardDTO;
import com.ticket.services.interfaces.DashboardService;

/**
 * Controlador REST del dashboard de indicadores.
 *
 * <p>Expone {@code GET /api/dashboard}, que devuelve el conjunto consolidado de
 * KPIs para la visualización del estado general del sistema (uso previsto: panel
 * del administrador / jefe de área).</p>
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Devuelve los indicadores consolidados de la mesa de ayuda.
     *
     * @return KPIs del sistema.
     */
    @GetMapping
    public ResponseEntity<DashboardDTO> resumen() {
        return ResponseEntity.ok(dashboardService.obtenerResumen());
    }
}
