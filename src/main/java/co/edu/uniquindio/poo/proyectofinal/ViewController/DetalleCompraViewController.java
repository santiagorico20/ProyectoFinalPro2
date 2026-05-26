package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.*;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.UUID;

public class DetalleCompraViewController {

    @FXML private Label lblNombreEvento, lblDetalleRecinto, lblAsientoSeleccionado, lblTotalPagar;
    @FXML private ComboBox<String> comboZonas;
    @FXML private GridPane gridAsientosCliente;
    @FXML private Button btnProcederPago;

    // Los CheckBoxes que el cliente va a marcar
    private CheckBox chkSeguro;
    private CheckBox chkKitRegalo;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();
    private Evento eventoActual;
    private Asiento asientoEscogido;
    private Zona zonaSeleccionada;

    // Precios de los servicios adicionales
    private final double PRECIO_SEGURO = 15000.0;
    private final double PRECIO_KIT = 35000.0;

    public void setEvento(Evento evento) {
        this.eventoActual = evento;
        lblNombreEvento.setText(evento.getNombre());
        lblDetalleRecinto.setText(evento.getRecinto().getNombre() + " (" + evento.getRecinto().getCiudad() + ")");

        comboZonas.getItems().clear();
        evento.getRecinto().getZonas().forEach(z -> comboZonas.getItems().add(z.getNombre()));
    }

    @FXML
    public void initialize() {
        // 🎯 CREACIÓN DINÁMICA: Creamos los CheckBoxes con diseño limpio
        chkSeguro = new CheckBox("Añadir Seguro de Asistencia Básica (+$15.000)");
        chkKitRegalo = new CheckBox("Añadir Kit Oficial Merchandising (+$35.000)");

        chkSeguro.setStyle("-fx-font-size: 13px; -fx-text-fill: #333333; -fx-cursor: hand;");
        chkKitRegalo.setStyle("-fx-font-size: 13px; -fx-text-fill: #333333; -fx-cursor: hand;");

        // 🎯 INYECCIÓN EN LA VISTA: Buscamos el contenedor del botón de pago para meter los CheckBoxes justo encima
        if (btnProcederPago != null && btnProcederPago.getParent() instanceof Pane) {
            Pane contenedorPadre = (Pane) btnProcederPago.getParent();

            // Creamos una cajita vertical para ordenar los checkboxes de forma elegante
            VBox cajaAdicionales = new VBox(10);
            cajaAdicionales.setPadding(new Insets(10, 0, 15, 0));
            cajaAdicionales.getChildren().addAll(chkSeguro, chkKitRegalo);

            // Si el contenedor es un VBox o similar, lo añadimos antes del botón de pago
            if (contenedorPadre instanceof VBox) {
                int indexBoton = ((VBox) contenedorPadre).getChildren().indexOf(btnProcederPago);
                ((VBox) contenedorPadre).getChildren().add(indexBoton, cajaAdicionales);
            } else {
                // Si es un contenedor plano, lo agregamos al final para que aparezca en pantalla
                contenedorPadre.getChildren().add(cajaAdicionales);
            }
        }

        // Listener: Al cambiar de localidad, se limpia todo y se redibuja el mapa
        comboZonas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            limpiarSeleccion();
            if (newVal != null) {
                zonaSeleccionada = eventoActual.getRecinto().getZonas().stream()
                        .filter(z -> z.getNombre().equals(newVal)).findFirst().orElse(null);
                dibujarMapaAsientos();
            }
        });

        // 🎯 LOGICA EN VIVO: Si el cliente interactúa, recalculamos el precio final de inmediato
        chkSeguro.setOnAction(e -> calcularYMostrarPrecioTotal());
        chkKitRegalo.setOnAction(e -> calcularYMostrarPrecioTotal());
    }

    /**
     * Revisa qué tiene seleccionado el usuario (Asiento + Adicionales) y actualiza el label del precio
     */
    private void calcularYMostrarPrecioTotal() {
        if (zonaSeleccionada == null || asientoEscogido == null) {
            lblTotalPagar.setText("$ 0.00");
            return;
        }

        // Iniciamos con el precio base de la zona del asiento
        double totalAcumulado = zonaSeleccionada.getPrecioBase();

        // Si marcó el seguro, le sumamos el precio del seguro
        if (chkSeguro.isSelected()) {
            totalAcumulado += PRECIO_SEGURO;
        }

        // Si marcó el kit, le sumamos el precio del kit
        if (chkKitRegalo.isSelected()) {
            totalAcumulado += PRECIO_KIT;
        }

        // Mostramos el resultado final en la interfaz
        lblTotalPagar.setText("$ " + totalAcumulado);
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

                    // Calculamos el precio total inmediatamente al tocar el asiento
                    calcularYMostrarPrecioTotal();
                    btnProcederPago.setDisable(false);

                    dibujarMapaAsientos(); // Refrescar colores del mapa
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

        asientoEscogido.ocupar();

        // Entrada recibe 4 argumentos (el 4to es el precio base que toma del modelo)
        Entrada nuevoTicket = new Entrada(eventoActual, zonaSeleccionada, asientoEscogido, zonaSeleccionada.getPrecioBase());

        Usuario usuarioActual = facade.getUsuarioAutenticado();
        if (usuarioActual != null) {

            String idCompra = "CMP-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();

            // Compra recibe exactamente 3 argumentos tal como lo muestra tu modelo
            Compra nuevaCompra = new Compra(idCompra, LocalDate.now(), usuarioActual);

            // Metemos la entrada obligatoria
            nuevaCompra.agregarEntrada(nuevoTicket);

            // 🎯 ADICIONALES CORREGIDOS: Estructura corregida por ti que ya compila
            if (chkSeguro.isSelected()) {
                ServicioAdicional seguro = new ServicioAdicional("SRV-01", "Seguro de Asistencia", "Seguro", PRECIO_SEGURO);
                nuevaCompra.agregarServicio(seguro);
            }
            if (chkKitRegalo.isSelected()) {
                ServicioAdicional kit = new ServicioAdicional("SRV-02", "Kit Oficial de Regalo", "Kit", PRECIO_KIT);
                nuevaCompra.agregarServicio(kit);
            }

            // Confirmar y guardar en el historial
            nuevaCompra.confirmarCompra();
            usuarioActual.getHistorialCompras().add(nuevaCompra);

            if (facade.getListaCompras() != null) {
                facade.getListaCompras().add(nuevaCompra);
            }

            // Mensaje de éxito detallado con lo que pagó al final
            mostrarAlerta("¡Compra Exitosa!",
                    "Tu pago ha sido confirmado.\n\n" +
                            "🎫 TICKET: " + nuevoTicket.getIdTicket() + "\n" +
                            "🪑 Asiento: " + asientoEscogido.getCodigo() + "\n" +
                            "💵 Total Pagado (Con adicionales): $" + nuevaCompra.getTotal() + "\n\n" +
                            "Puedes ver tus entradas en 'Mis Compras'.", Alert.AlertType.INFORMATION);
        }

        // Resetear todo para una nueva compra
        chkSeguro.setSelected(false);
        chkKitRegalo.setSelected(false);
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