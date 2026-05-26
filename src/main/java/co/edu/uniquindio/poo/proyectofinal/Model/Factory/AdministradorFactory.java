package co.edu.uniquindio.poo.proyectofinal.Model.Factory;

import co.edu.uniquindio.poo.proyectofinal.Model.Administrador;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;

/**
 * PATRÓN FACTORY METHOD: Fábrica concreta para crear Administradores.
 * ✅ SOLUCIONADO: Implementa el contrato con los 5 argumentos requeridos por IUsuarioFactory.
 */
public class AdministradorFactory implements IUsuarioFactory {

    /**
     * 🔥 MÉTODO IMPLEMENTADO EN SU TOTALIDAD: Cumple con la firma exacta de la interfaz
     * e instancía al Administrador pasando todos sus datos correspondientes.
     */
    @Override
    public Usuario crearUsuario(String idUsuario, String nombre, String correo, String contrasenia, String telefono) {
        System.out.println("🏭 [AdministradorFactory] Fabricando instancia con 5 parámetros...");

        // Retornamos el nuevo Administrador pasándole los 5 datos que entran por la interfaz
        return new Administrador(idUsuario, nombre, correo, contrasenia, telefono);
    }
}