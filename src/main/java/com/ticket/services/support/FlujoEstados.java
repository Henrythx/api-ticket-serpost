package com.ticket.services.support;

import java.util.Map;
import java.util.Set;

/**
 * Define el ciclo de vida de un ticket y las transiciones de estado permitidas.
 *
 * <p>Implementa la regla de negocio "validar que el estado del ticket siga un
 * flujo correcto". El ciclo de vida del Help Desk es:
 * {@code ABIERTO → ASIGNADO → EN_PROCESO → PENDIENTE → RESUELTO → CERRADO},
 * admitiendo transiciones de retorno controladas (poner pendiente, reanudar y
 * reabrir).</p>
 *
 * <p>Centralizar aquí el grafo de transiciones evita esparcir condicionales por la
 * lógica de negocio y facilita su mantenimiento.</p>
 */
public final class FlujoEstados {

    /** Estado inicial: ticket registrado sin técnico asignado. */
    public static final String ABIERTO = "ABIERTO";
    /** Ticket con técnico asignado, aún no iniciado. */
    public static final String ASIGNADO = "ASIGNADO";
    /** El técnico está trabajando activamente en el ticket. */
    public static final String EN_PROCESO = "EN_PROCESO";
    /** Ticket pausado a la espera de un tercero o información del cliente. */
    public static final String PENDIENTE = "PENDIENTE";
    /** El técnico aplicó una solución; pendiente de conformidad/cierre. */
    public static final String RESUELTO = "RESUELTO";
    /** Estado terminal: el ticket queda archivado. */
    public static final String CERRADO = "CERRADO";

    /** Grafo de transiciones válidas: estado actual → estados destino permitidos. */
    private static final Map<String, Set<String>> TRANSICIONES = Map.of(
            ABIERTO, Set.of(ASIGNADO, EN_PROCESO, CERRADO),
            ASIGNADO, Set.of(EN_PROCESO, PENDIENTE, CERRADO),
            EN_PROCESO, Set.of(PENDIENTE, RESUELTO, ASIGNADO),
            PENDIENTE, Set.of(EN_PROCESO, RESUELTO),
            RESUELTO, Set.of(CERRADO, EN_PROCESO),
            CERRADO, Set.of()
    );

    private FlujoEstados() {
        // Clase de utilidad: no instanciable.
    }

    /**
     * Indica si se permite mover un ticket de un estado a otro.
     *
     * @param origen  código del estado actual.
     * @param destino código del estado destino.
     * @return {@code true} si la transición es válida.
     */
    public static boolean esTransicionValida(String origen, String destino) {
        if (origen == null || destino == null) {
            return false;
        }
        if (origen.equals(destino)) {
            return false;
        }
        return TRANSICIONES.getOrDefault(origen, Set.of()).contains(destino);
    }
}
