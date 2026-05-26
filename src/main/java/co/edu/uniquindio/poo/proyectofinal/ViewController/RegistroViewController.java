package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.Persistencia; // 🔥 IMPORTANTE: Importamos la persistencia
import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import co.edu.uniquindio.poo.proyectofinal.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroViewController {

    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;

    // 🎯 COMPONENTES: Para elegir el rol desde la interfaz
    @FXML private ComboBox<String> comboRol;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    public void initialize() {
        // Llenamos el combo con las dos opciones disponibles
        comboRol.getItems().addAll("CLIENTE", "ADMIN");
        // Dejamos CLIENTE seleccionado por defecto para ahorrar clics
        comboRol.setValue("CLIENTE");
    }

    @FXML
    public void registrarUsuario() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        String rolAsignado = comboRol.getValue(); // 🎯 Leemos el rol seleccionado

        // 1. Validar campos vacíos
        if (cedula.isEmpty() || nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rolAsignado == null) {
            mostrarAlerta("Campos requeridos", "Por favor, completa todos los campos del formulario.", Alert.AlertType.WARNING);
            return;
        }

        // 2. Verificar duplicados (Cédula o Correo)
        boolean yaExiste = facade.getUsuarios().stream()
                .anyMatch(u -> u.getIdUsuario().equals(cedula) || u.getCorreo().equalsIgnoreCase(correo));

        if (yaExiste) {
            mostrarAlerta("Usuario duplicado", "Ya existe un usuario registrado con esa cédula o correo electrónico.", Alert.AlertType.ERROR);
            return;
        }

        // 3. Crear el nuevo usuario con el rol que el usuario escogió libremente
        // 💡 Nota: Usamos el constructor de 5 parámetros. Asegúrate de tener el constructor sobrecargado en Usuario.java
        Usuario nuevoUsuario = new Usuario(cedula, nombre, correo, contrasena, rolAsignado);
        facade.getUsuarios().add(nuevoUsuario);

        // 🔥 LA PIEZA MAESTRA CLAVE: Guardamos todo el sistema de inmediato en el archivo binario
        Persistencia.guardarSistema(facade);

        mostrarAlerta("¡Registro Exitoso!",
                "Cuenta creada correctamente con el rol de: " + rolAsignado + ".\nAhora puedes iniciar sesión.",
                Alert.AlertType.INFORMATION);

        // 4. Redireccionar al Login
        volverAlLogin();
    }

    @FXML
    public void volverAlLogin() {
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/LoginView.fxml", "Iniciar Sesión");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}