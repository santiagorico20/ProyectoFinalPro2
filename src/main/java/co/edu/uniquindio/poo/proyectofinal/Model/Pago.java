package co.edu.uniquindio.poo.proyectofinal.Model;

import java.time.LocalDate;

public class Pago {
    private String idPago;

    private double monto;

    private LocalDate fechaPago;

    private MetodoPago metodoPago;

    public Pago(String idPago, double monto, LocalDate fechaPago, MetodoPago metodoPago) {

        this.idPago = idPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
    }

    public boolean procesarPago(){

        return metodoPago.procesarPago(monto);
    }

    public void reembolsarPago(){

        System.out.println("Pago reembolsado");
    }


    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
}
