package co.edu.uniquindio.poo.proyectofinal.Model.Factory;

import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;

public interface IUsuarioFactory {
    // 🔐 Corregido: Ahora la firma del método exige los 5 parámetros obligatorios
    Usuario crearUsuario(String id, String nombre, String correo, String telefono, String contrasena);
}