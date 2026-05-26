package co.edu.uniquindio.poo.proyectofinal.Model.Decorator;

/**
 * INTERFAZ COMPONENTE: Define las operaciones que pueden ser alteradas
 * dinámicamente por los decoradores (los extras de la entrada).
 */
public interface IEntrada {

    /**
     * Calcula el precio sumando el valor base más los extras que se le añadan.
     */
    double calcularPrecio();

    /**
     * Devuelve el texto acumulado de la entrada (Ej: "Entrada VIP + Parqueadero + Backstage").
     */
    String obtenerDescripcion();

    /**
     * Valida si la entrada es apta para ingresar al recinto o zona.
     */
    boolean validarAcceso();

    /**
     * Quema o anula el acceso de la entrada (por ejemplo, al ser escaneada en la puerta).
     */
    void anularAcceso();
}