package com.ticket.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ticket.dto.ticket.AccionTicketDTO;
import com.ticket.dto.ticket.CreateTicketDTO;
import com.ticket.model.ticket.CategoriaTicketEntity;
import com.ticket.model.ticket.EstadoTicketEntity;
import com.ticket.model.ticket.PrioridadTicketEntity;
import com.ticket.model.ticket.SlaEntity;
import com.ticket.model.usuario.AreaEntity;
import com.ticket.model.usuario.RolEntity;
import com.ticket.model.usuario.UsuarioEntity;
import com.ticket.repositories.jpa.AreaRepository;
import com.ticket.repositories.jpa.CategoriaTicketRepository;
import com.ticket.repositories.jpa.EstadoTicketRepository;
import com.ticket.repositories.jpa.PrioridadTicketRepository;
import com.ticket.repositories.jpa.RolRepository;
import com.ticket.repositories.jpa.SlaRepository;
import com.ticket.repositories.jpa.TicketRepository;
import com.ticket.repositories.jpa.UsuarioRepository;
import com.ticket.services.interfaces.TicketService;

/**
 * Cargador de datos iniciales (seed).
 *
 * <p>Pobla los catálogos maestros, las reglas de SLA, los usuarios de prueba (con
 * contraseña cifrada en BCrypt) y un conjunto de tickets de ejemplo, de modo que
 * el sistema sea inmediatamente funcional y se puedan validar los flujos del
 * avance (login, registro de tickets, dashboard).</p>
 *
 * <p>Es idempotente: cada bloque solo inserta si su tabla está vacía, por lo que
 * puede ejecutarse en cada arranque sin duplicar información. Se activa con la
 * propiedad {@code app.seed.enabled=true}.</p>
 *
 * <h3>Credenciales de prueba</h3>
 * <ul>
 *   <li>admin@serpost.pe / admin123 (Administrador)</li>
 *   <li>tecnico@serpost.pe / tecnico123 (Técnico)</li>
 *   <li>usuario@serpost.pe / usuario123 (Usuario final)</li>
 * </ul>
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final RolRepository rolRepository;
    private final AreaRepository areaRepository;
    private final EstadoTicketRepository estadoRepository;
    private final PrioridadTicketRepository prioridadRepository;
    private final CategoriaTicketRepository categoriaRepository;
    private final SlaRepository slaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RolRepository rolRepository, AreaRepository areaRepository,
                      EstadoTicketRepository estadoRepository, PrioridadTicketRepository prioridadRepository,
                      CategoriaTicketRepository categoriaRepository, SlaRepository slaRepository,
                      UsuarioRepository usuarioRepository, TicketRepository ticketRepository,
                      TicketService ticketService, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.areaRepository = areaRepository;
        this.estadoRepository = estadoRepository;
        this.prioridadRepository = prioridadRepository;
        this.categoriaRepository = categoriaRepository;
        this.slaRepository = slaRepository;
        this.usuarioRepository = usuarioRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedRoles();
        seedAreas();
        seedEstados();
        seedPrioridades();
        seedCategorias();
        seedSla();
        seedUsuarios();
        seedTicketsEjemplo();
        log.info("DataSeeder: verificación de datos iniciales completada.");
    }

    // ─────────────────────────── Catálogos ──────────────────────────────────

    private void seedRoles() {
        if (rolRepository.count() > 0) {
            return;
        }
        // El orden define los IDs (1=Administrador, 2=Tecnico, 3=Cliente), valores
        // de los que dependen el frontend y la asignación automática.
        guardarRol("Administrador", "Jefe de área: acceso total al sistema");
        guardarRol("Tecnico", "Atiende y resuelve los tickets asignados");
        guardarRol("Cliente", "Usuario final que registra y consulta sus tickets");
        log.info("DataSeeder: roles creados.");
    }

    private void seedAreas() {
        if (areaRepository.count() > 0) {
            return;
        }
        // Áreas organizacionales de SERPOST (gerencias).
        guardarArea("Gerencia Comercial", "Ventas, marketing y atención al cliente");
        guardarArea("Gerencia de Desarrollo Corporativo", "Planeamiento y proyectos corporativos");
        guardarArea("Gerencia Postal", "Operaciones del servicio postal");
        guardarArea("Gerencia de Canales", "Red de oficinas y canales de atención");
        guardarArea("Gerencia de Administración de Recursos", "Recursos humanos, finanzas y logística");
        log.info("DataSeeder: áreas creadas.");
    }

    private void seedEstados() {
        if (estadoRepository.count() > 0) {
            return;
        }
        guardarEstado("ABIERTO", "Abierto", "Ticket registrado, sin técnico asignado", false);
        guardarEstado("ASIGNADO", "Asignado", "Asignado a un técnico, pendiente de inicio", false);
        guardarEstado("EN_PROCESO", "En Proceso", "El técnico está trabajando en el ticket", false);
        guardarEstado("PENDIENTE", "Pendiente", "En espera de información o de un tercero", false);
        guardarEstado("RESUELTO", "Resuelto", "Solución aplicada, pendiente de cierre", false);
        guardarEstado("CERRADO", "Cerrado", "Ticket finalizado y archivado", true);
        log.info("DataSeeder: estados creados.");
    }

    private void seedPrioridades() {
        if (prioridadRepository.count() > 0) {
            return;
        }
        guardarPrioridad("CRÍTICA", "ef4444");
        guardarPrioridad("ALTA", "f97316");
        guardarPrioridad("MEDIA", "eab308");
        guardarPrioridad("BAJA", "22c55e");
        log.info("DataSeeder: prioridades creadas.");
    }

    private void seedCategorias() {
        if (categoriaRepository.count() > 0) {
            return;
        }
        guardarCategoria("Hardware", "SOPORTE");
        guardarCategoria("Software", "SOPORTE");
        guardarCategoria("Accesos", "SOLICITUD");
        guardarCategoria("Instalación", "SOLICITUD");
        log.info("DataSeeder: categorías creadas.");
    }

    /**
     * Crea una matriz de reglas de SLA (categoría × prioridad). El tiempo de
     * resolución depende de la criticidad, según el diseño del motor de reglas.
     */
    private void seedSla() {
        if (slaRepository.count() > 0) {
            return;
        }
        List<CategoriaTicketEntity> categorias = categoriaRepository.findAll();
        List<PrioridadTicketEntity> prioridades = prioridadRepository.findAll();
        for (CategoriaTicketEntity cat : categorias) {
            for (PrioridadTicketEntity pri : prioridades) {
                int resolucion = horasPorPrioridad(pri.getNivel());
                SlaEntity sla = new SlaEntity();
                sla.setCategoria(cat);
                sla.setPrioridad(pri);
                sla.setTiempoAtencion(Math.max(1, resolucion / 4));
                sla.setTiempoResolucion(resolucion);
                sla.setActivo(true);
                slaRepository.save(sla);
            }
        }
        log.info("DataSeeder: reglas de SLA creadas ({} reglas).", categorias.size() * prioridades.size());
    }

    /** Horas de resolución comprometidas según el nivel de prioridad. */
    private int horasPorPrioridad(String nivel) {
        return switch (nivel) {
            case "CRÍTICA" -> 4;
            case "ALTA" -> 8;
            case "MEDIA" -> 24;
            default -> 72;
        };
    }

    // ─────────────────────────── Usuarios ───────────────────────────────────

    private void seedUsuarios() {
        if (usuarioRepository.count() > 0) {
            return;
        }
        RolEntity admin = rolRepository.findById(1L).orElse(null);
        RolEntity tecnico = rolRepository.findById(2L).orElse(null);
        RolEntity usuario = rolRepository.findById(3L).orElse(null);
        AreaEntity soporte = areaRepository.findById(1L).orElse(null);
        AreaEntity redes = areaRepository.findById(2L).orElse(null);
        AreaEntity operaciones = areaRepository.findById(5L).orElse(null);

        guardarUsuario("Admin", "Sistema", "admin@serpost.pe", "admin123", admin, soporte);
        guardarUsuario("Johan", "Cuenca", "tecnico@serpost.pe", "tecnico123", tecnico, soporte);
        guardarUsuario("Lucía", "Ramos", "tecnico2@serpost.pe", "tecnico123", tecnico, redes);
        guardarUsuario("María", "Torres", "usuario@serpost.pe", "usuario123", usuario, operaciones);
        guardarUsuario("Carlos", "Ríos", "carlos@serpost.pe", "usuario123", usuario, operaciones);
        log.info("DataSeeder: usuarios de prueba creados.");
    }

    // ─────────────────────────── Tickets demo ───────────────────────────────

    /**
     * Genera tickets de ejemplo usando el servicio de negocio real (de modo que se
     * disparen la asignación automática, el cálculo de SLA y el historial), y hace
     * avanzar algunos por el flujo de estados para poblar el dashboard.
     */
    private void seedTicketsEjemplo() {
        if (ticketRepository.count() > 0) {
            return;
        }
        Long solicitante = usuarioRepository.findByEmail("usuario@serpost.pe")
                .map(UsuarioEntity::getIdUsuario).orElse(null);
        Long solicitante2 = usuarioRepository.findByEmail("carlos@serpost.pe")
                .map(UsuarioEntity::getIdUsuario).orElse(null);
        if (solicitante == null) {
            return;
        }

        Long t1 = crearTicket(solicitante, 1L, 1L,
                "Impresora de red no responde en Piso 3",
                "La impresora HP LaserJet de Operaciones no imprime desde esta mañana.");
        Long t2 = crearTicket(solicitante2 != null ? solicitante2 : solicitante, 2L, 2L,
                "Correo corporativo no sincroniza en Outlook",
                "El correo del área de RR.HH. no sincroniza desde ayer.");
        crearTicket(solicitante, 3L, 3L,
                "Solicitud de nueva cuenta VPN para teletrabajo",
                "Se requiere acceso VPN para trabajo remoto durante la próxima semana.");
        Long t4 = crearTicket(solicitante2 != null ? solicitante2 : solicitante, 4L, 4L,
                "Instalación de software de facturación SUNAT",
                "Instalar el módulo de facturación electrónica en contabilidad.");

        // Hacer avanzar algunos tickets por el flujo para tener variedad de estados.
        if (t2 != null) {
            ticketService.atender(t2, null);
        }
        if (t4 != null) {
            AccionTicketDTO cierre = new AccionTicketDTO();
            cierre.setComentario("Software instalado y configurado. Usuario capacitado.");
            ticketService.cerrar(t4, cierre);
        }
        if (t1 != null) {
            ticketService.atender(t1, null);
        }
        log.info("DataSeeder: tickets de ejemplo creados.");
    }

    private Long crearTicket(Long idSolicitante, Long idCategoria, Long idPrioridad,
                             String titulo, String descripcion) {
        CreateTicketDTO dto = new CreateTicketDTO();
        dto.setIdUsuarioSolicitante(idSolicitante);
        dto.setIdCategoria(idCategoria);
        dto.setIdPrioridad(idPrioridad);
        dto.setTitulo(titulo);
        dto.setDescripcion(descripcion);
        return ticketService.crear(dto).getIdTicket();
    }

    // ─────────────────────── Helpers de persistencia ────────────────────────

    private void guardarRol(String nombre, String descripcion) {
        RolEntity r = new RolEntity();
        r.setNombre(nombre);
        r.setDescripcion(descripcion);
        rolRepository.save(r);
    }

    private void guardarArea(String nombre, String descripcion) {
        AreaEntity a = new AreaEntity();
        a.setNombre(nombre);
        a.setDescripcion(descripcion);
        a.setActivo(true);
        areaRepository.save(a);
    }

    private void guardarEstado(String codigo, String nombre, String descripcion, boolean terminal) {
        EstadoTicketEntity e = new EstadoTicketEntity();
        e.setCodigo(codigo);
        e.setNombre(nombre);
        e.setDescripcion(descripcion);
        e.setEsTerminal(terminal);
        estadoRepository.save(e);
    }

    private void guardarPrioridad(String nivel, String colorHex) {
        PrioridadTicketEntity p = new PrioridadTicketEntity();
        p.setNivel(nivel);
        p.setColorHex(colorHex);
        prioridadRepository.save(p);
    }

    private void guardarCategoria(String nombre, String tipo) {
        CategoriaTicketEntity c = new CategoriaTicketEntity();
        c.setNombre(nombre);
        c.setTipo(tipo);
        c.setActivo(true);
        categoriaRepository.save(c);
    }

    private void guardarUsuario(String nombre, String apellido, String email, String passwordPlano,
                                RolEntity rol, AreaEntity area) {
        UsuarioEntity u = new UsuarioEntity();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(passwordPlano));
        u.setRol(rol);
        u.setArea(area);
        u.setActivo(true);
        u.setCreadoEn(java.time.LocalDateTime.now());
        usuarioRepository.save(u);
    }
}
