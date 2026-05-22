package co.edu.uniquindio.poo.proyectofinal.Model.Factory;

import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;

public interface IUsuarioFactory {
    /**
     * Método central del patrón Factory para instanciar usuarios.
     */
    Usuario crearUsuario(String idUsuario, String nombre, String correo, String telefono);
}
