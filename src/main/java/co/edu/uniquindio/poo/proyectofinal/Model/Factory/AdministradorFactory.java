package co.edu.uniquindio.poo.proyectofinal.Model.Factory;

import co.edu.uniquindio.poo.proyectofinal.Model.Administrador;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;

public class AdministradorFactory implements IUsuarioFactory{
    @Override
    public Usuario crearUsuario(String idUsuario, String nombre, String correo, String telefono) {
        System.out.println("🏭 Factory: Creando un nuevo usuario ADMINISTRADOR con privilegios...");
        return new Administrador(idUsuario, nombre, correo, telefono);
    }

}
