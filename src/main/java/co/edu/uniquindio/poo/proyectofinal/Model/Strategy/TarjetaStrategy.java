package co.edu.uniquindio.poo.proyectofinal.Model.Strategy;

public class TarjetaStrategy implements IPagoStrategy {

    @Override
    public boolean procesarPago(double monto) {

        System.out.println("Pago realizado con tarjeta");

        return true;
    }

}
