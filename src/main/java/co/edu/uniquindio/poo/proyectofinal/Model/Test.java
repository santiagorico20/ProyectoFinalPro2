public class SistemaEventosTest {

    // Instancia del Logger del sistema
    private static final Logger logger = Logger.getLogger(SistemaEventosTest.class.getName());
    private SistemaEventosService service;

    @BeforeEach
    void setUp() {
        logger.info("Iniciando entorno de prueba...");
        service = new SistemaEventosService();
    }

    @Test
    @DisplayName("T01 - Registro exitoso de usuario")
    void testRegistrarUsuarioExitoso() {
        logger.log(Level.INFO, "Ejecutando T01: Registrar usuario.");

        Usuario usuario = service.registrarUsuario("Juan Pérez");

        assertNotNull(usuario, "El usuario no debería ser nulo");
        assertEquals("USR-123", usuario.id);
        logger.info("T01 Finalizado con éxito. Usuario registrado con ID: " + usuario.id);
    }

    @Test
    @DisplayName("T02 - Cancelar un evento activo")
    void testCancelarEvento() {
        logger.info("Ejecutando T02: Cancelar evento.");
        Evento evento = new Evento();
        evento.nombre = "Concierto Rock";

        service.cancelarEvento(evento);

        assertEquals("CANCELADO", evento.estado, "El estado debería pasar a CANCELADO");
        logger.info("T02 Finalizado con éxito. Estado del evento: " + evento.estado);
    }

    @Test
    @DisplayName("T03 - Consultar disponibilidad de asientos")
    void testConsultarDisponibilidadAsientos() {
        logger.info("Ejecutando T03: Consultar asientos.");

        List<Asiento> asientosLibres = service.consultarDisponibilidad();

        assertFalse(asientosLibres.isEmpty(), "Debería retornar asientos disponibles");
        logger.info("T03 Finalizado con éxito. Cantidad de asientos encontrados: " + asientosLibres.size());
    }

    @Test
    @DisplayName("T04 & T05 - Procesar pagos con diferentes métodos")
    void testProcesarPagoFallidoPorMonto() {
        logger.info("Ejecutando T04/T05: Validar flujo de pago.");
        Pago pagoInvalido = new Pago();
        pagoInvalido.metodo = "Tarjeta de Crédito";
        pagoInvalido.monto = -50.0; // Monto incorrecto

        boolean resultado = service.procesarPago(pagoInvalido);

        assertFalse(resultado, "El pago no debería procesarse con montos negativos o cero");
        logger.warning("T04/T05 Finalizado. El sistema rechazó correctamente el pago con monto inválido.");
    }

    @Test
    @DisplayName("T06 - Validar entrada con QR ya usado")
    void testValidarCodigoQrYaUsado() {
        logger.info("Ejecutando T06: Validar código QR.");
        TicketExt ticket = new TicketExt();
        ticket.qrCode = "QR-998877";
        ticket.validatedTrue(); // Se marca como usado previamente

        boolean esValido = service.validarQR(ticket);

        assertFalse(esValido, "Un QR ya usado no debe ser admitido de nuevo");
        logger.info("T06 Finalizado con éxito. El QR repetido fue rechazado.");
    }

    @Test
    @DisplayName("T07 - Notificar cambios de estado de eventos")
    void testNotificarCambiosDeEstado() {
        logger.info("Ejecutando T07: Enviar notificaciones de eventos.");

        boolean notificacionEnviada = service.enviarNotificacion("El evento de las 8:00 PM ha sido cancelado");

        assertTrue(notificacionEnviada, "La notificación debió despacharse correctamente");
        logger.info("T07 Finalizado con éxito. Notificaciones enviadas al broker de mensajería.");
    }

    @Test
    @DisplayName("T08 - Administrar cambio de estado de asientos")
    void testCambiarEstadoAsientoAReservado() {
        logger.info("Ejecutando T08: Modificar estado de asiento.");
        Asiento asiento = new Asiento();

        service.cambiarEstadoAsiento(asiento, "RESERVADO");

        assertFalse(asiento.disponible, "El asiento ya no debería estar disponible al pasar a RESERVADO");
        logger.info("T08 Finalizado con éxito. Estado del asiento actualizado correctamente.");
    }
}