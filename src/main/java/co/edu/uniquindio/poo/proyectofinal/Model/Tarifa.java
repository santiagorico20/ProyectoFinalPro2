package co.edu.uniquindio.poo.proyectofinal.Model;

public class Tarifa {

    private String idTarifa;
    private double precioBase;
    private double impuesto;   // Representa el porcentaje (Ej: 0.19 para el 19%)
    private double descuento;  // Representa el porcentaje (Ej: 0.10 para el 10%)

    public Tarifa(String idTarifa, double precioBase, double impuesto, double descuento) {
        this.idTarifa = idTarifa;
        this.precioBase = precioBase;
        this.impuesto = impuesto;
        this.descuento = descuento;
    }

    /**
     * Calcula el precio final aplicando de forma correcta las tasas porcentuales.
     * Fórmula: Precio Base + Valor Impuesto - Valor Descuento
     */
    public double calcularPrecioFinal(){
        double valorImpuesto = this.precioBase * this.impuesto;
        double valorDescuento = this.precioBase * this.descuento;

        return this.precioBase + valorImpuesto - valorDescuento;
    }

    /**
     * Permite actualizar la tasa de descuento aplicable (Ej: pasar de 0.0 a 0.15)
     */
    public void aplicarDescuento(double descuento){
        if (descuento >= 0 && descuento <= 1) {
            this.descuento = descuento;
        } else {
            System.err.println("Error: El descuento debe ser un porcentaje entre 0.0 y 1.0");
        }
    }

    // --- GETTERS Y SETTERS ---

    public String getIdTarifa() { return idTarifa; }
    public void setIdTarifa(String idTarifa) { this.idTarifa = idTarifa; }

    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }

    public double getImpuesto() { return impuesto; }
    public void setImpuesto(double impuesto) { this.impuesto = impuesto; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }
}