package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Compra;
import co.edu.uniquindio.poo.proyectofinal.Model.Entrada;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class MisComprasViewController {

    @FXML private TableView<Entrada> tablaMisEntradas;
    @FXML private TableColumn<Entrada, String> colTicket, colEvento, colFecha, colAsiento, colPrecio;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    public void initialize() {
        // Configuración de columnas
        colTicket.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioPagado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));

        // Propiedades calculadas (Sacar datos de objetos internos)
        colEvento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEvento().getNombre()));

        colAsiento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAsiento().getCodigo() + " (" + cellData.getValue().getZona().getNombre() + ")"));

        cargarDatos();
    }

    private void cargarDatos() {
        Usuario user = facade.getUsuarioAutenticado();
        if (user != null) {
            // 🎯 CORRECCIÓN: Desempacar las Entradas de la lista de Compras
            List<Entrada> todasMisEntradas = new ArrayList<>();

            for (Compra compra : user.getHistorialCompras()) {
                if (compra.getEntradas() != null) {
                    todasMisEntradas.addAll(compra.getEntradas());
                }
            }

            // Ahora sí le pasamos a la tabla una lista estrictamente de tipo Entrada
            tablaMisEntradas.setItems(FXCollections.observableArrayList(todasMisEntradas));
        }
    }

    @FXML
    public void regresar() {
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/CompraView.fxml", "Cartelera de Eventos");
    }
}