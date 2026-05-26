package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ENTIDAD: Actividad programada. Usa el Patrón State para manejar su ciclo de vida.
 */
public class Evento implements Serializable {
    private String idEvento;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private Recinto recinto;

    // Aquí vive la magia del Patrón State
    private EstadoEvento estadoActual;

    public Evento(String idEvento, String nombre, String descripcion, LocalDateTime fecha, Recinto recinto) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.recinto = recinto;

        // Todo evento nace por defecto en estado Borrador
        this.estadoActual = new EstadoBorrador();
    }

    // --- MÉTODOS DEL PATRÓN STATE (Delegan la acción al estado actual) ---
    public void publicar() {
        estadoActual.publicar(this);
    }

    public void pausar() {
        estadoActual.pausar(this);
    }

    public void cancelar() {
        estadoActual.cancelar(this);
    }

    // --- GETTERS Y SETTERS ---
    public EstadoEvento getEstadoActual() { return estadoActual; }
    public void setEstadoActual(EstadoEvento estadoActual) { this.estadoActual = estadoActual; }

    // Getter rápido para que JavaFX pinte el texto del estado en la tabla
    public String getNombreEstado() { return estadoActual.getNombreEstado(); }

    public String getIdEvento() { return idEvento; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFecha() { return fecha; }
    public Recinto getRecinto() { return recinto; }
}