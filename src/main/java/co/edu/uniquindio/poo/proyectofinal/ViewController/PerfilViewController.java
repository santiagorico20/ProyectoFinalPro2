package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PerfilViewController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private PasswordField txtPassword;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        // Extraemos el usuario que tiene la sesión activa usando tu método real
        usuarioActual = facade.getUsuarioAutenticado();

        if (usuarioActual != null) {
            // Llenamos los campos con la información actual del usuario
            txtNombre.setText(usuarioActual.getNombre());
            txtCorreo.setText(usuarioActual.getCorreo());
            txtTelefono.setText(usuarioActual.getTelefono());
            txtPassword.setText(usuarioActual.getContrasena()); // Tu atributo se llama getContrasena()

            // Bloqueamos el correo para que no lo altere (suele ser el ID de inicio de sesión)
            txtCorreo.setEditable(false);
        } else {
            mostrarAlerta("Error de Sesión", "No se encontró ningún usuario autenticado.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void guardarCambios() {
        if (usuarioActual == null) return;

        String nuevoNombre = txtNombre.getText().trim();
        String nuevoTelefono = txtTelefono.getText().trim();
        String nuevaPassword = txtPassword.getText().trim();

        if (nuevoNombre.isEmpty() || nuevoTelefono.isEmpty() || nuevaPassword.isEmpty()) {
            mostrarAlerta("Campos Vacíos", "Por favor, completa todos los campos editables.", Alert.AlertType.WARNING);
            return;
        }

        // ✅ CORREGIDO: Descomentados y aplicados los cambios reales al objeto usuario en memoria
        usuarioActual.setNombre(nuevoNombre);
        usuarioActual.setTelefono(nuevoTelefono);
        usuarioActual.setContrasena(nuevaPassword);

        // 🔥 CONGELAMOS EN EL DISCO AUTOMÁTICAMENTE
        facade.resguardarEstado();

        mostrarAlerta("Éxito", "Tu perfil ha sido actualizado de forma permanente.", Alert.AlertType.INFORMATION);
    }

    @FXML
    void volver() {
        if (usuarioActual == null) {
            Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/LoginView.fxml", "Nexus Tickets - Iniciar Sesión");
            return;
        }

        // ✅ CORREGIDO: Redirección inteligente al lugar exacto de donde vino
        if ("ADMIN".equals(usuarioActual.getRol())) {
            Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/AdminView.fxml", "Panel de Administrador");
        } else {
            // Te regresa limpiamente a la Cartelera Negra de eventos, no al mapa de asientos
            Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/CompraView.fxml", "Nexus Tickets - Cartelera de Eventos");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}