package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Entrada implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idTicket;
    private Evento evento;
    private Zona zona;
    private Asiento asiento;
    private double precioPagado;
    private LocalDateTime fechaCompra;

    public Entrada(Evento evento, Zona zona, Asiento asiento, double precioPagado) {
        // Genera un código único de ticket (Ej: TKT-A1B2C)
        this.idTicket = "TKT-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.evento = evento;
        this.zona = zona;
        this.asiento = asiento;
        this.precioPagado = precioPagado;
        this.fechaCompra = LocalDateTime.now();
    }

    // --- GETTERS ---
    public String getIdTicket() { return idTicket; }
    public Evento getEvento() { return evento; }
    public Zona getZona() { return zona; }
    public Asiento getAsiento() { return asiento; }
    public double getPrecioPagado() { return precioPagado; }

    // 🎯 NUEVO ALIAS: Resuelve el error de compilación en la clase Compra
    public double getPrecioFinal() {
        return precioPagado;
    }

    public LocalDateTime getFechaCompra() { return fechaCompra; }
}