package co.edu.uniquindio.poo.proyectofinal.Model;

/**
 * ENTIDAD: Usuario con credenciales operativas para gestionar el backend del negocio.
 */
public class Administrador extends Usuario {

    public Administrador(String idUsuario, String nombre, String correo, String telefono, String password) {
        super(idUsuario, nombre, correo, telefono, password);
    }

    @Override
    public String getRol() {
        return "ADMINISTRADOR";
    }
}