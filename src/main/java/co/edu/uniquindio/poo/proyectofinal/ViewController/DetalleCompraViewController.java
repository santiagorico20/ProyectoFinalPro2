package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.*;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.UUID;

public class DetalleCompraViewController {

    @FXML private Label lblNombreEvento, lblDetalleRecinto, lblAsientoSeleccionado, lblTotalPagar;
    @FXML private ComboBox<String> comboZonas;
    @FXML private GridPane gridAsientosCliente;
    @FXML private Button btnProcederPago;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();
    private Evento eventoActual;
    private Asiento asientoEscogido;
    private Zona zonaSeleccionada;

    // Método para inyectar el evento seleccionado desde la cartelera
    public void setEvento(Evento evento) {
        this.eventoActual = evento;
        lblNombreEvento.setText(evento.getNombre());
        lblDetalleRecinto.setText(evento.getRecinto().getNombre() + " (" + evento.getRecinto().getCiudad() + ")");

        // Cargar las zonas disponibles en el combo
        comboZonas.getItems().clear();
        evento.getRecinto().getZonas().forEach(z -> comboZonas.getItems().add(z.getNombre()));
    }

    @FXML
    public void initialize() {
        // Listener: Al cambiar de localidad, redibujamos el mapa de esa zona
        comboZonas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            limpiarSeleccion();
            if (newVal != null) {
                zonaSeleccionada = eventoActual.getRecinto().getZonas().stream()
                        .filter(z -> z.getNombre().equals(newVal)).findFirst().orElse(null);
                dibujarMapaAsientos();
            }
        });
    }

    private void dibujarMapaAsientos() {
        gridAsientosCliente.getChildren().clear();
        if (zonaSeleccionada == null) return;

        for (Asiento asiento : zonaSeleccionada.getAsientos()) {
            Button btnSilla = new Button(asiento.getCodigo());
            btnSilla.setPrefSize(55, 35);

            String estado = asiento.getNombreEstado();
            String color = "#2ea043"; // Verde: Disponible

            if (estado.equals("RESERVADO")) {
                if (asientoEscogido == asiento) color = "#d29922"; // Amarillo: Tu Selección
                else color = "#f85149"; // Rojo: Bloqueado por otro proceso
            } else if (estado.equals("OCUPADO")) {
                color = "#f85149"; // Rojo: Vendido
            }

            btnSilla.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 9px; -fx-font-weight: bold; -fx-cursor: hand;");

            btnSilla.setOnAction(e -> {
                if (estado.equals("DISPONIBLE")) {
                    if (asientoEscogido != null) asientoEscogido.liberar();

                    asientoEscogido = asiento;
                    asientoEscogido.reservar();

                    lblAsientoSeleccionado.setText(asiento.getCodigo() + " - " + zonaSeleccionada.getNombre());
                    lblTotalPagar.setText("$ " + zonaSeleccionada.getPrecioBase());
                    btnProcederPago.setDisable(false);

                    dibujarMapaAsientos(); // Refrescar colores
                } else {
                    mostrarAlerta("Asiento no disponible", "Esta silla ya está apartada o vendida.", Alert.AlertType.WARNING);
                }
            });

            gridAsientosCliente.add(btnSilla, asiento.getNumero() - 1, asiento.getFila() - 1);
        }
    }

    @FXML
    public void procesarPago() {
        if (asientoEscogido == null) return;

        // 1. Cambiar el estado de reservado a OCUPADO utilizando el Patrón State
        asientoEscogido.ocupar();

        // 2. Generar el Ticket (Entrada)
        Entrada nuevoTicket = new Entrada(eventoActual, zonaSeleccionada, asientoEscogido, zonaSeleccionada.getPrecioBase());

        // 3. Guardar el ticket encapsulado en una Compra dentro del historial del usuario logueado
        Usuario usuarioActual = facade.getUsuarioAutenticado();
        if (usuarioActual != null) {

            // Generamos un ID único para la compra con formato CMP-XXXXX
            String idCompra = "CMP-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();

            // Instanciamos tu clase Compra usando su constructor real
            Compra nuevaCompra = new Compra(idCompra, LocalDate.now(), usuarioActual);

            // Agregamos la entrada a la lista interna de la compra y recalculamos el total de forma segura
            nuevaCompra.agregarEntrada(nuevoTicket);

            // Cambiamos el estado de la compra a CONFIRMADA
            nuevaCompra.confirmarCompra();

            // Añadimos la compra al historial que ahora acepta estrictamente objetos de tipo 'Compra'
            usuarioActual.getHistorialCompras().add(nuevaCompra);

            // Sincronizamos con la lista global de compras de la fachada (si existe en tu fachada)
            if (facade.getListaCompras() != null) {
                facade.getListaCompras().add(nuevaCompra);
            }
        }

        mostrarAlerta("¡Compra Exitosa!",
                "Tu pago ha sido confirmado.\n\n" +
                        "🎫 TICKET: " + nuevoTicket.getIdTicket() + "\n" +
                        "🪑 Asiento: " + asientoEscogido.getCodigo() + "\n\n" +
                        "Puedes ver tus entradas en 'Mis Compras'.", Alert.AlertType.INFORMATION);

        // 4. Resetear la vista y volver a pintar el mapa
        asientoEscogido = null;
        limpiarSeleccion();
        dibujarMapaAsientos();
    }

    private void limpiarSeleccion() {
        asientoEscogido = null;
        lblAsientoSeleccionado.setText("Ninguno (Haz clic en el mapa)");
        lblTotalPagar.setText("$ 0.00");
        btnProcederPago.setDisable(true);
    }

    @FXML
    public void regresar() {
        if (asientoEscogido != null) {
            asientoEscogido.liberar();
        }
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/CompraView.fxml", "Cartelera de Eventos");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}