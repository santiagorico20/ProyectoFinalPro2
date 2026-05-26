package co.edu.uniquindio.poo.proyectofinal.Model.Decorator;

/**
 * DECORADOR CONCRETO: Añade la opción de Kit de Merchandising a cualquier tipo de entrada.
 */
public class MerchandisingDecorator implements IEntrada {

    private double precioKit;
    private IEntrada entradaDecorada; // 🔥 CORREGIDO: Ahora usa la interfaz para permitir envolturas múltiples

    // El constructor recibe la interfaz general
    public MerchandisingDecorator(IEntrada entradaDecorada, double precioKit) {
        this.entradaDecorada = entradaDecorada;
        this.precioKit = precioKit;
    }

    /**
     * Calcula el precio acumulando el valor del kit al precio del objeto interno.
     */
    @Override
    public double calcularPrecio() {
        return entradaDecorada.calcularPrecio() + precioKit;
    }

    /**
     * 🔥 CORREGIDO: Acumula las descripciones en lugar de borrarlas.
     * Si envuelve una Entrada VIP, devolverá: "Entrada VIP + Kit de Merchandising"
     */
    @Override
    public String obtenerDescripcion() {
        return entradaDecorada.obtenerDescripcion() + " + Kit de Merchandising";
    }

    /**
     * 🔥 CORREGIDO: Delega la validación real al objeto interno para evitar fraudes.
     */
    @Override
    public boolean validarAcceso() {
        return entradaDecorada.validarAcceso();
    }

    /**
     * 🔥 CORREGIDO: Delega la anulación para que afecte al objeto real.
     */
    @Override
    public void anularAcceso() {
        entradaDecorada.anularAcceso();
    }

    // --- GETTERS Y SETTERS ---
    public double getPrecioKit() { return precioKit; }
    public void setPrecioKit(double precioKit) { this.precioKit = precioKit; }
    public IEntrada getEntradaDecorada() { return entradaDecorada; }
    public void setEntradaDecorada(IEntrada entradaDecorada) { this.entradaDecorada = entradaDecorada; }
}