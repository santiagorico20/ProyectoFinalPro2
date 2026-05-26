package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Incidencia;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class VerIncidenciasAdminViewController {

    @FXML private ListView<Incidencia> listViewIncidencias;
    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    public void initialize() {
        cargarIncidencias();
    }

    private void cargarIncidencias() {
        if (facade.getListaIncidencias() != null) {
            ObservableList<Incidencia> incidenciasObservable = FXCollections.observableArrayList(facade.getListaIncidencias());
            listViewIncidencias.setItems(incidenciasObservable);
        }
    }

    @FXML
    public void marcarComoResuelta(ActionEvent event) {
        Incidencia seleccionada = listViewIncidencias.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            if (seleccionada.isResuelta()) {
                mostrarAlerta("Aviso", "Esta incidencia ya estaba resuelta.", Alert.AlertType.INFORMATION);
                return;
            }

            // Cerramos la incidencia usando el método del modelo
            seleccionada.cerrarIncidencia();

            // 🔥 PERSISTENCIA: Guardamos el cambio de estado en el archivo binario
            facade.resguardarEstado();

            // Refrescamos la lista visualmente
            listViewIncidencias.refresh();

            mostrarAlerta("Actualizada", "La incidencia " + seleccionada.getIdIncidencia() + " ha sido marcada como RESUELTA y guardada.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Atención", "Selecciona una incidencia de la lista primero.", Alert.AlertType.WARNING);
        }
    }

    // 🎯 CORREGIDO: Volver usando la navegación estática consistente
    @FXML
    void regresar(ActionEvent event) {
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/AdminView.fxml", "Panel de Administrador");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}