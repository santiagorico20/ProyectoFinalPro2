package co.edu.uniquindio.poo.proyectofinal.Model.Decorator;

/**
 * DECORADOR CONCRETO: Añade de forma dinámica un seguro de cancelación/asistencia a la entrada.
 */
public class SeguroDecorator implements IEntrada {

    private double precioSeguro;
    private IEntrada entradaDecorada; // 🔥 CORREGIDO: Ahora usa la interfaz general para permitir envolturas múltiples

    // El constructor recibe la interfaz general
    public SeguroDecorator(IEntrada entradaDecorada, double precioSeguro) {
        this.entradaDecorada = entradaDecorada;
        this.precioSeguro = precioSeguro;
    }

    /**
     * Acumula el precio del seguro al costo total acumulado del objeto interno.
     */
    @Override
    public double calcularPrecio() {
        return entradaDecorada.calcularPrecio() + precioSeguro;
    }

    /**
     * 🔥 CORREGIDO: Concatena la descripción para no perder el rastro de los extras.
     * Resultado esperado: "Entrada VIP + Kit de Merchandising + Seguro"
     */
    @Override
    public String obtenerDescripcion() {
        return entradaDecorada.obtenerDescripcion() + " + Seguro";
    }

    /**
     * 🔥 CORREGIDO: Delega la validación real al objeto interno para mantener la seguridad.
     */
    @Override
    public boolean validarAcceso() {
        return entradaDecorada.validarAcceso();
    }

    /**
     * 🔥 CORREGIDO: Transmite la orden de anulación hasta el objeto base real.
     */
    @Override
    public void anularAcceso() {
        entradaDecorada.anularAcceso();
    }

    // --- GETTERS Y SETTERS ---
    public double getPrecioSeguro() { return precioSeguro; }
    public void setPrecioSeguro(double precioSeguro) { this.precioSeguro = precioSeguro; }
    public IEntrada getEntradaDecorada() { return entradaDecorada; }
    public void setEntradaDecorada(IEntrada entradaDecorada) { this.entradaDecorada = entradaDecorada; }
}