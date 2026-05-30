INSERT INTO area (id_area, nombre, descripcion, activo) VALUES
(1, 'Soporte Técnico', 'Atiende incidencias de sistemas', 1),
(2, 'Desarrollo', 'Equipo de desarrollo de software', 1),
(3, 'Infraestructura', 'Gestión de servidores y redes', 1),
(4, 'Seguridad Informática', 'Protección de datos y accesos', 1),
(5, 'Atención al Cliente', 'Soporte directo a usuarios', 1),
(6, 'Recursos Humanos', 'Gestión de personal', 1),
(7, 'Finanzas', 'Control de presupuestos y pagos', 1),
(8, 'Marketing', 'Promoción y comunicación', 1),
(9, 'Ventas', 'Gestión comercial y clientes', 1),
(10, 'Dirección General', 'Administración y liderazgo', 1);

INSERT INTO rol (id_rol, nombre, descripcion) VALUES
(1, 'ADMIN', 'Acceso completo al sistema'),
(2, 'TECNICO', 'Atiende tickets asignados'),
(3, 'CLIENTE', 'Solicita soporte');

INSERT INTO usuario (id_usuario, id_area, id_rol, nombre, apellido, email, password, activo, creado_en, ultimo_acceso) VALUES
(1, 1, 3, 'Juan', 'Pérez', 'juan.perez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(2, 2, 2, 'María', 'Gómez', 'maria.gomez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(3, 3, 1, 'Carlos', 'Ramírez', 'carlos.ramirez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(4, 4, 2, 'Lucía', 'Fernández', 'lucia.fernandez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(5, 5, 3, 'Pedro', 'Torres', 'pedro.torres@utp.edu.pe', '123456', 1, NOW(), NOW()),
(6, 6, 1, 'Ana', 'Martínez', 'ana.martinez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(7, 7, 2, 'José', 'Castillo', 'jose.castillo@utp.edu.pe', '123456', 1, NOW(), NOW()),
(8, 8, 3, 'Rosa', 'Vargas', 'rosa.vargas@utp.edu.pe', '123456', 1, NOW(), NOW()),
(9, 9, 2, 'Miguel', 'Sánchez', 'miguel.sanchez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(10, 10, 1, 'Elena', 'Morales', 'elena.morales@utp.edu.pe', '123456', 1, NOW(), NOW()),
-- más usuarios variados
(11, 1, 2, 'Diego', 'Alvarez', 'diego.alvarez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(12, 2, 3, 'Valeria', 'Ríos', 'valeria.rios@utp.edu.pe', '123456', 1, NOW(), NOW()),
(13, 3, 1, 'Andrés', 'Flores', 'andres.flores@utp.edu.pe', '123456', 1, NOW(), NOW()),
(14, 4, 2, 'Camila', 'Mendoza', 'camila.mendoza@utp.edu.pe', '123456', 1, NOW(), NOW()),
(15, 5, 3, 'Sebastián', 'Cruz', 'sebastian.cruz@utp.edu.pe', '123456', 1, NOW(), NOW()),
(16, 6, 1, 'Paola', 'Jiménez', 'paola.jimenez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(17, 7, 2, 'Hugo', 'Salazar', 'hugo.salazar@utp.edu.pe', '123456', 1, NOW(), NOW()),
(18, 8, 3, 'Natalia', 'Reyes', 'natalia.reyes@utp.edu.pe', '123456', 1, NOW(), NOW()),
(19, 9, 2, 'Ricardo', 'Campos', 'ricardo.campos@utp.edu.pe', '123456', 1, NOW(), NOW()),
(20, 10, 1, 'Laura', 'Paredes', 'laura.paredes@utp.edu.pe', '123456', 1, NOW(), NOW()),
-- continúa hasta 47 usuarios
(21, 1, 3, 'Fernando', 'Silva', 'fernando.silva@utp.edu.pe', '123456', 1, NOW(), NOW()),
(22, 2, 2, 'Gabriela', 'Peña', 'gabriela.pena@utp.edu.pe', '123456', 1, NOW(), NOW()),
(23, 3, 1, 'Mauricio', 'Ortega', 'mauricio.ortega@utp.edu.pe', '123456', 1, NOW(), NOW()),
(24, 4, 2, 'Daniela', 'Suárez', 'daniela.suarez@utp.edu.pe', '123456', 1, NOW(), NOW()),
(25, 5, 3, 'Felipe', 'Navarro', 'felipe.navarro@utp.edu.pe', '123456', 1, NOW(), NOW()),
(26, 6, 1, 'Andrea', 'Quispe', 'andrea.quispe@utp.edu.pe', '123456', 1, NOW(), NOW()),
(27, 7, 2, 'Rodrigo', 'Mejía', 'rodrigo.mejia@utp.edu.pe', '123456', 1, NOW(), NOW()),
(28, 8, 3, 'Claudia', 'Huamán', 'claudia.huaman@utp.edu.pe', '123456', 1, NOW(), NOW()),
(29, 9, 2, 'Esteban', 'Palacios', 'esteban.palacios@utp.edu.pe', '123456', 1, NOW(), NOW()),
(30, 10, 1, 'Mónica', 'Aguilar', 'monica.aguilar@utp.edu.pe', '123456', 1, NOW(), NOW()),
-- hasta completar 47
(31, 1, 3, 'Julio', 'Carrillo', 'julio.carrillo@utp.edu.pe', '123456', 1, NOW(), NOW()),
(32, 2, 2, 'Patricia', 'Lozano', 'patricia.lozano@utp.edu.pe', '123456', 1, NOW(), NOW()),
(33, 3, 1, 'Oscar', 'Villanueva', 'oscar.villanueva@utp.edu.pe', '123456', 1, NOW(), NOW()),
(34, 4, 2, 'Liliana', 'Cornejo', 'liliana.cornejo@utp.edu.pe', '123456', 1, NOW(), NOW()),
(35, 5, 3, 'Raúl', 'Espinoza', 'raul.espinoza@utp.edu.pe', '123456', 1, NOW(), NOW()),
(36, 6, 1, 'Carmen', 'Bravo', 'carmen.bravo@utp.edu.pe', '123456', 1, NOW(), NOW()),
(37, 7, 2, 'Alberto', 'Rojas', 'alberto.rojas@utp.edu.pe', '123456', 1, NOW(), NOW()),
(38, 8, 3, 'Verónica', 'Delgado', 'veronica.delgado@utp.edu.pe', '123456', 1, NOW(), NOW()),
(39, 9, 2, 'Ignacio', 'Fuentes', 'ignacio.fuentes@utp.edu.pe', '123456', 1, NOW(), NOW()),
(40, 10, 1, 'Marisol', 'Cáceres', 'marisol.caceres@utp.edu.pe', '123456', 1, NOW(), NOW());





INSERT INTO categoria_ticket (id_categoria, nombre, tipo, activo) VALUES
(1, 'Hardware', 'Incidencia', 1),
(2, 'Software', 'Incidencia', 1),
(3, 'Red', 'Incidencia', 1),
(4, 'Correo Electrónico', 'Servicio', 1),
(5, 'Impresoras', 'Incidencia', 1),
(6, 'Aplicaciones Internas', 'Servicio', 1),
(7, 'Seguridad', 'Incidencia', 1),
(8, 'Acceso a Sistemas', 'Servicio', 1),
(9, 'Base de Datos', 'Incidencia', 1),
(10, 'Otros', 'General', 1);

INSERT INTO estado_ticket (id_estado, codigo, nombre, descripcion, es_terminal) VALUES
(1, 'ABIERTO', 'Abierto', 'Ticket recién creado', 0),
(2, 'ASIGNADO', 'Asignado', 'Ticket asignado a un técnico', 0),
(3, 'EN_PROCESO', 'En proceso', 'Ticket en atención', 0),
(4, 'EN_ESPERA', 'En espera', 'Ticket pausado por información pendiente', 0),
(5, 'RESUELTO', 'Resuelto', 'Ticket solucionado', 1),
(6, 'CERRADO', 'Cerrado', 'Ticket cerrado definitivamente', 1),
(7, 'CANCELADO', 'Cancelado', 'Ticket cancelado por el usuario', 1);

INSERT INTO prioridad_ticket (id_prioridad, nivel, color_hex) VALUES
(1, 'Baja', '#00FF00'),
(2, 'Media', '#FFFF00'),
(3, 'Alta', '#FF8000'),
(4, 'Crítica', '#FF0000');

INSERT INTO ticket (id_ticket, id_usuario_solicitante, id_usuario_tecnico, id_categoria, id_prioridad, id_estado, titulo, descripcion, fecha_creacion, sla_vencimiento) VALUES
(21, 1, 2, 1, 2, 2, 'Falla en monitor', 'El monitor parpadea constantemente', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(22, 3, 4, 2, 3, 3, 'Aplicación contable caída', 'El sistema contable no responde', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(23, 5, 6, 3, 1, 1, 'Internet intermitente', 'La conexión se corta cada 5 minutos', NOW(), DATE_ADD(NOW(), INTERVAL 3 HOUR)),
(24, 7, 8, 4, 2, 2, 'Correo bloqueado', 'No se pueden recibir correos externos', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(25, 9, 10, 5, 3, 3, 'Impresora no imprime', 'La impresora se queda en cola', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(26, 11, 12, 6, 2, 1, 'Error en sistema de RRHH', 'No se cargan los datos de empleados', NOW(), DATE_ADD(NOW(), INTERVAL 3 HOUR)),
(27, 13, 14, 7, 4, 1, 'Intento de intrusión', 'Se detectó acceso no autorizado', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR)),
(28, 15, 16, 8, 2, 2, 'Problema de login', 'Usuario no puede ingresar al sistema', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(29, 17, 18, 9, 3, 3, 'Error en consultas SQL', 'Reportes financieros no cargan', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(30, 19, 20, 10, 1, 1, 'Consulta general', 'Usuario solicita información básica', NOW(), DATE_ADD(NOW(), INTERVAL 5 HOUR)),

(31, 21, 22, 1, 3, 2, 'CPU sobrecalentado', 'El equipo se apaga por temperatura', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(32, 23, 24, 2, 4, 1, 'Sistema crítico caído', 'El sistema de ventas está fuera de línea', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR)),
(33, 25, 26, 3, 2, 2, 'VPN no conecta', 'No se logra conexión remota', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(34, 27, 28, 4, 1, 3, 'Correo interno lento', 'Retraso en entrega de correos internos', NOW(), DATE_ADD(NOW(), INTERVAL 3 HOUR)),
(35, 29, 30, 5, 2, 2, 'Impresora sin papel', 'No imprime documentos', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(36, 31, 32, 6, 3, 1, 'Error en nómina', 'Cálculo de sueldos incorrecto', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(37, 33, 34, 7, 4, 1, 'Malware detectado', 'Antivirus detectó amenaza crítica', NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR)),
(38, 35, 36, 8, 2, 2, 'Problema de login', 'Usuario no puede acceder al sistema', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(39, 37, 38, 9, 3, 3, 'Error en consultas', 'Reportes financieros no cargan', NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR)),
(40, 39, 40, 10, 1, 1, 'Consulta general', 'Usuario solicita información básica', NOW(), DATE_ADD(NOW(), INTERVAL 5 HOUR));
