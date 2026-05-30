# API — Sistema de Tickets Automatizado SERPOST

Servicio web REST para la Mesa de Ayuda de SERPOST, desarrollado bajo una
**Arquitectura Orientada a Servicios (SOA)**. Permite el registro autónomo de
incidencias por el usuario final, la asignación automática de técnicos, el control
de niveles de servicio (SLA), la trazabilidad por historial y el monitoreo
mediante indicadores (dashboard).

## 🧱 Stack tecnológico

| Capa | Tecnología |
|------|------------|
| Lenguaje | Java 21 |
| Framework | Spring Boot 4.0.6 (Spring MVC, Spring Data JPA) |
| Persistencia | MySQL 8+/9 · Hibernate ORM 7 |
| Seguridad | BCrypt (cifrado de contraseñas) |
| Serialización | Jackson 3 (JSON) |
| Build | Maven (wrapper incluido) |

## 🏛️ Arquitectura en capas (MVC)

El proyecto aplica una separación estricta de responsabilidades. El flujo de una
petición atraviesa las capas de arriba hacia abajo, y la respuesta regresa
convertida en DTO:

```
  Cliente (Next.js / React)
        │  HTTP + JSON
        ▼
┌──────────────────────────────────────────────────────────────┐
│ controller/   →  Capa de presentación (endpoints REST)         │
│                  Traduce HTTP ↔ negocio. No contiene reglas.    │
├──────────────────────────────────────────────────────────────┤
│ services/     →  Capa de lógica de negocio (interfaces + impl)  │
│                  Validaciones, asignación, SLA, flujo, etc.     │
├──────────────────────────────────────────────────────────────┤
│ repositories/ →  Capa de acceso a datos (Spring Data JPA)       │
│                  Único punto que habla con la base de datos.    │
├──────────────────────────────────────────────────────────────┤
│ model/        →  Entidades JPA (mapeo objeto-relacional)        │
└──────────────────────────────────────────────────────────────┘
        ▲
        │ dto/    Objetos de transferencia (entrada/salida JSON)
        │ mapper/ Conversión Entidad ↔ DTO (centralizada)
```

### Estructura de paquetes

```
com.ticket
├── config/            WebConfig (CORS), CryptoConfig (BCrypt), DataSeeder
├── controller/        AuthController, TicketController, SlaController,
│   │                  DashboardController, CatalogoController, Usuario/Area/Rol
│   └── advice/        GlobalExceptionHandler (manejo uniforme de errores)
├── dto/
│   ├── auth/          LoginDTO, SesionUsuarioDTO
│   ├── ticket/        CreateTicketDTO, TicketResponseDTO, HistorialTicketDTO, ...
│   ├── dashboard/     DashboardDTO, ConteoEstadoDTO, CargaTecnicoDTO
│   ├── common/        PaginatedResponse<T>
│   └── usuario/...    DTOs de usuario, área y rol (preexistentes)
├── mapper/            TicketMapper (Entidad → DTO, nulo-seguro y DRY)
├── model/             Entidades JPA (ticket/, usuario/, notificacion/)
├── repositories/jpa/  Repositorios Spring Data
└── services/
    ├── interfaces/    Contratos de servicio
    ├── impl/          Implementaciones (reglas de negocio)
    └── support/       FlujoEstados (grafo de transiciones del ciclo de vida)
```

## ⚙️ Requisitos previos

- **JDK 21** (configurar `JAVA_HOME`).
- **MySQL** en `localhost:3306` (usuario `root` / contraseña `root` por defecto;
  ajustar en `application.properties`). La base `db_serpost_tickets` se crea
  automáticamente.

## ▶️ Ejecución

```bash
# Windows (PowerShell)
$env:JAVA_HOME="C:\Program Files\Java\jdk-21"
.\mvnw.cmd spring-boot:run

# Linux / macOS
JAVA_HOME=/ruta/jdk-21 ./mvnw spring-boot:run
```

Al iniciar, el `DataSeeder` puebla catálogos, reglas de SLA, usuarios de prueba y
tickets de ejemplo (solo si las tablas están vacías). El servicio queda disponible
en `http://localhost:8080/api`.

Comprobación rápida: `GET http://localhost:8080/api/health`.

### 👤 Credenciales de prueba

| Rol | Usuario (email) | Contraseña |
|-----|-----------------|------------|
| Administrador | `admin@serpost.pe` | `admin123` |
| Técnico | `tecnico@serpost.pe` | `tecnico123` |
| Usuario final | `usuario@serpost.pe` | `usuario123` |

## 📡 Contrato del servicio (endpoints)

Todas las rutas cuelgan del contexto `/api`.

### Autenticación
| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/auth/login` | Valida credenciales (BCrypt) y devuelve la sesión |

### Tickets
| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/tickets` | Registrar un ticket (asignación + SLA automáticos) |
| GET | `/tickets` | Listar/filtrar (`estado`, `idTecnico`, `idSolicitante`, `page`, `per_page`) |
| GET | `/tickets/assigned` | Bandeja de tickets asignados a un técnico |
| GET | `/tickets/{id}` | Detalle de un ticket |
| POST | `/tickets/{id}/atender` | Iniciar atención (→ EN_ATENCION) |
| POST | `/tickets/{id}/cerrar` | Resolver con comentario (→ RESUELTO) |
| PUT | `/tickets/{id}` | Cambiar estado validando el flujo |
| PATCH | `/tickets/{id}/tecnico` | Reasignar técnico (admin) |
| PATCH | `/tickets/{id}/prioridad` | Cambiar prioridad y recalcular SLA (admin) |
| GET | `/tickets/{id}/historial` | Línea de tiempo del ticket |
| POST | `/tickets/{id}/comentarios` | Agregar comentario/evento |

### Catálogos, SLA y Dashboard
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/estados` · `/prioridades` · `/categorias` | Catálogos del ticket |
| GET | `/sla` | Listar reglas de SLA |
| POST | `/sla` | Crear una regla de SLA |
| PUT | `/sla/{id}` | Actualizar tiempos de una regla |
| PATCH | `/sla/{id}/estado?activo=` | Activar/inactivar una regla |
| DELETE | `/sla/{id}` | Eliminar una regla |
| GET | `/dashboard` | KPIs consolidados (admin) |
| GET | `/auditoria` | Bitácora de auditoría (admin) |

### Usuarios / Áreas / Roles
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET/POST/PUT/DELETE | `/usuarios` … | Gestión de usuarios (BCrypt; DELETE protege integridad) |
| PATCH | `/usuarios/estado/cambiar` | Activar/inactivar usuario |
| GET | `/roles` · `/areas` | Catálogos de usuario |

## 🧠 Reglas de negocio implementadas

- **Validación de creación**: campos obligatorios (solicitante, categoría, título,
  descripción) verificados con Bean Validation + en el servicio.
- **Asignación automática de técnico**: se elige el técnico activo con menor carga
  de tickets abiertos (balanceo de carga). Ver `TicketServiceImpl#asignarTecnicoAutomatico`.
- **Cálculo automático de SLA**: `fecha_creación + horas_resolución` según la regla
  de la combinación categoría + prioridad (con respaldos por prioridad y por defecto).
- **Flujo de estados** (`FlujoEstados`): `ABIERTO → ASIGNADO → EN_PROCESO →
  PENDIENTE → RESUELTO → CERRADO`, con transiciones de retorno controladas. Las
  transiciones inválidas devuelven `422`.
- **Trazabilidad**: cada acción genera un evento de historial (CREACION, ASIGNACION,
  ATENCION, RESOLUCION, CAMBIO_ESTADO, REASIGNACION, CAMBIO_PRIORIDAD, COMENTARIO).
- **Auditoría**: bitácora de seguridad (login/logout, creación, cambios,
  reasignaciones, eliminaciones) con usuario, IP y marca de tiempo.
- **Notificaciones**: se registran ante creación y cambios de estado (el envío SMTP
  real es una fase posterior).

## 🔐 Seguridad (JWT + BCrypt)

- Las contraseñas se almacenan cifradas con **BCrypt** (`CryptoConfig`).
- **Autenticación con JWT** (`security/`):
  - Al iniciar sesión, `AuthService` valida las credenciales y `JwtService` emite
    un token firmado (HS512) con el id y el rol del usuario, incluido en la
    respuesta de `/auth/login`.
  - El cliente envía el token en cada petición: `Authorization: Bearer <token>`.
  - `JwtAuthFilter` valida el token y carga la identidad en el contexto de seguridad.
  - `SecurityConfig` define una cadena **stateless**: `/auth/**` y `/health` son
    públicos, `/dashboard/**` requiere rol **Administrador** y el resto exige token.
- **Autorización por rol**: las autoridades se derivan del rol del token
  (`ROLE_ADMINISTRADOR`, `ROLE_TECNICO`, `ROLE_USUARIO`).
- **CORS** integrado en la cadena de seguridad; manejo uniforme de errores
  (401/403 en JSON) y `GlobalExceptionHandler` (siempre incluye `message`).

Configurar el secreto y la vigencia del token en `application.properties`
(`app.jwt.secret`, `app.jwt.expiration-ms`).

## 📌 Estado del avance

Operativo de extremo a extremo: **autenticación JWT**, CRUD de usuarios, registro y
ciclo de vida completo de tickets, SLA, historial y dashboard. Pendiente para el
100%: envío real de notificaciones por correo (SMTP) y documentación interactiva
Swagger UI.
