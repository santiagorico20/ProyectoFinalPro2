package co.edu.uniquindio.poo.proyectofinal.Model;

public class EstadoBorrador implements EstadoEvento {

    private static final long serialVersionUID = 1L;

    @Override
    public void publicar(Evento evento) {
        evento.setEstadoActual(new EstadoPublicado());
        System.out.println("✅ El evento ahora está PUBLICADO y visible para los clientes.");
    }

    @Override
    public void pausar(Evento evento) {
        System.out.println("⚠️ No puedes pausar un evento que aún es un borrador.");
    }

    @Override
    public void cancelar(Evento evento) {
        evento.setEstadoActual(new EstadoCancelado());
        System.out.println("🚫 El evento en borrador ha sido cancelado.");
    }

    @Override
    public String getNombreEstado() {
        return "Borrador";
    }
}