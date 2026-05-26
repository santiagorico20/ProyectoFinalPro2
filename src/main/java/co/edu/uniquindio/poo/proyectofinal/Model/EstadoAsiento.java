package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.Serializable;

/**
 * PATRÓN STATE: Interfaz para los estados de un asiento individual.
 */
public interface EstadoAsiento extends Serializable {
    void reservar(Asiento asiento);
    void ocupar(Asiento asiento);
    void liberar(Asiento asiento);
    String getNombreEstado();
}