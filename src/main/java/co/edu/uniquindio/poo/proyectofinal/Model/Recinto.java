package co.edu.uniquindio.poo.proyectofinal.Model;

import java.util.ArrayList;
import java.util.List;

public class Recinto {
    private String idRecinto;
    private String nombre;
    private String direccion;
    private int capacidadMaxima;
    private List<Zona> ListZonas;

    public Recinto(String idRecinto, String nombre, String direccion, int capacidadMaxima) {
        this.idRecinto = idRecinto;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadMaxima = capacidadMaxima;

        ListZonas = new ArrayList<>();
    }

    public void agregarZona(Zona zona){

        ListZonas.add(zona);
    }

    public int consultarCapacidad(){

        return capacidadMaxima;
    }

/// GETS Y SETS
    public String getIdRecinto() {
        return idRecinto;
    }

    public void setIdRecinto(String idRecinto) {
        this.idRecinto = idRecinto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public List<Zona> getListZonas() {
        return ListZonas;
    }

    public void setListZonas(List<Zona> listZonas) {
        ListZonas = listZonas;
    }
}
