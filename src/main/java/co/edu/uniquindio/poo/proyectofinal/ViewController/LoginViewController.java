package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController {

    @FXML private TextField txtUsuario; // Puede ser el correo o la cédula
    @FXML private PasswordField txtContrasena;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    public void login() {
        String userInput = txtUsuario.getText().trim();
        String passwordInput = txtContrasena.getText().trim();

        if (userInput.isEmpty() || passwordInput.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, ingresa tus credenciales.", Alert.AlertType.WARNING);
            return;
        }

        // Buscar el usuario por ID o por Correo, y verificar contraseña
        Usuario usuario = facade.getUsuarios().stream()
                .filter(u -> (u.getIdUsuario().equals(userInput) || u.getCorreo().equalsIgnoreCase(userInput))
                        && u.getContrasena().equals(passwordInput))
                .findFirst()
                .orElse(null);

        if (usuario != null) {
            // Guardar la sesión activa en la Facade
            facade.setUsuarioAutenticado(usuario);
            System.out.println("🔐 Sesión iniciada para: " + usuario.getNombre() + " con Rol: " + usuario.getRol());

            // Redirección según el rol de la entidad
            if (usuario.getRol().equalsIgnoreCase("ADMIN")) {
                Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/AdminView.fxml", "Panel de Administración");
            } else {
                Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/CompraView.fxml", "Cartelera de Eventos");
            }
        } else {
            mostrarAlerta("Error de Autenticación", "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    // 🎯 NUEVO: Abre la interfaz de registro de usuarios
    @FXML
    public void irAPantallaRegistro() {
        System.out.println("📝 Navegando a la pantalla de registro...");
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/RegistroView.fxml", "Crear Cuenta - Nexus Tickets");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}