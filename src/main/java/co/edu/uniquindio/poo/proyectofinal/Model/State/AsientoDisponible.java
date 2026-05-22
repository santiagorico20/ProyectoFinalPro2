package co.edu.uniquindio.poo.proyectofinal.Model.State;

public class AsientoDisponible implements IEstadoAsientoState {
    @Override
    public void reservar() {

        System.out.println("Asiento reservado");
    }

    @Override
    public void vender() {

        System.out.println("Asiento vendido");
    }

    @Override
    public void liberar() {

        System.out.println("Asiento ya disponible");
    }
}
