package co.edu.uniquindio.poo.proyectofinal.Model.Decorator;

import co.edu.uniquindio.poo.proyectofinal.Model.Entrada;

public class SeguroDecorator implements IEntradaDecorator {

    private double precioSeguro;

    private Entrada entrada;


    public SeguroDecorator(Entrada entrada, double precioSeguro) {

        this.entrada = entrada;
        this.precioSeguro = precioSeguro;
    }

    @Override
    public double calcularPrecio() {

        return entrada.getPrecioFinal() + precioSeguro;
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
