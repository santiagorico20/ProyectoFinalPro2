package co.edu.uniquindio.poo.proyectofinal.Model.Decorator;

/**
 * DECORADOR CONCRETO: Añade un estatus o seguro VIP adicional a cualquier entrada del sistema.
 */
public class VIPSeguroDecorator implements IEntrada {

    private double precioVIP;
    private IEntrada entradaDecorada; // 🔥 CORREGIDO: Ahora usa la interfaz general para envolturas dinámicas

    // El constructor recibe la interfaz general
    public VIPSeguroDecorator(IEntrada entradaDecorada, double precioVIP) {
        this.entradaDecorada = entradaDecorada;
        this.precioVIP = precioVIP;
    }

    /**
     * Suma el valor del extra VIP al costo total acumulado por los otros decoradores.
     */
    @Override
    public double calcularPrecio() {
        return entradaDecorada.calcularPrecio() + precioVIP;
    }

    /**
     * 🔥 CORREGIDO: Concatena la descripción para mantener el historial de modificaciones.
     * Resultado esperado: "Entrada General + Kit de Merchandising + Seguro VIP"
     */
    @Override
    public String obtenerDescripcion() {
        return entradaDecorada.obtenerDescripcion() + " + Seguro VIP";
    }

    /**
     * 🔥 CORREGIDO: Delega el control de acceso real para asegurar que no se salteen restricciones.
     */
    @Override
    public boolean validarAcceso() {
        return entradaDecorada.validarAcceso();
    }

    /**
     * 🔥 CORREGIDO: Propaga la anulación del ticket hasta la entidad base real.
     */
    @Override
    public void anularAcceso() {
        entradaDecorada.anularAcceso();
    }

    // --- GETTERS Y SETTERS ---
    public double getPrecioVIP() { return precioVIP; }
    public void setPrecioVIP(double precioVIP) { this.precioVIP = precioVIP; }
    public IEntrada getEntradaDecorada() { return entradaDecorada; }
    public void setEntradaDecorada(IEntrada entradaDecorada) { this.entradaDecorada = entradaDecorada; }
}