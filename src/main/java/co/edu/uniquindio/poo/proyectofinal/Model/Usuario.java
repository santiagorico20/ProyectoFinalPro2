package co.edu.uniquindio.poo.proyectofinal.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {

    // 🛡️ REGLA DE ORO DE PERSISTENCIA: Fija la identidad de la clase para que Java no rompa la lectura
    private static final long serialVersionUID = 1L;

    private String idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol; // "ADMIN" o "CLIENTE"
    private String telefono; // El nuevo campo

    private List<Compra> historialCompras;
    private List<String> notificaciones; // El nuevo campo de alertas

    // Constructor Completo (6 parámetros)
    public Usuario(String idUsuario, String nombre, String correo, String contrasena, String rol, String telefono) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.telefono = telefono;
        this.historialCompras = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

    // Constructor Sobrecargado (5 parámetros para compatibilidad con código antiguo)
    public Usuario(String idUsuario, String nombre, String correo, String contrasena, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.telefono = "No registrado"; // Valor por defecto seguro
        this.historialCompras = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

    /**
     * 🔥 CONTROLADOR DE CRISIS DE PERSISTENCIA:
     * Este método se ejecuta automáticamente cuando Java lee un Usuario desde el archivo binario.
     * Si el archivo binario es viejo y no tiene 'telefono' o 'notificaciones', este método evita que queden en NULL.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        // Lee los atributos estándar que ya existían
        ois.defaultReadObject();

        // 🛠️ Validaciones de seguridad post-lectura:
        if (this.telefono == null) {
            this.telefono = "No registrado";
        }
        if (this.notificaciones == null) {
            this.notificaciones = new ArrayList<>();
        }
        if (this.historialCompras == null) {
            this.historialCompras = new ArrayList<>();
        }
    }

    // --- LÓGICA DE NOTIFICACIONES ---
    public List<String> getNotificaciones() {
        if (notificaciones == null) notificaciones = new ArrayList<>();
        return notificaciones;
    }
    public void agregarNotificacion(String mensaje) { this.getNotificaciones().add(mensaje); }
    public void limpiarNotificaciones() { this.getNotificaciones().clear(); }

    // --- GETTERS Y SETTERS BÁSICOS ---
    public String getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public String getRol() { return rol; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Compra> getHistorialCompras() {
        if (historialCompras == null) historialCompras = new ArrayList<>();
        return historialCompras;
    }
}