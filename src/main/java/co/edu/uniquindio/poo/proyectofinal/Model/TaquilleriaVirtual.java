package co.edu.uniquindio.poo.proyectofinal.Model;

import java.util.ArrayList;
import java.util.List;

public class TaquilleriaVirtual {

    private static volatile TaquilleriaVirtual instance;

    // Listas globales del sistema
    private List<Usuario> usuarios;
    private List<Evento> eventos; // 🔥 Corregido: Ahora almacena el Producto real (Evento)
    private List<Compra> compras;
    private List<Incidencia> incidencias;

    // Constructor privado del Singleton
    private TaquilleriaVirtual() {
        usuarios = new ArrayList<>();
        eventos = new ArrayList<>(); // 🔥 Inicializado correctamente
        compras = new ArrayList<>();
        incidencias = new ArrayList<>();
    }

    /**
     * Retorna la instancia única del sistema aplicando Double-Checked Locking (Seguro para hilos).
     */
    public static TaquilleriaVirtual getInstance() {
        if (instance == null) {
            synchronized (TaquilleriaVirtual.class) {
                if (instance == null) {
                    instance = new TaquilleriaVirtual();
                }
            }
        }
        return instance;
    }

    // --- MÉTODOS DE ADICIÓN SEGUROS ---

    public void agregarUsuario(Usuario usuario) {
        if (usuario != null) usuarios.add(usuario);
    }

    public void agregarEvento(Evento evento) { // 🔥 Corregido el parámetro a Evento
        if (evento != null) eventos.add(evento);
    }

    public void agregarCompra(Compra compra) {
        if (compra != null) compras.add(compra);
    }

    public void agregarIncidencia(Incidencia incidencia) {
        if (incidencia != null) incidencias.add(incidencia);
    }

    // --- GETTERS Y SETTERS PROTEGIDOS (Fundamentales para la estabilidad de JavaFX) ---

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * 🔥 Devuelve la lista de eventos reales para mapear directamente en las TableView.
     */
    public List<Evento> getEventos() {
        return eventos;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    // Métodos de asignación segura en bloque por si cargas datos desde archivos
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = (usuarios != null) ? usuarios : new ArrayList<>();
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = (eventos != null) ? eventos : new ArrayList<>();
    }

    public void setCompras(List<Compra> compras) {
        this.compras = (compras != null) ? compras : new ArrayList<>();
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = (incidencias != null) ? incidencias : new ArrayList<>();
    }
}