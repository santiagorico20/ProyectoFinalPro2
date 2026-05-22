package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.Compra;
import co.edu.uniquindio.poo.proyectofinal.Model.Entrada;
import co.edu.uniquindio.poo.proyectofinal.Model.ServicioAdicional;

public class CompraController {
    private TaquilleriaVirtualFacade facade;

    public CompraController() {

        facade = new TaquilleriaVirtualFacade();
    }

    public void agregarEntrada(Compra compra,
                               Entrada entrada){

        compra.agregarEntrada(entrada);
    }

    public void agregarServicio(Compra compra,
                                ServicioAdicional servicio){

        compra.agregarServicio(servicio);
    }

    public double calcularTotal(Compra compra){

        return compra.calcularTotal();
    }

    public void confirmarCompra(Compra compra){

        compra.confirmarCompra();

        facade.registrarCompra(compra);
    }
}
