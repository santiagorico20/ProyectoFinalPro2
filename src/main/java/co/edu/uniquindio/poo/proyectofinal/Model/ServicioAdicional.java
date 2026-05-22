package co.edu.uniquindio.poo.proyectofinal.Model;

public class ServicioAdicional {
    private String idServicio;

    private String nombre;

    private String descripcion;

    private double precio;

    public ServicioAdicional(String idServicio, String nombre, String descripcion, double precio) {

        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public double calcularCosto(){

        return precio;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
