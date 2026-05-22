package co.edu.uniquindio.poo.proyectofinal.Model.Decorator;

import co.edu.uniquindio.poo.proyectofinal.Model.Entrada;

public class VIPSeguroDecorator implements IEntradaDecorator {
    private double precioVIP;

    private Entrada entrada;


    public  VIPSeguroDecorator(Entrada entrada, double precioVIP) {

        this.entrada = entrada;
        this.precioVIP = precioVIP;
    }

    @Override
    public double calcularPrecio() {

        return entrada.getPrecioFinal() + precioVIP;
    }

    @Override
    public String obtenerDescripcion() {

        return "Entrada con seguro VIP";
    }

    @Override
    public boolean validarAcceso() {

        return true;
    }

    @Override
    public void anularAcceso() {

        System.out.println("Entrada anulada");
    }
}
