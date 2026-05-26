package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.Serializable;

/**
 * ENTIDAD: Representa un asiento físico en una zona numerada.
 */
public class Asiento implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo; // Ej: "A-12"
    private int fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(int fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.codigo = "F" + fila + "-N" + numero;
        this.estado = new EstadoAsientoDisponible(); // Todos nacen libres
    }

    // --- ACCIONES DEL STATE ---
    public void reservar() { estado.reservar(this); }
    public void ocupar() { estado.ocupar(this); }
    public void liberar() { estado.liberar(this); }

    // --- GETTERS Y SETTERS ---
    public String getCodigo() { return codigo; }
    public int getFila() { return fila; }
    public int getNumero() { return numero; }
    public EstadoAsiento getEstado() { return estado; }
    public void setEstado(EstadoAsiento estado) { this.estado = estado; }

    public String getNombreEstado() { return estado.getNombreEstado(); }
}