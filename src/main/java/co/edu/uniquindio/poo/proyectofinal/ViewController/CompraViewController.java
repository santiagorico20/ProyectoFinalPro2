package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Evento;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CompraViewController {

    @FXML private Label lblSaludoUsuario;
    @FXML private TextField txtBuscarEvento;
    @FXML private ComboBox<String> comboCategoria;

    @FXML private TableView<Evento> tablaEventosCliente;
    @FXML private TableColumn<Evento, String> colNombre, colDescripcion, colCiudad, colFecha;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();
    private ObservableList<Evento> listaEventosPublicados; // Lista base

    @FXML
    public void initialize() {
        // Cargar opciones del ComboBox (Las opciones deben coincidir con la descripción del evento por ahora)
        comboCategoria.getItems().addAll("Todos", "Concierto", "Teatro", "Conferencia", "Deportes");
        comboCategoria.setValue("Todos"); // Valor por defecto

        if (facade.getUsuarioAutenticado() != null) {
            lblSaludoUsuario.setText("Sesión activa: " + facade.getUsuarioAutenticado().getNombre());
        }

        // Configuración de columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecinto().getCiudad()));

        cargarEventosPublicados();
        configurarFiltros();
    }

    private void cargarEventosPublicados() {
        // Solo mostramos los eventos que el Admin ya pasó a estado "Publicado"
        List<Evento> eventos = facade.getEventos().stream()
                .filter(evento -> evento.getNombreEstado().equals("Publicado"))
                .collect(Collectors.toList());

        listaEventosPublicados = FXCollections.observableArrayList(eventos);
    }

    private void configurarFiltros() {
        // 1. Envolvemos la lista observable en una FilteredList
        FilteredList<Evento> datosFiltrados = new FilteredList<>(listaEventosPublicados, b -> true);

        // 2. Listener para la barra de búsqueda (Texto)
        txtBuscarEvento.textProperty().addListener((observable, oldValue, newValue) -> {
            datosFiltrados.setPredicate(evento -> cumpleCriterios(evento, newValue, comboCategoria.getValue()));
        });

        // 3. Listener para el ComboBox (Categoría)
        comboCategoria.valueProperty().addListener((observable, oldValue, newValue) -> {
            datosFiltrados.setPredicate(evento -> cumpleCriterios(evento, txtBuscarEvento.getText(), newValue));
        });

        // 4. Envolvemos la lista filtrada en una SortedList para permitir ordenamiento por columnas
        SortedList<Evento> datosOrdenados = new SortedList<>(datosFiltrados);
        datosOrdenados.comparatorProperty().bind(tablaEventosCliente.comparatorProperty());

        // 5. Inyectamos la lista final a la tabla
        tablaEventosCliente.setItems(datosOrdenados);
    }

    // Método que evalúa si un evento debe mostrarse según los filtros activos
    private boolean cumpleCriterios(Evento evento, String textoBusqueda, String categoria) {
        // Validación de categoría (Asumimos que la categoría está escrita en la descripción para este ejemplo)
        boolean coincideCategoria = categoria == null || categoria.equals("Todos") ||
                evento.getDescripcion().toLowerCase().contains(categoria.toLowerCase());

        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            return coincideCategoria; // Si no hay texto, solo filtra por categoría
        }

        String filtroMinusculas = textoBusqueda.toLowerCase();

        // Buscamos coincidencia en el Nombre del evento o en la Ciudad del recinto
        boolean coincideTexto = evento.getNombre().toLowerCase().contains(filtroMinusculas) ||
                evento.getRecinto().getCiudad().toLowerCase().contains(filtroMinusculas);

        return coincideTexto && coincideCategoria;
    }

    @FXML
    public void limpiarFiltros() {
        txtBuscarEvento.clear();
        comboCategoria.setValue("Todos");
    }

    @FXML
    public void verDetalleEvento() {
        Evento eventoSeleccionado = tablaEventosCliente.getSelectionModel().getSelectedItem();

        if (eventoSeleccionado == null) {
            mostrarAlerta("Atención", "Por favor, selecciona un evento de la tabla primero.", Alert.AlertType.WARNING);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyectofinal/DetalleCompraView.fxml"));
        try {
            Parent root = loader.load();
            DetalleCompraViewController controller = loader.getController();
            controller.setEvento(eventoSeleccionado);

            Stage stage = (Stage) tablaEventosCliente.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Nexus Tickets - Compra de Entradas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void verMisCompras() {
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/MisComprasView.fxml", "Mis Compras - Nexus Tickets");
    }

    @FXML
    public void cerrarSesion() {
        facade.setUsuarioAutenticado(null);
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/LoginView.fxml", "Iniciar Sesión");
    }

    // 📢 NUEVO MÉTODO PARA ABRIR LA PANTALLA DE INCIDENCIAS
    @FXML
    public void abrirReportarIncidencia() {
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/ReportarIncidenciaView.fxml", "Reportar un Problema");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}