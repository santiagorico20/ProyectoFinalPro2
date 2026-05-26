package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.*;
import java.io.Serializable; // 🛡️ IMPORTANTE: Importamos Serializable
import java.util.ArrayList;
import java.util.List;

/**
 * Fachada principal del sistema.
 * Se implementa Serializable ya que la clase Persistencia guarda este objeto por completo.
 */
public class TaquillaVirtualFacade implements Serializable {

    // 🛡️ REGLA DE ORO DE PERSISTENCIA: Fija la identidad de la fachada.
    // Esto garantiza que el archivo binario no se corrompa al añadir o quitar métodos.
    private static final long serialVersionUID = 1L;

    private static TaquillaVirtualFacade instancia;

    private List<Usuario> usuarios;
    private List<Recinto> recintos;
    private List<Evento> eventos;
    private Usuario usuarioAutenticado;

    private TaquillaVirtualFacade() {
        usuarios = new ArrayList<>();
        recintos = new ArrayList<>();
        eventos = new ArrayList<>();
    }

    public static TaquillaVirtualFacade getInstancia() {
        if (instancia == null) instancia = new TaquillaVirtualFacade();
        return instancia;
    }

    /**
     * 🔥 TRUCO DE REINYECCIÓN DE PERSISTENCIA:
     * Permite que la clase Persistencia reemplace la instancia activa del Singleton
     * al momento de leer los datos guardados en el disco duro.
     */
    public static void setInstancia(TaquillaVirtualFacade nuevaInstancia) {
        instancia = nuevaInstancia;
    }

    /**
     * 💾 MÉTODO AUXILIAR: Guarda el estado completo de esta fachada de forma rápida.
     */
    public void resguardarEstado() {
        Persistencia.guardarSistema(this);
    }

    // 🚨 Método para que el Admin quite asientos y notifique
    public void cancelarEntradaPorAdmin(Asiento asiento, String motivo) {
        for (Usuario u : usuarios) {
            if (u.getHistorialCompras() != null) {
                for (Compra c : u.getHistorialCompras()) {
                    boolean eliminada = c.getEntradas().removeIf(entrada ->
                            entrada.getAsiento().getCodigo().equals(asiento.getCodigo())
                    );

                    if (eliminada) {
                        u.agregarNotificacion("⚠️ Tu entrada para el asiento " + asiento.getCodigo() +
                                " ha sido cancelada. Motivo: " + motivo +
                                ". Nos contactaremos para tu reembolso.");

                        // Guardamos automáticamente al alterar la información
                        resguardarEstado();
                        return;
                    }
                }
            }
        }
    }

    // 📊 Método para recolectar las compras
    public List<Compra> getListaCompras() {
        List<Compra> todasLasCompras = new ArrayList<>();
        if (this.usuarios != null) {
            for (Usuario u : this.usuarios) {
                if (u.getHistorialCompras() != null) {
                    todasLasCompras.addAll(u.getHistorialCompras());
                }
            }
        }
        return todasLasCompras;
    }

    // 👥 NUEVO ALIAS: Esto soluciona el error "cannot find symbol: method getListaUsuarios()"
    public List<Usuario> getListaUsuarios() {
        return this.usuarios;
    }

    // --- GETTERS Y SETTERS ---
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Recinto> getRecintos() { return recintos; }
    public List<Evento> getEventos() { return eventos; }
    public Usuario getUsuarioAutenticado() { return usuarioAutenticado; }
    public void setUsuarioAutenticado(Usuario usuario) { this.usuarioAutenticado = usuario; }
}