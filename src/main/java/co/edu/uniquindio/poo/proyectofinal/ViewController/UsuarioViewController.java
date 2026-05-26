package co.edu.uniquindio.poo.proyectofinal.ViewController;

import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UsuarioViewController {

    @FXML private Label lblNombreUsuario;
    @FXML private Label lblCorreoUsuario;
    @FXML private Label lblTelefonoUsuario;
    @FXML private Label lblIdUsuario;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    public void initialize() {
        Usuario activo = facade.getUsuarioAutenticado();
        if (activo != null) {
            lblIdUsuario.setText(activo.getIdUsuario());
            lblNombreUsuario.setText(activo.getNombre());
            lblCorreoUsuario.setText(activo.getCorreo());
            lblTelefonoUsuario.setText(activo.getTelefono());
        }
    }
}