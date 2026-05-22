package co.edu.uniquindio.poo.proyectofinal.Model.Strategy;

public class TransferenciaStrategy implements IPagoStrategy {

    @Override
    public boolean procesarPago(double monto) {

        System.out.println("Pago realizado por transferencia");

        return true;
    }

}
