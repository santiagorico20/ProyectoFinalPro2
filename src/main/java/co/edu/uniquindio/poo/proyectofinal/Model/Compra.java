package co.edu.uniquindio.poo.proyectofinal.Model;

import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.IPagoStrategy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

// 🌟 CRÍTICO: Añadimos implements Serializable para que se guarde en el disco
public class Compra implements Serializable {

    // ID de control para la serialización binaria sin errores
    private static final long serialVersionUID = 1L;

    private String idCompra;
    private LocalDate fechaCompra;
    private double total;
    private EstadoCompra estadoCompra;
    private Usuario usuario;
    private Pago pago;
    private List<Entrada> entradas;
    private List<ServicioAdicional> servicios;

    // El transient evita fallos de guardado si la estrategia externa no fuera serializable
    private transient IPagoStrategy metodoPago;

    public Compra(String idCompra, LocalDate fechaCompra, Usuario usuario) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.usuario = usuario;
        this.estadoCompra = EstadoCompra.CREADA; // Nace en CREADA

        this.entradas = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    // ============================================================
    // 🔥 CONTROL DE FLUJO Y POLÍTICAS DE MODIFICACIÓN (RF-006 / RF-035)
    // ============================================================

    /**
     * RF-006 y RF-035: Permite remover una entrada antes de pagar.
     * Si la compra ya no está en estado CREADA, bloquea la acción.
     */
    public boolean eliminarEntradaDeCarrito(Entrada entrada) {
        if (this.estadoCompra != EstadoCompra.CREADA) {
            System.out.println("❌ RF-035: No se puede modificar una compra que ya fue procesada/pagada.");
            return false;
        }
        if (entradas.remove(entrada)) {
            calcularTotal();
            return true;
        }
        return false;
    }

    /**
     * RF-035: Permite remover un servicio adicional antes de pagar.
     */
    public boolean eliminarServicioDeCarrito(ServicioAdicional servicio) {
        if (this.estadoCompra != EstadoCompra.CREADA) {
            System.out.println("❌ RF-035: No se puede modificar una compra que ya fue procesada/pagada.");
            return false;
        }
        if (servicios.remove(servicio)) {
            calcularTotal();
            return true;
        }
        return false;
    }

    // ============================================================
    // 🚨 POLÍTICAS DE CANCELACIÓN Y REEMBOLSOS (RF-036)
    // ============================================================

    /**
     * RF-036: Modifica el comportamiento de la cancelación aplicando restricciones de tiempo.
     * @param fechaEvento La fecha y hora programada del evento asociado.
     * @return true si se pudo cancelar (con o sin derecho a reembolso).
     */
    public boolean solicitarCancelacionConPolitica(LocalDateTime fechaEvento) {
        // 1. Si la compra está CREADA pero no pagada, se cancela de inmediato sin líos.
        if (this.estadoCompra == EstadoCompra.CREADA) {
            this.estadoCompra = EstadoCompra.CANCELADA;
            System.out.println("✅ Compra de carrito cancelada con éxito.");
            return true;
        }

        // 2. Si ya fue CONFIRMADA o PAGADA, se evalúa la ventana de 48 horas antes del show.
        if (this.estadoCompra == EstadoCompra.CONFIRMADA || this.estadoCompra == EstadoCompra.PAGADA) {
            long horasRestantes = ChronoUnit.HOURS.between(LocalDateTime.now(), fechaEvento);

            if (horasRestantes >= 48) {
                this.estadoCompra = EstadoCompra.REEMBOLSADA;
                System.out.println("✅ REEMBOLSO APROBADO: Cancelada con " + horasRestantes + "h de anticipación.");
                return true;
            } else {
                this.estadoCompra = EstadoCompra.CANCELADA; // Se cancela el cupo pero pierde el dinero
                System.out.println("❌ SIN REEMBOLSO: Cancelación fuera de tiempo (menos de 48h). Penalización aplicada.");
                return true;
            }
        }

        System.out.println("❌ La compra ya está en un estado no cancelable: " + estadoCompra);
        return false;
    }

    // ============================================================
    // ⚙️ MÉTODO ESTRATÉGICO PARA PROCESAR EL PAGO (INTACTO)
    // ============================================================

    public boolean procesarPagoConEstrategia() {
        if (metodoPago == null) {
            throw new IllegalStateException("⚠️ Error: No se ha seleccionado una estrategia de pago (Tarjeta o Transferencia).");
        }

        double montoACobrar = calcularTotal();
        boolean pagoAprobado = metodoPago.procesarPago(montoACobrar);

        if (pagoAprobado) {
            confirmarCompra(); // Pasa el estado a CONFIRMADA automáticamente
            System.out.println("🎟️ [Compra] Pago verificado de forma exitosa. Transacción guardada.");
            return true;
        } else {
            this.estadoCompra = EstadoCompra.INCIDENCIA; // Si la pasarela falla, usamos tu estado INCIDENCIA
            System.out.println("❌ [Compra] La pasarela rechazó la transacción.");
            return false;
        }
    }

    // ============================================================
    // 📋 LÓGICA DE NEGOCIO EXISTENTE (INTACTA)
    // ============================================================

    public void agregarEntrada(Entrada entrada){
        if (this.estadoCompra != EstadoCompra.CREADA) return; // Bloqueo de adición RF-035
        if (entrada != null) {
            entradas.add(entrada);
            calcularTotal();
        }
    }

    public void agregarServicio(ServicioAdicional servicio){
        if (this.estadoCompra != EstadoCompra.CREADA) return; // Bloqueo de adición RF-035
        if (servicio != null) {
            servicios.add(servicio);
            calcularTotal();
        }
    }

    public double calcularTotal(){
        double suma = 0;
        if (entradas != null) {
            for (Entrada entrada : entradas){
                if (entrada != null) suma += entrada.getPrecioFinal();
            }
        }
        if (servicios != null) {
            for (ServicioAdicional servicio : servicios){
                if (servicio != null) suma += servicio.getPrecio();
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

    /**
     * 🛡️ SEGURO DE PERSISTENCIA: Evita que colecciones viejas queden en null al deserializar.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        if (this.entradas == null) this.entradas = new ArrayList<>();
        if (this.servicios == null) this.servicios = new ArrayList<>();
        if (this.estadoCompra == null) this.estadoCompra = EstadoCompra.CREADA;
    }

    // --- GETTERS Y SETTERS ---
    public void setMetodoPago(IPagoStrategy metodoPago) { this.metodoPago = metodoPago; }
    public IPagoStrategy getMetodoPago() { return metodoPago; }
    public String getIdCompra() { return idCompra; }
    public void setIdCompra(String idCompra) { this.idCompra = idCompra; }
    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }
    public double getTotal() { return calcularTotal(); }
    public void setTotal(double total) { this.total = total; }
    public EstadoCompra getEstadoCompra() { return estadoCompra; }
    public void setEstadoCompra(EstadoCompra estadoCompra) { this.estadoCompra = estadoCompra; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Pago getPago() { return pago; }
    public void setPago(Pago pago) { this.pago = pago; }
    public List<Entrada> getEntradas() { return entradas; }
    public List<ServicioAdicional> getServicios() { return servicios; }
}