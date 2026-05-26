package co.edu.uniquindio.poo.proyectofinal.Model;

public class EstadoPublicado implements EstadoEvento {
    private static final long serialVersionUID = 1L;
    @Override
    public void publicar(Evento evento) {
        System.out.println("⚠️ El evento ya se encuentra publicado.");
    }

    @Override
    public void pausar(Evento evento) {
        // Aquí iría a EstadoPausado, pero para simplificar lo dejamos pendiente
        System.out.println("⏸️ Evento pausado temporalmente (Nadie puede comprar boletas).");
    }

    @Override
    public void cancelar(Evento evento) {
        evento.setEstadoActual(new EstadoCancelado());
        System.out.println("🚫 Evento cancelado. Se debe iniciar proceso de reembolsos.");
    }

    @Override
    public String getNombreEstado() {
        return "Publicado";
    }
}