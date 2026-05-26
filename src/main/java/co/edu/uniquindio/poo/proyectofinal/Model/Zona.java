package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Zona implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private boolean esNumerada;
    private List<Asiento> asientos; // 🪑 AGREGADO: Lista de asientos para la zona

    public Zona(String idZona, String nombre, int capacidad, double precioBase, boolean esNumerada) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.esNumerada = esNumerada;
        this.asientos = new ArrayList<>();

        // Si es numerada, generamos los asientos automáticamente según su capacidad
        if (esNumerada) {
            generarAsientos();
        }
    }

    private void generarAsientos() {
        // Ejemplo: Si la capacidad es 100, hacemos 10 filas de 10 asientos
        int filas = (int) Math.sqrt(capacidad);
        int asientosPorFila = capacidad / filas;

        int contador = 0;
        for (int f = 1; f <= filas; f++) {
            for (int n = 1; n <= asientosPorFila; n++) {
                if (contador < capacidad) {
                    asientos.add(new Asiento(f, n));
                    contador++;
                }
            }
        }
    }

    // --- GETTERS Y SETTERS ---
    public String getIdZona() { return idZona; }
    public void setIdZona(String idZona) { this.idZona = idZona; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
    public boolean isEsNumerada() { return esNumerada; }
    public void setEsNumerada(boolean esNumerada) { this.esNumerada = esNumerada; }
    public List<Asiento> getAsientos() { return asientos; } // 🪑 GETTER AGREGADO
}