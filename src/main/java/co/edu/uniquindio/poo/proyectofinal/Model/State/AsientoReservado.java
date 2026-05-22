package co.edu.uniquindio.poo.proyectofinal.Model.State;

public class AsientoReservado implements IEstadoAsientoState {

    @Override
    public void reservar() {

        System.out.println("Asiento ya reservado");
    }

    @Override
    public void vender() {

        System.out.println("Asiento vendido");
    }

    @Override
    public void liberar() {

        System.out.println("Reserva liberada");
    }

}
