package co.edu.uniquindio.poo.proyectofinal.Model.Decorator;

public interface IEntradaDecorator {

    double calcularPrecio();

    String obtenerDescripcion();

    boolean validarAcceso();

    void anularAcceso();

}
