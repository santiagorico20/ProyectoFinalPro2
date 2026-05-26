package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.Serializable;
import java.time.LocalDate;

// 1. Le agregamos implements Serializable
public class Incidencia implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idIncidencia;
    private String descripcion;
    private LocalDate fecha;
    private boolean resuelta;
    private Usuario usuarioReportador;

    public Incidencia(String idIncidencia, String descripcion, LocalDate fecha, Usuario usuarioReportador) {
        this.idIncidencia = idIncidencia;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuarioReportador = usuarioReportador;
        this.resuelta = false;
    }

    public void registrarIncidencia(){
        String nombreUser = (usuarioReportador != null) ? usuarioReportador.getNombre() : "Anónimo";
        System.out.println("Incidencia [" + idIncidencia + "] registrada exitosamente por: " + nombreUser);
    }

    public void cerrarIncidencia(){
        this.resuelta = true;
        System.out.println("Incidencia [" + idIncidencia + "] ha sido marcada como RESUELTA.");
    }

    // --- GETTERS Y SETTERS ---
    public String getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(String idIncidencia) { this.idIncidencia = idIncidencia; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public boolean isResuelta() { return resuelta; }
    public void setResuelta(boolean resuelta) { this.resuelta = resuelta; }

    public Usuario getUsuarioReportador() { return usuarioReportador; }
    public void setUsuarioReportador(Usuario usuarioReportador) { this.usuarioReportador = usuarioReportador; }

    // 2. Le agregamos el toString para que el administrador la vea bien en pantalla
    @Override
    public String toString() {
        String estadoVisual = resuelta ? "✅ RESUELTA" : "⏳ PENDIENTE";
        String nombre = (usuarioReportador != null) ? usuarioReportador.getNombre() : "Usuario Desconocido";
        return "[" + fecha + "] " + idIncidencia + " - " + estadoVisual +
                " | Cliente: " + nombre + "\n" + descripcion;
    }
}