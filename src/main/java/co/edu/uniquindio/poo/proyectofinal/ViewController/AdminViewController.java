package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.*;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdminViewController {

    @FXML private StackPane stackContenido;
    @FXML private VBox panelBienvenida, panelEventos, panelRecintos, panelAsientos, panelUsuarios, panelMetricas;

    // Formulario Eventos
    @FXML private TextField txtNombreEvento, txtDescEvento;
    @FXML private DatePicker dpFechaEvento;
    @FXML private ComboBox<String> comboRecintoEvento;

    // Formulario Recintos y Zonas
    @FXML private TextField txtNombreRecinto, txtCiudadRecinto, txtDireccionRecinto;
    @FXML private ComboBox<String> comboRecintoParaZona;
    @FXML private TextField txtNombreZona, txtCapacidadZona, txtPrecioZona;
    @FXML private CheckBox chkEsNumerada;

    // Tablas
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colUsuarioId, colUsuarioNombre, colUsuarioCorreo, colUsuarioRol;

    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colEventoId, colEventoNombre, colEventoCiudad, colEventoFecha, colEventoEstado;

    @FXML private TableView<Recinto> tablaRecintos;
    @FXML private TableColumn<Recinto, String> colRecintoId, colRecintoNombre, colRecintoCiudad, colRecintoDireccion;

    // Mapa Asientos
    @FXML private ComboBox<String> comboRecintoAsientos, comboZonaAsientos;
    @FXML private GridPane gridAsientos;

    // Etiquetas de Métricas
    @FXML private Label lblTotalIngresos, lblTotalEntradas, lblTotalClientes, lblTotalEventos;

    // 📊 NUEVOS COMPONENTES VISUALES PARA LAS MÉTRICAS ANMADOS
    @FXML private PieChart chartIngresos;
    @FXML private VBox cardClientes;
    @FXML private VBox cardEventos;
    @FXML private VBox cardEntradas;
    @FXML private VBox cardIngresos;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    public void initialize() {
        colUsuarioId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colUsuarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsuarioCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colUsuarioRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        colRecintoId.setCellValueFactory(new PropertyValueFactory<>("idRecinto"));
        colRecintoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRecintoCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colRecintoDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        colEventoId.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        colEventoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEventoFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEventoEstado.setCellValueFactory(new PropertyValueFactory<>("nombreEstado"));
        colEventoCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecinto().getCiudad()));

        comboRecintoAsientos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            comboZonaAsientos.getItems().clear();
            if (newVal != null) {
                Recinto r = facade.getRecintos().stream().filter(rec -> rec.getNombre().equals(newVal)).findFirst().orElse(null);
                if (r != null) r.getZonas().stream().filter(Zona::isEsNumerada).forEach(z -> comboZonaAsientos.getItems().add(z.getNombre()));
            }
        });
    }

    private void ocultarTodosLosPaneles() {
        panelBienvenida.setVisible(false); panelEventos.setVisible(false);
        panelRecintos.setVisible(false); panelAsientos.setVisible(false);
        panelUsuarios.setVisible(false); panelMetricas.setVisible(false);
    }

    private void actualizarCombosRecintos() {
        comboRecintoEvento.getItems().clear();
        comboRecintoParaZona.getItems().clear();
        comboRecintoAsientos.getItems().clear();
        facade.getRecintos().forEach(r -> {
            comboRecintoEvento.getItems().add(r.getNombre());
            comboRecintoParaZona.getItems().add(r.getNombre());
            comboRecintoAsientos.getItems().add(r.getNombre());
        });
    }

    // ==========================================
    // SECCIÓN: EVENTOS
    // ==========================================
    @FXML
    public void mostrarPanelEventos() {
        ocultarTodosLosPaneles(); panelEventos.setVisible(true);
        actualizarTablaEventos();
        actualizarCombosRecintos();
    }

    @FXML
    public void crearEvento() {
        String nombre = txtNombreEvento.getText();
        String desc = txtDescEvento.getText();
        String nomRecinto = comboRecintoEvento.getValue();

        if (nombre == null || desc == null || nombre.isEmpty() || desc.isEmpty() || dpFechaEvento.getValue() == null || nomRecinto == null) {
            mostrarAlerta("Campos vacíos", "Completa todos los campos para crear el evento.", Alert.AlertType.WARNING);
            return;
        }

        Recinto recinto = facade.getRecintos().stream().filter(r -> r.getNombre().equals(nomRecinto)).findFirst().orElse(null);
        if (recinto != null) {
            String idAleatorio = "EVE-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            Evento nuevoEvento = new Evento(idAleatorio, nombre, desc, dpFechaEvento.getValue().atTime(20, 0), recinto);
            facade.getEventos().add(nuevoEvento);

            mostrarAlerta("Éxito", "El evento '" + nombre + "' fue creado.", Alert.AlertType.INFORMATION);
            txtNombreEvento.clear(); txtDescEvento.clear(); dpFechaEvento.setValue(null); comboRecintoEvento.setValue(null);
            actualizarTablaEventos();
        }
    }

    @FXML public void publicarEventoSeleccionado() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e != null) { e.publicar(); tablaEventos.refresh(); }
    }

    @FXML public void cancelarEventoSeleccionado() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e != null) { e.cancelar(); tablaEventos.refresh(); }
    }

    private void actualizarTablaEventos() {
        tablaEventos.setItems(FXCollections.observableArrayList(facade.getEventos()));
        tablaEventos.refresh();
    }

    // ==========================================
    // SECCIÓN: RECINTOS Y ZONAS
    // ==========================================
    @FXML
    public void mostrarPanelRecintos() {
        ocultarTodosLosPaneles();
        panelRecintos.setVisible(true);
        tablaRecintos.setItems(FXCollections.observableArrayList(facade.getRecintos()));
        actualizarCombosRecintos();
    }

    @FXML
    public void crearRecinto() {
        String nombre = txtNombreRecinto.getText();
        String ciudad = txtCiudadRecinto.getText();
        String direccion = txtDireccionRecinto.getText();

        if (nombre == null || ciudad == null || direccion == null || nombre.isEmpty() || ciudad.isEmpty() || direccion.isEmpty()) {
            mostrarAlerta("Error", "Completa los campos del recinto.", Alert.AlertType.WARNING); return;
        }

        String idAleatorio = "REC-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        Recinto nuevoRecinto = new Recinto(idAleatorio, nombre, direccion, ciudad);
        facade.getRecintos().add(nuevoRecinto);

        mostrarAlerta("Éxito", "Recinto '" + nombre + "' creado. Ahora puedes agregarle zonas.", Alert.AlertType.INFORMATION);
        txtNombreRecinto.clear(); txtCiudadRecinto.clear(); txtDireccionRecinto.clear();

        tablaRecintos.setItems(FXCollections.observableArrayList(facade.getRecintos()));
        tablaRecintos.refresh();
        actualizarCombosRecintos();
    }

    @FXML
    public void crearZonaParaRecinto() {
        String nomRecinto = comboRecintoParaZona.getValue();
        String nombreZona = txtNombreZona.getText();

        try {
            int capacidad = Integer.parseInt(txtCapacidadZona.getText());
            double precio = Double.parseDouble(txtPrecioZona.getText());
            boolean numerada = chkEsNumerada.isSelected();

            if (nomRecinto == null || nombreZona.isEmpty() || capacidad <= 0 || precio < 0) {
                mostrarAlerta("Datos inválidos", "Verifica la información de la zona.", Alert.AlertType.WARNING);
                return;
            }

            Recinto r = facade.getRecintos().stream().filter(rec -> rec.getNombre().equals(nomRecinto)).findFirst().orElse(null);
            if (r != null) {
                String idZona = "ZON-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
                Zona nuevaZona = new Zona(idZona, nombreZona, capacidad, precio, numerada);
                r.agregarZona(nuevaZona);

                mostrarAlerta("Éxito", "Zona '" + nombreZona + "' agregada a " + r.getNombre() + " con " + capacidad + " sillas.", Alert.AlertType.INFORMATION);
                txtNombreZona.clear(); txtCapacidadZona.clear(); txtPrecioZona.clear(); chkEsNumerada.setSelected(true);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La capacidad y el precio deben ser números válidos.", Alert.AlertType.ERROR);
        }
    }

    // ==========================================
    // SECCIÓN: VISUALIZACIÓN GRÁFICA DEL MAPA
    // ==========================================
    @FXML
    public void mostrarPanelAsientos() {
        ocultarTodosLosPaneles();
        panelAsientos.setVisible(true);
        actualizarCombosRecintos();
        gridAsientos.getChildren().clear();
    }

    @FXML
    public void cargarMapaAsientos() {
        gridAsientos.getChildren().clear();
        gridAsientos.setHgap(8); gridAsientos.setVgap(8);

        String nomRecinto = comboRecintoAsientos.getValue();
        String nomZona = comboZonaAsientos.getValue();
        if (nomRecinto == null || nomZona == null) return;

        Recinto r = facade.getRecintos().stream().filter(rec -> rec.getNombre().equals(nomRecinto)).findFirst().orElse(null);
        if (r == null) return;
        Zona z = r.getZonas().stream().filter(zo -> zo.getNombre().equals(nomZona)).findFirst().orElse(null);
        if (z == null) return;

        for (Asiento asiento : z.getAsientos()) {
            Button btnSilla = new Button("💺\n" + asiento.getCodigo());
            btnSilla.setPrefSize(70, 55);

            String estadoActual = asiento.getNombreEstado().toUpperCase();
            String colorFondo = "#2ea043"; // VERDE
            if (estadoActual.equals("RESERVADO")) colorFondo = "#d29922"; // AMARILLO
            else if (estadoActual.equals("OCUPADO")) colorFondo = "#f85149"; // ROJO

            btnSilla.setStyle("-fx-background-color: " + colorFondo + "; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");

            Tooltip tooltip = new Tooltip("📍 Asiento: " + asiento.getCodigo() + "\n📌 Estado: " + estadoActual);
            btnSilla.setTooltip(tooltip);

            btnSilla.setOnAction(e -> {
                if (estadoActual.equals("DISPONIBLE")) {
                    asiento.reservar();
                    cargarMapaAsientos();
                } else if (estadoActual.equals("RESERVADO")) {
                    asiento.ocupar();
                    cargarMapaAsientos();
                } else if (estadoActual.equals("OCUPADO")) {
                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Revocar Asiento");
                    dialog.setHeaderText("Vas a cancelar el asiento " + asiento.getCodigo());
                    dialog.setContentText("Escribe el motivo de la cancelación para el cliente:");

                    dialog.showAndWait().ifPresent(motivo -> {
                        if(!motivo.trim().isEmpty()) {
                            asiento.liberar();
                            facade.cancelarEntradaPorAdmin(asiento, motivo);
                            cargarMapaAsientos();
                            mostrarAlerta("Éxito", "Silla liberada y cliente notificado.", Alert.AlertType.INFORMATION);
                        } else {
                            mostrarAlerta("Error", "Debes ingresar un motivo.", Alert.AlertType.ERROR);
                        }
                    });
                }
            });
            gridAsientos.add(btnSilla, asiento.getNumero() - 1, asiento.getFila() - 1);
        }
    }

    // ==========================================
    // SECCIÓN: MÉTRICAS Y USUARIOS 🚀
    // ==========================================
    @FXML
    public void mostrarPanelUsuarios() {
        ocultarTodosLosPaneles();
        panelUsuarios.setVisible(true);
        tablaUsuarios.setItems(FXCollections.observableArrayList(facade.getUsuarios()));
    }

    @FXML
    public void mostrarPanelMetricas() {
        ocultarTodosLosPaneles();
        panelMetricas.setVisible(true);

        // 1. Cálculos lógicos
        long totalClientes = facade.getUsuarios().stream().filter(u -> u.getRol().equalsIgnoreCase("CLIENTE")).count();
        long totalEventos = facade.getEventos().stream().filter(e -> e.getNombreEstado().equals("Publicado")).count();

        lblTotalClientes.setText(String.valueOf(totalClientes));
        lblTotalEventos.setText(String.valueOf(totalEventos));

        int entradasVendidas = 0;
        double ingresosTotales = 0.0;

        for (Usuario u : facade.getUsuarios()) {
            if (u.getHistorialCompras() != null) {
                entradasVendidas += u.getHistorialCompras().size();
                for (Compra compra : u.getHistorialCompras()) {
                    ingresosTotales += compra.getTotal();
                }
            }
        }

        lblTotalEntradas.setText(String.valueOf(entradasVendidas));
        lblTotalIngresos.setText(String.format("$%,.2f", ingresosTotales));

        // 2. 📊 CONFIGURACIÓN DEL PIECHART SEGURA (No detiene la app si falta el fx:id)
        try {
            if (chartIngresos != null) {
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Clientes (" + totalClientes + ")", totalClientes),
                        new PieChart.Data("Entradas V. (" + entradasVendidas + ")", entradasVendidas),
                        new PieChart.Data("Eventos Activos (" + totalEventos + ")", totalEventos)
                );
                chartIngresos.setData(pieChartData);
                chartIngresos.setLegendVisible(true);
            }
        } catch (Exception e) {
            System.err.println("⚠️ [MÉTRICAS] El PieChart no está configurado en el archivo FXML.");
        }

        // 3. 🎬 ANIMACIÓN INDIVIDUAL DE LAS TARJETAS (Efecto de carga progresiva)
        try {
            aplicarEfectoPopIn(cardClientes, 0);
            aplicarEfectoPopIn(cardEventos, 150);
            aplicarEfectoPopIn(cardEntradas, 300);
            aplicarEfectoPopIn(cardIngresos, 450);

            if (chartIngresos != null) {
                aplicarEfectoFade(chartIngresos, 600);
            }
        } catch (Exception e) {
            System.err.println("⚠️ [MÉTRICAS] Revisa que cardClientes, cardEventos, cardEntradas y cardIngresos existan en tu FXML.");
        }
    }

    /**
     * Hace que las tarjetas emerjan con una escala y opacidad sutil combinadas.
     */
    private void aplicarEfectoPopIn(Node nodo, double retrasoMilisegundos) {
        if (nodo == null) return;

        nodo.setOpacity(0.0);
        nodo.setScaleX(0.85);
        nodo.setScaleY(0.85);

        FadeTransition fade = new FadeTransition(Duration.millis(450), nodo);
        fade.setToValue(1.0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(450), nodo);
        scale.setToX(1.0);
        scale.setToY(1.0);

        ParallelTransition pt = new ParallelTransition(fade, scale);
        pt.setDelay(Duration.millis(retrasoMilisegundos));
        pt.play();
    }

    /**
     * Transición suave de visibilidad para los gráficos pesados.
     */
    private void aplicarEfectoFade(Node nodo, double retrasoMilisegundos) {
        if (nodo == null) return;
        nodo.setOpacity(0.0);
        FadeTransition ft = new FadeTransition(Duration.millis(500), nodo);
        ft.setToValue(1.0);
        ft.setDelay(Duration.millis(retrasoMilisegundos));
        ft.play();
    }

    @FXML public void cerrarSesion() { facade.setUsuarioAutenticado(null); Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/LoginView.fxml", "Iniciar Sesión"); }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo); alerta.setTitle(titulo); alerta.setHeaderText(null); alerta.setContentText(mensaje); alerta.showAndWait();
    }
}