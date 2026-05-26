package co.edu.uniquindio.poo.proyectofinal.Model.Factory;

import co.edu.uniquindio.poo.proyectofinal.Model.Cliente; // Tu subclase Cliente
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;

public class ClienteFactory implements IUsuarioFactory {

    @Override
    public Usuario crearUsuario(String id, String nombre, String correo, String telefono, String contrasena) {
        // 🔥 CORREGIDO: Retorna un Cliente real con los parámetros en el orden exacto del constructor base
        // Orden base: idUsuario, contraseña, nombre, correo, telefono
        return new Cliente(id, contrasena, nombre, correo, telefono);
    }
}