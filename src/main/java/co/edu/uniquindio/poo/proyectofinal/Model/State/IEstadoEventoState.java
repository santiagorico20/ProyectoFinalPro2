package co.edu.uniquindio.poo.proyectofinal.Model.State;

import co.edu.uniquindio.poo.proyectofinal.Model.EventoBuilder;

public interface IEstadoEventoState {
    boolean mostrarCartelera();

    boolean permitirVenta();

    void cancelarEvento(EventoBuilder eventoBuilder);
}
