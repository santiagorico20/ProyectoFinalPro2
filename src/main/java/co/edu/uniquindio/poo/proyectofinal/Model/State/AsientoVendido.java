package co.edu.uniquindio.poo.proyectofinal.Model.State;

public class AsientoVendido implements IEstadoAsientoState {

    @Override
    public void reservar() {

        System.out.println("No disponible");
    }

    @Override
    public void vender() {

        System.out.println("Ya vendido");
    }

    @Override
    public void liberar() {

        System.out.println("No puede liberarse");
    }

}
