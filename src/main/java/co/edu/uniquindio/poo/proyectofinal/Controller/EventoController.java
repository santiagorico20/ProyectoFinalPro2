package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.EventoBuilder;
import co.edu.uniquindio.poo.proyectofinal.Model.State.IEstadoEventoState;

public class EventoController {
    private TaquilleriaVirtualFacade facade;

    public EventoController() {

        facade = new TaquilleriaVirtualFacade();
    }

    public void crearEvento(EventoBuilder evento){

        facade.agregarEvento(evento);
    }

    public void cambiarEstado(EventoBuilder evento, IEstadoEventoState nuevoEstado){

        evento.cambiarEstado(nuevoEstado);
    }
}
