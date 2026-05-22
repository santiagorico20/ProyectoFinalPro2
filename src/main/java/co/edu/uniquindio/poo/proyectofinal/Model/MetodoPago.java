package co.edu.uniquindio.poo.proyectofinal.Model;

import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.IPagoStrategy;

public class MetodoPago {
    private String idMetodoPago;
    private String titular;

    private IPagoStrategy estrategiaPago;

    public MetodoPago(String idMetodoPago,
                      String titular,
                      IPagoStrategy estrategiaPago) {

        this.idMetodoPago = idMetodoPago;
        this.titular = titular;
        this.estrategiaPago = estrategiaPago;
    }

    public boolean procesarPago(double monto){

        return estrategiaPago.procesarPago(monto);
    }
}

