package co.edu.uniquindio.poo.proyectofinal.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * ENTIDAD: Representa al consumidor final con pasarela simulación y su historial.
 */
public class Cliente extends Usuario {

    private String telefono; // 🎯 Atributo propio que requiere el cliente
    private List<String> metodosPagoSimulados;

    // 🎯 ELIMINADO: Se borró 'historialCompras' de tipo Object porque ya se hereda correctamente de Usuario

    public Cliente(String idUsuario, String nombre, String correo, String telefono, String password) {
        // 🎯 CORREGIDO: Pasamos las variables en el orden exacto que pide el constructor de Usuario:
        // (idUsuario, nombre, correo, contrasena [password], rol ["CLIENTE"])
        super(idUsuario, nombre, correo, password, "CLIENTE");

        this.telefono = telefono;
        this.metodosPagoSimulados = new ArrayList<>();

        // Métodos iniciales por defecto (Simulación)
        this.metodosPagoSimulados.add("Visa **** 4321");
    }

    // --- GETTER Y SETTER DE TELÉFONO ---
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // --- GESTIÓN DE MÉTODOS DE PAGO (RF-021) ---
    public List<String> getMetodosPagoSimulados() {
        return metodosPagoSimulados;
    }

    public void agregarMetodoPago(String nuevoMetodo) {
        this.metodosPagoSimulados.add(nuevoMetodo);
    }

    public void eliminarMetodoPago(String metodo) {
        this.metodosPagoSimulados.remove(metodo);
    }

    // 🎯 ELIMINADO: El método getHistorialCompras() que devolvía List<Object>.
    // Ahora usa de forma transparente el método heredado del padre que devuelve List<Compra>.
}