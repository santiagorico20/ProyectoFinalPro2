package co.edu.uniquindio.poo.proyectofinal.Model.State;

import co.edu.uniquindio.poo.proyectofinal.Model.EventoBuilder;

public class EventoPausado implements IEstadoEventoState {
    @Override
    public boolean mostrarCartelera() {

        return true;
    }

    @Override
    public boolean permitirVenta() {

        return false;
    }

    public void cancelarEvento(EventoBuilder eventoBuilder) {
        eventoBuilder.setEstadoEventoActual(new EventoCancelado());
        eventoBuilder.notificarObservadores("El evento '" + eventoBuilder.getNombre() + "' ha sido CANCELADO. Se procesarán reembolsos.");
    }
}
