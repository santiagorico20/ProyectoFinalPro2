package co.edu.uniquindio.poo.proyectofinal.Model.Strategy;

import java.io.Serializable;

public interface IPagoStrategy extends Serializable {

    boolean procesarPago(double monto);

}
