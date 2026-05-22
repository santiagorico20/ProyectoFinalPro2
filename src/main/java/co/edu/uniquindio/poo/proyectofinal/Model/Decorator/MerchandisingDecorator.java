package co.edu.uniquindio.poo.proyectofinal.Model.Decorator;

import co.edu.uniquindio.poo.proyectofinal.Model.Entrada;

public class MerchandisingDecorator implements IEntradaDecorator {
    private double precioKit;

    private Entrada entrada;


    public MerchandisingDecorator(Entrada entrada, double precioKit) {

        this.entrada = entrada;
        this.precioKit = precioKit;
    }

    @Override
    public double calcularPrecio() {

        return entrada.getPrecioFinal() + precioKit;
    }

    @Override
    public String obtenerDescripcion() {

        return "Entrada con seguro";
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
