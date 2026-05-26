package co.edu.uniquindio.poo.proyectofinal.Model;

import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.IPagoStrategy;

public class MetodoPago {
    private String idMetodoPago;
    private String titular;
    private IPagoStrategy estrategiaPago;

    public MetodoPago(String idMetodoPago, String titular, IPagoStrategy estrategiaPago) {
        this.idMetodoPago = idMetodoPago;
        this.titular = titular;
        this.estrategiaPago = estrategiaPago;
    }

    /**
     * Procesa el pago delegando la acción al patrón Strategy.
     * Incluye una validación de seguridad contra valores nulos.
     */
    public boolean procesarPago(double monto){
        if (estrategiaPago == null) {
            System.err.println("Error: No se ha definido una estrategia de pago válida.");
            return false;
        }
        return estrategiaPago.procesarPago(monto);
    }

    // --- GETTERS Y SETTERS COMPLETOS (Esenciales para JavaFX y lógica) ---

    public String getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(String idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public IPagoStrategy getEstrategiaPago() {
        return estrategiaPago;
    }

    public void setEstrategiaPago(IPagoStrategy estrategiaPago) {
        this.estrategiaPago = estrategiaPago;
    }
}