package co.edu.uniquindio.poo.proyectofinal.Model;

import java.time.LocalDate;

public class Incidencia {
    private String idIncidencia;

    private String descripcion;

    private LocalDate fecha;

    private boolean resuelta;

    public Incidencia(String idIncidencia, String descripcion, LocalDate fecha) {
        this.idIncidencia = idIncidencia;
        this.descripcion = descripcion;
        this.fecha = fecha;

        this.resuelta = false;
    }

    public void registrarIncidencia(){

        System.out.println("Incidencia registrada");
    }

    public void cerrarIncidencia(){

        resuelta = true;
    }


    /// GETS Y SETS

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }
}
