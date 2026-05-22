package co.edu.uniquindio.poo.proyectofinal.Model.State;

import co.edu.uniquindio.poo.proyectofinal.Model.EventoBuilder;

public class EventoCancelado implements IEstadoEventoState {
    @Override
    public boolean mostrarCartelera() {

        return true;
    }

    @Override
    public boolean permitirVenta() {

        return false;
    }

    @Override
    public void cancelarEvento(EventoBuilder eventoBuilder) {
        // Ya está cancelado, no hace nada
    }
}
