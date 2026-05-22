package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;

public class UsuarioController {

    private TaquilleriaVirtualFacade facade;

    public UsuarioController() {

        facade = new TaquilleriaVirtualFacade();
    }

    public void registrarUsuario(Usuario usuario){

        facade.registrarUsuario(usuario);
    }

    public void modificarPerfil(Usuario usuario, String nombre, String correo, String telefono){
        usuario.modificarPerfil(nombre, correo, telefono);
    }
}
