package co.edu.uniquindio.poo.proyectofinal.Model.State;


import co.edu.uniquindio.poo.proyectofinal.Model.EventoBuilder;

public class EventoBorrador implements IEstadoEventoState {

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
        // Aun no esta publicado
    }

}
