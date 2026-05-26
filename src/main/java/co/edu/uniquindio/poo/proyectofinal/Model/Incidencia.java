package co.edu.uniquindio.poo.proyectofinal.Model;

import java.time.LocalDate;

public class Incidencia {
    private String idIncidencia;
    private String descripcion;
    private LocalDate fecha;
    private boolean resuelta;
    // Opcional: Agregar el usuario que reporta para darle más sentido al modelo de negocio
    private Usuario usuarioReportador;

    public Incidencia(String idIncidencia, String descripcion, LocalDate fecha, Usuario usuarioReportador) {
        this.idIncidencia = idIncidencia;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuarioReportador = usuarioReportador;
        this.resuelta = false; // Por defecto toda incidencia nace abierta
    }

    /**
     * Simula el registro formal de la incidencia en el sistema
     */
    public void registrarIncidencia(){
        String nombreUser = (usuarioReportador != null) ? usuarioReportador.getNombre() : "Anónimo";
        System.out.println("Incidencia [" + idIncidencia + "] registrada exitosamente por: " + nombreUser);
    }

    /**
     * Cierra o marca como resuelta la incidencia
     */
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

    // El nombre 'isResuelta' está perfecto para el estándar JavaBean
    public boolean isResuelta() { return resuelta; }
    public void setResuelta(boolean resuelta) { this.resuelta = resuelta; }

    public Usuario getUsuarioReportador() { return usuarioReportador; }
    public void setUsuarioReportador(Usuario usuarioReportador) { this.usuarioReportador = usuarioReportador; }
}