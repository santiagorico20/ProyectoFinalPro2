package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Controller.Persistencia;
import co.edu.uniquindio.poo.proyectofinal.Model.*;
import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.IPagoStrategy;
import co.edu.uniquindio.poo.proyectofinal.Navegador;

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

            if (carritoCompras.contains(asiento)) {
                btnSilla.setStyle("-fx-background-color: #1f6feb; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
            } else if (estado.equals("DISPONIBLE")) {
                btnSilla.setStyle("-fx-background-color: #2ea043; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
            } else {
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

        String idUnicoCompra = "COM-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        LocalDate fechaHoy = LocalDate.now();

        Compra nuevaCompra = new Compra(idUnicoCompra, fechaHoy, usuarioActual);

        for (Asiento a : carritoCompras) {
            a.ocupar();
        }

        IPagoStrategy estrategiaSeleccionada = null;

        if (opcionPago.contains("Tarjeta")) {
            // estrategiaSeleccionada = new TarjetaStrategy();
        } else if (opcionPago.contains("Transferencia")) {
            // estrategiaSeleccionada = new TransferenciaStrategy();
        }

        if (estrategiaSeleccionada == null) {
            estrategiaSeleccionada = (monto) -> true;
        }

        nuevaCompra.setMetodoPago(estrategiaSeleccionada);
        boolean transaccionExitosa = nuevaCompra.procesarPagoConEstrategia();

        if (transaccionExitosa) {
            usuarioActual.getHistorialCompras().add(nuevaCompra);

            // Guardamos el estado usando tu método de fachada
            facade.resguardarEstado();

            mostrarAlerta("¡Pago Exitoso!", "Tu pago a través de '" + opcionPago + "' fue procesado correctamente.\n¡Disfruta el evento!", Alert.AlertType.INFORMATION);

            carritoCompras.clear();
            actualizarCarrito();
            cargarMapaCliente();
        } else {
            mostrarAlerta("Transacción Cancelada", "La pasarela de pago rechazó la operación. Intente con otro método.", Alert.AlertType.ERROR);
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