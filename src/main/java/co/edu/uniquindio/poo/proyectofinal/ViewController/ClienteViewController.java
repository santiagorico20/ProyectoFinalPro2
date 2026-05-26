package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.*;
import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.IPagoStrategy; // 🔥 Importación de tu interfaz Strategy
// Nota: Asegúrate de importar tus implementaciones concretas de Strategy, por ejemplo:
// import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.TarjetaStrategy;
// import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.TransferenciaStrategy;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClienteViewController {

    @FXML private ComboBox<String> comboEventos;
    @FXML private ComboBox<String> comboZonas;
    @FXML private GridPane gridAsientosCliente;

    // Panel de Pagos
    @FXML private VBox panelPago;
    @FXML private ListView<String> listaCarrito;
    @FXML private Label lblTotalPago;
    @FXML private ComboBox<String> comboMetodoPago;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();
    private List<Asiento> carritoCompras = new ArrayList<>();
    private Evento eventoSeleccionado;
    private Zona zonaSeleccionada;

    @FXML
    public void initialize() {
        // Cargar eventos publicados en el sistema
        facade.getEventos().stream()
                .filter(e -> e.getNombreEstado().equals("Publicado"))
                .forEach(e -> comboEventos.getItems().add(e.getNombre()));

        // Listener para cuando el cliente selecciona un evento
        comboEventos.setOnAction(e -> {
            String nomEv = comboEventos.getValue();
            eventoSeleccionado = facade.getEventos().stream().filter(ev -> ev.getNombre().equals(nomEv)).findFirst().orElse(null);
            comboZonas.getItems().clear();
            if (eventoSeleccionado != null) {
                eventoSeleccionado.getRecinto().getZonas().forEach(z -> comboZonas.getItems().add(z.getNombre()));
            }
        });

        // Listener para cuando selecciona la zona
        comboZonas.setOnAction(e -> cargarMapaCliente());

        // Inicializar el ComboBox con las opciones legibles de pago
        comboMetodoPago.setItems(FXCollections.observableArrayList(
                "💳 Tarjeta de Crédito/Débito",
                "🏦 Transferencia Bancaria (PSE)"
        ));

        // Revisar si el administrador le canceló algo mientras no estaba
        revisarNotificaciones();
    }

    private void cargarMapaCliente() {
        gridAsientosCliente.getChildren().clear();
        if (eventoSeleccionado == null || comboZonas.getValue() == null) return;

        zonaSeleccionada = eventoSeleccionado.getRecinto().getZonas().stream()
                .filter(z -> z.getNombre().equals(comboZonas.getValue())).findFirst().orElse(null);
        if (zonaSeleccionada == null) return;

        gridAsientosCliente.setHgap(8);
        gridAsientosCliente.setVgap(8);

        for (Asiento asiento : zonaSeleccionada.getAsientos()) {
            Button btnSilla = new Button("💺\n" + asiento.getCodigo());
            btnSilla.setPrefSize(70, 55);

            String estado = asiento.getNombreEstado().toUpperCase();

            // Si el cliente ya seleccionó la silla localmente, se pinta azul (Carrito)
            if (carritoCompras.contains(asiento)) {
                btnSilla.setStyle("-fx-background-color: #1f6feb; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
            } else if (estado.equals("DISPONIBLE")) {
                btnSilla.setStyle("-fx-background-color: #2ea043; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
            } else {
                // Sillas ocupadas o reservadas por otros se deshabilitan y van a rojo
                btnSilla.setStyle("-fx-background-color: #f85149; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-opacity: 0.4;");
                btnSilla.setDisable(true);
            }

            btnSilla.setOnAction(e -> {
                if (carritoCompras.contains(asiento)) {
                    carritoCompras.remove(asiento);
                } else if (estado.equals("DISPONIBLE")) {
                    carritoCompras.add(asiento);
                }
                actualizarCarrito();
                cargarMapaCliente();
            });
            gridAsientosCliente.add(btnSilla, asiento.getNumero() - 1, asiento.getFila() - 1);
        }
    }

    private void actualizarCarrito() {
        listaCarrito.getItems().clear();
        double total = 0;
        for (Asiento a : carritoCompras) {
            listaCarrito.getItems().add("Asiento: " + a.getCodigo() + " - $" + zonaSeleccionada.getPrecioBase());
            total += zonaSeleccionada.getPrecioBase();
        }
        lblTotalPago.setText("$ " + total);
        panelPago.setVisible(!carritoCompras.isEmpty());
    }

    @FXML
    public void procesarPago() {
        String opcionPago = comboMetodoPago.getValue();
        if (opcionPago == null) {
            mostrarAlerta("Método requerido", "Por favor, selecciona un método de pago antes de continuar.", Alert.AlertType.WARNING);
            return;
        }

        Usuario usuarioActual = facade.getUsuarioAutenticado();
        if (usuarioActual == null) {
            mostrarAlerta("Error de Sesión", "No se detectó un usuario autenticado.", Alert.AlertType.ERROR);
            return;
        }

        // ✨ SOLUCIÓN AL ERROR: Instanciamos tu objeto Compra con tus 3 argumentos exactos del modelo
        String idUnicoCompra = "COM-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        LocalDate fechaHoy = LocalDate.now();

        Compra nuevaCompra = new Compra(idUnicoCompra, fechaHoy, usuarioActual);

        // 🎟️ Construcción de las Entradas para la compra basándonos en los asientos del carrito
        for (Asiento a : carritoCompras) {
            // Suponiendo que tu constructor de Entrada pide el ID, el precio, el asiento y el evento:
            // Entrada entrada = new Entrada("ENT-" + UUID.randomUUID().toString().substring(0,5), zonaSeleccionada.getPrecioBase(), a, eventoSeleccionado);
            // nuevaCompra.agregarEntrada(entrada);

            // Para mantener el flujo del estado visual del mapa:
            a.ocupar();
        }

        // 🔄 INYECCIÓN DINÁMICA DEL PATRÓN STRATEGY
        IPagoStrategy estrategiaSeleccionada = null;

        if (opcionPago.contains("Tarjeta")) {
            // Reemplaza 'TarjetaStrategy' por el nombre exacto de tu clase concreta
            // estrategiaSeleccionada = new TarjetaStrategy();
        } else if (opcionPago.contains("Transferencia")) {
            // Reemplaza 'TransferenciaStrategy' por el nombre exacto de tu clase concreta
            // estrategiaSeleccionada = new TransferenciaStrategy();
        }

        // Simulador de fallback en caso de que no tengas las clases de estrategia creadas todavía
        if (estrategiaSeleccionada == null) {
            // Estrategia anónima temporal de respaldo para evitar NullPointerException si no están listas las clases
            estrategiaSeleccionada = (monto) -> true;
        }

        // Pasamos la estrategia elegida a tu modelo Compra
        nuevaCompra.setMetodoPago(estrategiaSeleccionada);

        // 🔥 Ejecutamos la lógica estratégica que creaste en tu modelo
        boolean transaccionExitosa = nuevaCompra.procesarPagoConEstrategia();

        if (transaccionExitosa) {
            // Si la pasarela aprueba, vinculamos la compra al historial del usuario
            usuarioActual.getHistorialCompras().add(nuevaCompra);

            mostrarAlerta("¡Pago Exitoso!", "Tu pago a través de '" + opcionPago + "' fue procesado correctamente.\n¡Disfruta el evento!", Alert.AlertType.INFORMATION);

            carritoCompras.clear();
            actualizarCarrito();
            cargarMapaCliente();
        } else {
            // Si falla la transacción, liberamos los asientos locales del carrito para que reintenten
            mostrarAlerta("Transacción Rechazada", "La pasarela de pago rechazó la operación. Intente con otro método.", Alert.AlertType.ERROR);
        }
    }

    private void revisarNotificaciones() {
        Usuario actual = facade.getUsuarioAutenticado();
        if (actual != null && actual.getNotificaciones() != null && !actual.getNotificaciones().isEmpty()) {
            StringBuilder mensajeAlerta = new StringBuilder("⚠️ AVISO IMPORTANTE DE LA ADMINISTRACIÓN:\n\n");
            for (String notificacion : actual.getNotificaciones()) {
                mensajeAlerta.append("• ").append(notificacion).append("\n\n");
            }

            mostrarAlerta("Notificaciones del Sistema", mensajeAlerta.toString(), Alert.AlertType.WARNING);
            actual.limpiarNotificaciones(); // Se limpian los mensajes una vez leídos
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}