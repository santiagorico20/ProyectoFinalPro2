package co.edu.uniquindio.poo.proyectofinal.Model;

import co.edu.uniquindio.poo.proyectofinal.Model.State.EventoPublicado;

public class Administrador extends Usuario {
    public Administrador(String idUsuario, String nombre, String correo, String telefono) {

        super(idUsuario, nombre, correo, telefono);
    }

    public void publicarEvento(EventoBuilder eventoBuilder) {
        System.out.println("Administrador " + this.getNombre() + " está publicando el evento: " + eventoBuilder.getNombre());

        // El administrador le ordena al evento cambiar su estado usando el patrón State
        eventoBuilder.setEstadoEventoActual(new EventoPublicado());

        // Opcional: Puede disparar una alerta de que hay un nuevo evento disponible
        eventoBuilder.notificarObservadores("¡Nuevo evento publicado! " + eventoBuilder.getNombre() + " ya está en cartelera.");
    }

    public void cancelarEvento(EventoBuilder eventoBuilder) {
        System.out.println("Administrador " + this.getNombre() + " ha ordenado la cancelación del evento: " + eventoBuilder.getNombre());

        // El administrador delega la acción; el Evento sabrá qué hacer según su estado actual
        eventoBuilder.cancelar();
    }


}
