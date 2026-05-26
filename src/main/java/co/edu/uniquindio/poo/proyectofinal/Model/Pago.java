package co.edu.uniquindio.poo.proyectofinal.Model;

import java.time.LocalDate;

public class Pago {
    private String idPago;
    private double monto;
    private LocalDate fechaPago;
    private MetodoPago metodoPago;
    private boolean reembolsado; // Atributo extra para control de auditoría

    public Pago(String idPago, double monto, LocalDate fechaPago, MetodoPago metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.reembolsado = false; // Todo pago nace sin ser reembolsado
    }

    /**
     * Procesa la transacción de forma segura protegiendo contra nulos.
     */
    public boolean procesarPago(){
        if (metodoPago == null) {
            System.err.println("Error Crítico: No se puede procesar el pago porque el Método de Pago es nulo.");
            return false;
        }
        return metodoPago.procesarPago(monto);
    }

    /**
     * Cambia el estado del pago a reembolsado si se cancela una compra.
     */
    public boolean reembolsarPago(){
        if (this.reembolsado) {
            System.out.println("El pago [" + idPago + "] ya había sido reembolsado previamente.");
            return false;
        }
        this.reembolsado = true;
        System.out.println("Pago [" + idPago + "] por un monto de $" + monto + " ha sido REEMBOLSADO con éxito.");
        return true;
    }

    // --- GETTERS Y SETTERS ---

    public String getIdPago() { return idPago; }
    public void setIdPago(String idPago) { this.idPago = idPago; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public boolean isReembolsado() { return reembolsado; }
    public void setReembolsado(boolean reembolsado) { this.reembolsado = reembolsado; }
}