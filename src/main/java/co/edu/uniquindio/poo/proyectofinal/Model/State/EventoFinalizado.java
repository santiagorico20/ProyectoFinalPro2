package co.edu.uniquindio.poo.proyectofinal.Model.State;

import co.edu.uniquindio.poo.proyectofinal.Model.EventoBuilder;

public class EventoFinalizado implements IEstadoEventoState {
    @Override
    public boolean mostrarCartelera() {

        return false;
    }

    @Override
    public boolean permitirVenta() {

        return false;
    }

    @Override
    public void cancelarEvento(EventoBuilder eventoBuilder) {
        // Ya está finalizado, no hace nada
    }
}