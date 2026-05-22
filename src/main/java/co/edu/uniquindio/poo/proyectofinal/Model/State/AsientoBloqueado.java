package co.edu.uniquindio.poo.proyectofinal.Model.State;

public class AsientoBloqueado implements IEstadoAsientoState {
    @Override
    public void reservar() {

        System.out.println("Asiento bloqueado");
    }

    @Override
    public void vender() {

        System.out.println("Asiento bloqueado");
    }

    @Override
    public void liberar() {

        System.out.println("Asiento desbloqueado");
    }
}
