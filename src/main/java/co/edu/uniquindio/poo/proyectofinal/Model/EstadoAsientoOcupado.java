package co.edu.uniquindio.poo.proyectofinal.Model;

public class EstadoAsientoOcupado implements EstadoAsiento {
    private static final long serialVersionUID = 1L;
    @Override
    public void reservar(Asiento asiento) { System.out.println("❌ Imposible: Asiento ya vendido."); }
    @Override
    public void ocupar(Asiento asiento) { System.out.println("❌ Imposible: Asiento ya vendido."); }
    @Override
    public void liberar(Asiento asiento) {
        // En caso de cancelación de evento o devolución aprobada
        asiento.setEstado(new EstadoAsientoDisponible());
        System.out.println("♻️ Asiento liberado por el sistema.");
    }

    @Override
    public String getNombreEstado() { return "OCUPADO"; }
}