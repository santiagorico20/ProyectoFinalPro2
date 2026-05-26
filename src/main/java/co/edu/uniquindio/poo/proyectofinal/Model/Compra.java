package co.edu.uniquindio.poo.proyectofinal.Model;

import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.IPagoStrategy; // 🔥 IMPORTANTE: Importamos tu interfaz Strategy
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private static final long serialVersionUID = 1L;
    private String idCompra;
    private LocalDate fechaCompra;
    private double total;
    private EstadoCompra estadoCompra;
    private Usuario usuario;
    private Pago pago; // Puedes mantenerlo si guarda el registro histórico de la transacción
    private List<Entrada> entradas;
    private List<ServicioAdicional> servicios;

    // 🔄 CONEXIÓN CON EL PATRÓN STRATEGY: El algoritmo de pago elegido dinámicamente
    private IPagoStrategy metodoPago;

    public Compra(String idCompra, LocalDate fechaCompra, Usuario usuario) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.usuario = usuario;
        this.estadoCompra = EstadoCompra.CREADA;

        this.entradas = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    // ============================================================
    // ⚙️ MÉTODO ESTRATÉGICO PARA PROCESAR EL PAGO
    // ============================================================

    /**
     * 🔥 Invoca la estrategia de pago inyectada para realizar el cobro del total.
     * Si la pasarela aprueba la transacción, la compra se confirma automáticamente.
     * @return true si el pago fue exitoso y la compra se confirmó.
     */
    public boolean procesarPagoConEstrategia() {
        if (metodoPago == null) {
            throw new IllegalStateException("⚠️ Error: No se ha seleccionado una estrategia de pago (Tarjeta o Transferencia).");
        }

        // Calculamos el total exacto actualizado antes de mandar a cobrar
        double montoACobrar = calcularTotal();

        // Ejecución polimórfica de la pasarela de pago
        boolean pagoAprobado = metodoPago.procesarPago(montoACobrar);

        if (pagoAprobado) {
            confirmarCompra(); // Pasa el estado a CONFIRMADA
            System.out.println("🎟️ [Compra] Pago verificado de forma exitosa. Transacción guardada.");
            return true;
        } else {
            System.out.println("❌ [Compra] La pasarela rechazó la transacción. Intente con otro método.");
            return false;
        }
    }

    // ============================================================
    // 📋 LÓGICA DE NEGOCIO EXISTENTE (INTACTA Y EXCELENTE)
    // ============================================================

    public void agregarEntrada(Entrada entrada){
        if (entrada != null) {
            entradas.add(entrada);
            calcularTotal();
        }
    }

    public void agregarServicio(ServicioAdicional servicio){
        if (servicio != null) {
            servicios.add(servicio);
            calcularTotal();
        }
    }

    public double calcularTotal(){
        double suma = 0;

        for (Entrada entrada : entradas){
            if (entrada != null) {
                suma += entrada.getPrecioFinal();
            }
        }

        for (ServicioAdicional servicio : servicios){
            if (servicio != null) {
                suma += servicio.getPrecio();
            }
        }

        this.total = suma;
        return this.total;
    }

    public void confirmarCompra(){
        this.estadoCompra = EstadoCompra.CONFIRMADA;
    }

    public void cancelarCompra(){
        this.estadoCompra = EstadoCompra.CANCELADA;
    }

    // --- GETTERS Y SETTERS COMPLEMENTARIOS ---

    /**
     * 🔥 SETTER CLAVE: Permite inyectar TarjetaStrategy o TransferenciaStrategy
     * desde tus controladores de JavaFX en tiempo de ejecución.
     */
    public void setMetodoPago(IPagoStrategy metodoPago) {
        this.metodoPago = metodoPago;
    }

    public IPagoStrategy getMetodoPago() {
        return metodoPago;
    }

    public String getIdCompra() { return idCompra; }
    public void setIdCompra(String idCompra) { this.idCompra = idCompra; }

    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }

    public double getTotal() {
        return calcularTotal();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoCompra getEstadoCompra() { return estadoCompra; }
    public void setEstadoCompra(EstadoCompra estadoCompra) { this.estadoCompra = estadoCompra; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Pago getPago() { return pago; }
    public void setPago(Pago pago) { this.pago = pago; }

    public List<Entrada> getEntradas() { return entradas; }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = (entradas != null) ? entradas : new ArrayList<>();
        calcularTotal();
    }

    public List<ServicioAdicional> getServicios() { return servicios; }

    public void setServicios(List<ServicioAdicional> servicios) {
        this.servicios = (servicios != null) ? servicios : new ArrayList<>();
        calcularTotal();
    }
}