package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Compra;
import co.edu.uniquindio.poo.proyectofinal.Model.Entrada;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class MisComprasViewController {

    @FXML private TableView<Entrada> tablaMisEntradas;
    @FXML private TableColumn<Entrada, String> colTicket, colEvento, colFecha, colAsiento, colPrecio;

    // 🎯 COMPONENTES VISUALES ENLAZADOS PARA EL RF-010 (Agrégalos a tu archivo FXML)
    @FXML private TextField txtFiltroTexto;
    @FXML private ComboBox<String> comboFiltroEstado;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();
    private ObservableList<Entrada> listaMaestraEntradas;

    @FXML
    public void initialize() {
        // Cargar las opciones del filtro de estados basados en tus criterios de Enum
        if (comboFiltroEstado != null) {
            comboFiltroEstado.getItems().addAll("Todos", "CREADA", "CONFIRMADA", "CANCELADA", "REEMBOLSADA");
            comboFiltroEstado.setValue("Todos");
        }

        // Configuración de columnas
        colTicket.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioPagado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));

        // Propiedades calculadas (Sacar datos de objetos internos)
        colEvento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEvento().getNombre()));

        colAsiento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAsiento().getCodigo() + " (" + cellData.getValue().getZona().getNombre() + ")"));

        cargarDatosConPredicados();
    }

    private void cargarDatosConPredicados() {
        Usuario user = facade.getUsuarioAutenticado();
        if (user != null) {
            List<Entrada> todasMisEntradas = new ArrayList<>();

            for (Compra compra : user.getHistorialCompras()) {
                if (compra.getEntradas() != null) {
                    todasMisEntradas.addAll(compra.getEntradas());
                }
            }

            listaMaestraEntradas = FXCollections.observableArrayList(todasMisEntradas);

            // 🎯 CONSTRUCCIÓN DEL FILTRADO MULTICRITERIO DE LA UI (RF-010)
            FilteredList<Entrada> datosFiltrados = new FilteredList<>(listaMaestraEntradas, p -> true);

            // Listener reactivo para la caja de texto
            if (txtFiltroTexto != null) {
                txtFiltroTexto.textProperty().addListener((obs, viejo, nuevo) -> {
                    datosFiltrados.setPredicate(entrada -> evaluarFiltrosCruzados(entrada, nuevo, comboFiltroEstado.getValue()));
                });
            }

            // Listener reactivo para el combo de estados del Enum
            if (comboFiltroEstado != null) {
                comboFiltroEstado.valueProperty().addListener((obs, viejo, nuevo) -> {
                    datosFiltrados.setPredicate(entrada -> evaluarFiltrosCruzados(entrada, txtFiltroTexto.getText(), nuevo));
                });
            }

            // Integrar posibilidad de ordenar las columnas interactivamente
            SortedList<Entrada> datosOrdenados = new SortedList<>(datosFiltrados);
            datosOrdenados.comparatorProperty().bind(tablaMisEntradas.comparatorProperty());

            tablaMisEntradas.setItems(datosOrdenados);
        }
    }

    /**
     * Motor analítico que cruza los filtros de la pantalla con las propiedades de la entrada y de su Compra dueña.
     */
    private boolean evaluarFiltrosCruzados(Entrada entrada, String busquedaTexto, String filtroEstado) {
        // 1. Encontrar el estado de la compra a la que pertenece este Ticket
        Usuario user = facade.getUsuarioAutenticado();
        String estadoCompraDeEstaEntrada = "CREADA"; // Valor por defecto seguro

        if (user != null) {
            for (Compra c : user.getHistorialCompras()) {
                if (c.getEntradas() != null && c.getEntradas().contains(entrada)) {
                    estadoCompraDeEstaEntrada = c.getEstadoCompra().toString();
                    break;
                }
            }
        }

        // Evaluar coincidencia de estado (Enum)
        boolean coincideEstado = filtroEstado == null || filtroEstado.equals("Todos") ||
                estadoCompraDeEstaEntrada.equalsIgnoreCase(filtroEstado);

        // 2. Evaluar coincidencia de campo de texto (Nombre del evento o ID de Ticket)
        if (busquedaTexto == null || busquedaTexto.trim().isEmpty()) {
            return coincideEstado; // Si no hay texto, solo importa el estado
        }

        String filtroMinusculas = busquedaTexto.toLowerCase();
        boolean coincideTexto = entrada.getEvento().getNombre().toLowerCase().contains(filtroMinusculas) ||
                entrada.getIdTicket().toLowerCase().contains(filtroMinusculas);

        return coincideEstado && coincideTexto;
    }

    @FXML
    public void limpiarFiltrosHistorial() {
        if (txtFiltroTexto != null) txtFiltroTexto.clear();
        if (comboFiltroEstado != null) comboFiltroEstado.setValue("Todos");
    }

    @FXML
    public void regresar() {
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/CompraView.fxml", "Cartelera de Eventos");
    }
}