package co.edu.uniquindio.poo.proyectofinal.Model.Observer;

/**
 * INTERFAZ OBSERVADOR: Define el contrato para los objetos que desean
 * recibir notificaciones sobre cambios en los temas que siguen (ej. Eventos).
 */
public interface IObserver {

    /**
     * Método que será invocado automáticamente por el sujeto observable.
     * @param mensaje Información o alerta sobre el cambio de estado.
     */
    void actualizarEstado(String mensaje);
}