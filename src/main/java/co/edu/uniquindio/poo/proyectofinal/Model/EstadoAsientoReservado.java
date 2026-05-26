package co.edu.uniquindio.poo.proyectofinal.Model;

public class EstadoAsientoReservado implements EstadoAsiento {
    private static final long serialVersionUID = 1L;
    @Override
    public void reservar(Asiento asiento) {
        System.out.println("⚠️ El asiento ya está reservado por otro usuario.");
    }

    @Override
    public void ocupar(Asiento asiento) {
        asiento.setEstado(new EstadoAsientoOcupado());
        System.out.println("🔒 Reserva confirmada. Asiento " + asiento.getCodigo() + " ahora está OCUPADO.");
    }

    @Override
    public void liberar(Asiento asiento) {
        asiento.setEstado(new EstadoAsientoDisponible());
        System.out.println("🟢 Tiempo límite expirado. Asiento " + asiento.getCodigo() + " vuelve a estar DISPONIBLE.");
    }

    @Override
    public String getNombreEstado() { return "RESERVADO"; }
}