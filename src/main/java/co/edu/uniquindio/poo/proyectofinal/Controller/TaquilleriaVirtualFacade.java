package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.*;

public class TaquilleriaVirtualFacade {

    private TaquilleriaVirtual taquilleriaVirtual;

    public TaquilleriaVirtualFacade() {

        this.taquilleriaVirtual = TaquilleriaVirtual.getInstance();
    }

    // =========================
    // USUARIOS
    // =========================

    public void registrarUsuario(Usuario usuario){

        taquilleriaVirtual.agregarUsuario(usuario);
    }

    // =========================
    // EVENTOS
    // =========================

    public void agregarEvento(EventoBuilder evento){

        taquilleriaVirtual.agregarEvento(evento);
    }

    // =========================
    // COMPRAS
    // =========================

    public void registrarCompra(Compra compra){

        taquilleriaVirtual.agregarCompra(compra);
    }

    // =========================
    // INCIDENCIAS
    // =========================

    public void registrarIncidencia(Incidencia incidencia){

        taquilleriaVirtual.agregarIncidencia(incidencia);
    }

    public TaquilleriaVirtual getTaquilleriaVirtual() {
        return taquilleriaVirtual;
    }
}
