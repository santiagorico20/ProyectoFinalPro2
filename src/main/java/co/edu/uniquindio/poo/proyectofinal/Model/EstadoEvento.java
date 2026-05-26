package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.Serializable;

/**
 * PATRÓN STATE: Interfaz que define las transiciones posibles de un evento.
 */
public interface EstadoEvento extends Serializable {
    void publicar(Evento evento);
    void pausar(Evento evento);
    void cancelar(Evento evento);
    String getNombreEstado();
}