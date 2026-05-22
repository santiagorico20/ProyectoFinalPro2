package co.edu.uniquindio.poo.proyectofinal.Model;

import co.edu.uniquindio.poo.proyectofinal.Model.State.AsientoDisponible;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String idZona;
    private String nombre;
    private double precioBase;
    private List<Asiento> ListAsientos;
    private Tarifa tarifa;

    public Zona(String idZona, String nombre, double precioBase) {

        this.idZona = idZona;
        this.nombre = nombre;
        this.precioBase = precioBase;

        ListAsientos = new ArrayList<>();
    }

    public int consultarDisponibilidad(){

        int disponibles = 0;

        for (Asiento asiento : ListAsientos){

            if(asiento.getEstadoActual() instanceof AsientoDisponible){

                disponibles++;
            }
        }

        return disponibles;
    }

    public void agregarAsiento(Asiento asiento){

        ListAsientos.add(asiento);
    }

    public double obtenerPrecioZona(){

        return tarifa.calcularPrecioFinal();
    }
/// GETS Y SETS

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public List<Asiento> getListAsientos() {
        return ListAsientos;
    }

    public void setListAsientos(List<Asiento> listAsientos) {
        ListAsientos = listAsientos;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }
}
