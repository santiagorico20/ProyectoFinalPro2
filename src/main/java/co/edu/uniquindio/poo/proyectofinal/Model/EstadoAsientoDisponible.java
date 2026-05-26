package co.edu.uniquindio.poo.proyectofinal.Model;

public class EstadoAsientoDisponible implements EstadoAsiento {
    private static final long serialVersionUID = 1L;
    @Override
    public void reservar(Asiento asiento) {
        asiento.setEstado(new EstadoAsientoReservado());
        System.out.println("⏳ Asiento " + asiento.getCodigo() + " reservado temporalmente.");
    }

    @Override
    public void ocupar(Asiento asiento) {
        asiento.setEstado(new EstadoAsientoOcupado());
        System.out.println("✅ Asiento " + asiento.getCodigo() + " comprado directamente.");
    }

    @Override
    public void liberar(Asiento asiento) {
        System.out.println("⚠️ El asiento ya está libre.");
    }

    @Override
    public String getNombreEstado() { return "DISPONIBLE"; }
}