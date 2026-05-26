package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import co.edu.uniquindio.poo.proyectofinal.Model.Administrador;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UsuarioController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasenia; // Simulada para validación

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    /**
     * Valida las credenciales del usuario y almacena la sesión en la Fachada.
     * @return true si la autenticación fue exitosa.
     */
    @FXML
    public boolean onIniciarSesion() {
        String correo = txtCorreo.getText();

        if (correo == null || correo.trim().isEmpty()) {
            mostrarAlerta("Campos Vacíos", "Por favor ingrese su correo electrónico.", Alert.AlertType.WARNING);
            return false;
        }

        // Buscamos el usuario en nuestra lista global
        for (Usuario u : facade.getListaUsuarios()) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                facade.setUsuarioAutenticado(u);

                if (u instanceof Administrador) {
                    mostrarAlerta("Bienvenido", "Sesión iniciada como Administrador: " + u.getNombre(), Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Bienvenido", "Sesión iniciada como Cliente: " + u.getNombre(), Alert.AlertType.INFORMATION);
                }
                return true;
            }
        }

        mostrarAlerta("Error", "Usuario no registrado en el sistema.", Alert.AlertType.ERROR);
        return false;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}