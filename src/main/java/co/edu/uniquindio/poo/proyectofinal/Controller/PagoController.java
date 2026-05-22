package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.Pago;

public class PagoController {

    public boolean procesarPago(Pago pago){

        return pago.procesarPago();
    }

    public void reembolsarPago(Pago pago){

        pago.reembolsarPago();
    }
}
