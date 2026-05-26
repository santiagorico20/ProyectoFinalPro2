package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Incidencia;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.util.UUID;

public class ReportarIncidenciaViewController {

    @FXML private TextField txtAsunto;
    @FXML private TextArea txtDescripcion;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    void enviarIncidencia(ActionEvent event) {
        String asunto = txtAsunto.getText();
        String descripcion = txtDescripcion.getText();

        if (asunto == null || asunto.trim().isEmpty() || descripcion == null || descripcion.trim().isEmpty()) {
            mostrarAlerta("Error", "Por favor completa todos los campos (Asunto y Descripción).", Alert.AlertType.ERROR);
            return;
        }

        // 1. Obtener el usuario autenticado en la sesión
        Usuario usuarioActual = facade.getUsuarioAutenticado();

        // 2. Generar un código único e identificable para la incidencia (Ej: INC-A3F12)
        String idIncidencia = "INC-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        // 3. Unir el asunto y la descripción para no perder datos, ya que el modelo maneja "descripcion"
        String cuerpoCompleto = "Asunto: " + asunto + " | Detalle: " + descripcion;

        // 4. Crear la instancia de Incidencia respetando los tipos de tu modelo
        Incidencia nuevaIncidencia = new Incidencia(idIncidencia, cuerpoCompleto, LocalDate.now(), usuarioActual);

        // 5. Registrar en la fachada (esto activa automáticamente el resguardo de persistencia)
        facade.registrarIncidencia(nuevaIncidencia);

        mostrarAlerta("Éxito", "Tu incidencia ha sido reportada y enviada al administrador.", Alert.AlertType.INFORMATION);

        // Devolvemos al cliente a su pantalla principal automáticamente tras enviar
        regresar(event);
    }

    @FXML
    void regresar(ActionEvent event) {
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/CompraView.fxml", "Cartelera de Eventos - Nexus Tickets");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}