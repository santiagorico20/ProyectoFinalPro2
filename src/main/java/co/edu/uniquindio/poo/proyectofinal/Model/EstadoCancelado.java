package co.edu.uniquindio.poo.proyectofinal.Model;

public class EstadoCancelado implements EstadoEvento {
    private static final long serialVersionUID = 1L;

    @Override
    public void publicar(Evento evento) {
        System.out.println("❌ Error: Un evento cancelado no puede volver a publicarse.");
    }

    @Override
    public void pausar(Evento evento) {
        System.out.println("❌ Error: Un evento cancelado no se puede pausar.");
    }

    @Override
    public void cancelar(Evento evento) {
        System.out.println("⚠️ El evento ya estaba cancelado.");
    }

    @Override
    public String getNombreEstado() {
        return "Cancelado";
    }
}