package co.edu.uniquindio.poo.proyectofinal.Model;

public class Tarifa {

    private String idTarifa;

    private double precioBase;

    private double impuesto;

    private double descuento;

    public Tarifa(String idTarifa, double precioBase, double impuesto, double descuento) {

        this.idTarifa = idTarifa;
        this.precioBase = precioBase;
        this.impuesto = impuesto;
        this.descuento = descuento;
    }

    public double calcularPrecioFinal(){

        return precioBase + impuesto - descuento;
    }

    public void aplicarDescuento(double descuento){

        this.descuento = descuento;
    }

    /// GETS Y SETS
    public String getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(String idTarifa) {
        this.idTarifa = idTarifa;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
