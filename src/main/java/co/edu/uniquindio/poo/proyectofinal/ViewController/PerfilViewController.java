package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.Persistencia;
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
    @FXML private PasswordField txtPassword;

    private TaquillaVirtualFacade sistema = TaquillaVirtualFacade.getInstancia();
    private Usuario usuarioLogueado;

    @FXML
    public void initialize() {
        // 1. Obtenemos el usuario que está usando la app actualmente
        usuarioLogueado = sistema.getUsuarioLogueado();

        if (usuarioLogueado != null) {
            // 2. Llenamos los campos de la interfaz con sus datos actuales
            txtNombre.setText(usuarioLogueado.getNombre());
            txtCorreo.setText(usuarioLogueado.getCorreo());
            txtPassword.setText(usuarioLogueado.getPassword());

            // Opcional: Bloqueamos el correo para que no lo cambie si es su ID de login
            txtCorreo.setEditable(false);
        } else {
            mostrarAlerta("Error", "No hay ninguna sesión activa.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void guardarCambios() {
        if (usuarioLogueado == null) return;

        // 1. Validar que los campos no estén vacíos
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaPassword = txtPassword.getText().trim();

        if (nuevoNombre.isEmpty() || nuevaPassword.isEmpty()) {
            mostrarAlerta("Campos Vacíos", "Por favor llena todos los campos.", Alert.AlertType.WARNING);
            return;
        }

        // 2. Modificar los datos del objeto que está en la memoria del Singleton
        usuarioLogueado.setNombre(nuevoNombre);
        usuarioLogueado.setPassword(nuevaPassword);

        // 3. 🔥 EL PASO MAESTRO: Guardar inmediatamente en el archivo binario
        Persistencia.guardarSistema(sistema);

        mostrarAlerta("Éxito", "Perfil actualizado correctamente de forma permanente.", Alert.AlertType.INFORMATION);
    }

    @FXML
    void volver() {
        // Redirecciona según el rol para que no se pierda
        if (usuarioLogueado != null && "ADMIN".equals(usuarioLogueado.getRol())) {
            Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/AdminView.fxml", "Panel de Administrador");
        } else {
            Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/ClienteView.fxml", "Panel de Cliente");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}