package co.edu.uniquindio.poo.proyectofinal.Model.Strategy;

/**
 * ESTRATEGIA CONCRETA: Procesa pagos utilizando tarjetas de crédito o débito.
 */
public class TarjetaStrategy implements IPagoStrategy {
    private static final long serialVersionUID = 1L;

    private String numeroTarjeta;
    private String titular;

    /**
     * Constructor para inicializar los datos mínimos de la tarjeta.
     */
    public TarjetaStrategy(String numeroTarjeta, String titular) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
    }

    @Override
    public boolean procesarPago(double monto) {
        // Validación de negocio preventiva
        if (monto <= 0) {
            System.out.println("❌ [Tarjeta] El monto a pagar debe ser mayor a cero.");
            return false;
        }

        System.out.println("💳 [Strategy] Procesando pago de $" + monto + " con Tarjeta.");
        System.out.println("   Titular: " + titular + " | Tarjeta: ****" +
                (numeroTarjeta.length() > 4 ? numeroTarjeta.substring(numeroTarjeta.length() - 4) : "1234"));
        System.out.println("✅ [Strategy] Transacción autorizada exitosamente.");
        return true;
    }
}