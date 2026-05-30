package com.ticket.services.interfaces;

import java.util.List;

import com.ticket.dto.ticket.CreateSlaDTO;
import com.ticket.dto.ticket.SlaResponseDTO;
import com.ticket.dto.ticket.UpdateSlaDTO;

/**
 * Servicio de gestión de las reglas de SLA.
 *
 * <p>Permite a la administración consultar y ajustar dinámicamente los tiempos de
 * resolución comprometidos por combinación de categoría y prioridad.</p>
 */
public interface SlaService {

    /**
     * @return todas las reglas de SLA configuradas.
     */
    List<SlaResponseDTO> listar();

    /**
     * Crea una nueva regla de SLA para una combinación categoría + prioridad.
     *
     * @param dto datos de la regla.
     * @return la regla creada.
     */
    SlaResponseDTO crear(CreateSlaDTO dto);

    /**
     * Activa o inactiva una regla de SLA.
     *
     * @param id     identificador de la regla.
     * @param activo nuevo estado.
     * @return la regla actualizada.
     */
    SlaResponseDTO cambiarEstado(Long id, boolean activo);

    /**
     * Actualiza los tiempos de una regla de SLA.
     *
     * @param id  identificador de la regla.
     * @param dto nuevos tiempos.
     * @return la regla actualizada.
     */
    SlaResponseDTO actualizar(Long id, UpdateSlaDTO dto);

    /**
     * Elimina una regla de SLA.
     *
     * @param id identificador de la regla.
     */
    void eliminar(Long id);
}
