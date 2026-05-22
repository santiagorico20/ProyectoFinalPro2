package co.edu.uniquindio.poo.proyectofinal.Model;

public class Entrada {
    private String idEntrada;
    private EventoBuilder eventoBuilder;
    private Asiento asiento;
    private double precioFinal;

    private EstadoEntrada estadoEntrada;

    public Entrada(String idEntrada, EventoBuilder eventoBuilder, Asiento asiento, double precioFinal) {

        this.idEntrada = idEntrada;
        this.eventoBuilder = eventoBuilder;
        this.asiento = asiento;
        this.precioFinal = precioFinal;

        estadoEntrada = EstadoEntrada.ACTIVA;
    }

    public boolean validarAcceso(){

        return estadoEntrada == EstadoEntrada.ACTIVA;
    }

    public void anularEntrada(){

        estadoEntrada = EstadoEntrada.ANULADA;
    }
    public double calcularPrecioFinal(){

        return asiento.getZona().getTarifa().calcularPrecioFinal();
    }
  /// GET y SET

    public String getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(String idEntrada) {
        this.idEntrada = idEntrada;
    }

    public EventoBuilder getEvento() {
        return eventoBuilder;
    }

    public void setEvento(EventoBuilder eventoBuilder) {
        this.eventoBuilder = eventoBuilder;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public EstadoEntrada getEstadoEntrada() {
        return estadoEntrada;
    }

    public void setEstadoEntrada(EstadoEntrada estadoEntrada) {
        this.estadoEntrada = estadoEntrada;
    }


}
