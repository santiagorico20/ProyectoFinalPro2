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

    /**
     * Calcula el costo real del servicio.
     * Centralizar esto aquí te permitirá meterle lógica (como IVA o descuentos)
     * en el futuro sin romper la clase Compra.
     */
    public double calcularCosto(){
        return this.precio;
    }

    // --- GETTERS Y SETTERS ---

    public String getIdServicio() { return idServicio; }
    public void setIdServicio(String idServicio) { this.idServicio = idServicio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Modificamos el Getter para que devuelva siempre el valor procesado por calcularCosto.
     * De esta forma, tu tabla en JavaFX y el total de la Compra se mantienen sincronizados automáticamente.
     */
    public double getPrecio() {
        return calcularCosto();
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}