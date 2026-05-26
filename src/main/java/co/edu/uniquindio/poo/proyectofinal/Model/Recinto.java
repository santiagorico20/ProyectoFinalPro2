package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ENTIDAD: El lugar físico que aloja el evento y contiene múltiples zonas.
 */
public class Recinto implements Serializable {
    private String idRecinto;
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Zona> zonas; // Lista de zonas asociadas (Composición)

    public Recinto(String idRecinto, String nombre, String direccion, String ciudad) {
        this.idRecinto = idRecinto;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.zonas = new ArrayList<>();
    }

    public void agregarZona(Zona zona) {
        this.zonas.add(zona);
    }

    public void eliminarZona(Zona zona) {
        this.zonas.remove(zona);
    }

    // --- GETTERS Y SETTERS ---
    public String getIdRecinto() { return idRecinto; }
    public void setIdRecinto(String idRecinto) { this.idRecinto = idRecinto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public List<Zona> getZonas() { return zonas; }
}