package co.edu.uniquindio.poo.proyectofinal.Model.Strategy;

/**
 * ESTRATEGIA CONCRETA: Procesa pagos simulando una transferencia bancaria (ej. PSE).
 */
public class TransferenciaStrategy implements IPagoStrategy {
    private static final long serialVersionUID = 1L;

    private String banco;
    private String numeroCuenta;

    /**
     * Constructor para inicializar los datos de la transferencia.
     */
    public TransferenciaStrategy(String banco, String numeroCuenta) {
        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
    }

    @Override
    public boolean procesarPago(double monto) {
        // Validación de negocio preventiva
        if (monto <= 0) {
            System.out.println("❌ [Transferencia] El monto a pagar debe ser mayor a cero.");
            return false;
        }

        System.out.println("🏦 [Strategy] Conectando con la pasarela bancaria de: " + banco);
        System.out.println("   Verificando fondos en la cuenta No: " + numeroCuenta + "...");
        System.out.println("✅ [Strategy] Débito de $" + monto + " realizado con éxito por transferencia.");
        return true;
    }
}