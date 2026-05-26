package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.Administrador;
import co.edu.uniquindio.poo.proyectofinal.Model.Evento;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario;
import javafx.scene.control.Alert;

public class EventoController {

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    /**
     * LÓGICA DE NEGOCIO: Valida las condiciones del administrador y crea el evento.
     */
    public boolean registrarNuevoEvento(String id, String nombre, String fecha, String descripcion, String lugar) {
        Usuario usuarioActivo = facade.getUsuarioAutenticado();

        // Validamos si hay una sesión de administrador activa
        if (!(usuarioActivo instanceof Administrador admin)) {
            mostrarAlerta("Acceso Denegado", "Solo un usuario Administrador puede registrar eventos.", Alert.AlertType.ERROR);
            return false;
        }

        if (id.isEmpty() || nombre.isEmpty() || fecha.isEmpty()) {
            mostrarAlerta("Campos Obligatorios", "El ID, Nombre y Fecha son requeridos.", Alert.AlertType.WARNING);
            return false;
        }

        try {
            // ❌ ERROR SOLUCIONADO: Ya no usamos admin.crearEvento(...)
            // En su lugar, instanciamos el evento directamente (usando tu constructor o Builder real de la clase Evento).
            // NOTA: Si tu Evento requiere LocalDateTime y Recinto en lugar de String, deberás parsearlos aquí,
            // o usar el AdminViewController que ya hace este proceso de forma nativa.

            Evento nuevoEvento = new Evento(id, nombre, descripcion, null, null); // Ajusta los parámetros según tu constructor de Evento

            // Lo guardamos en la lista global de la fachada para que la tabla del view lo renderice automáticamente
            facade.getEventos().add(nuevoEvento);

            mostrarAlerta("Éxito", "Evento '" + nombre + "' creado correctamente (Borrador).", Alert.AlertType.INFORMATION);
            return true;

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo estructurar el evento: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    /**
     * Aplica la transición del patrón State para publicar
     */
    public void publicarEvento(Evento evento) {
        if (facade.getUsuarioAutenticado() instanceof Administrador admin && evento != null) {
            // ❌ ERROR SOLUCIONADO: Ya no usamos admin.publicarEvento(evento);
            // Delega la responsabilidad de publicar directamente al objeto Evento usando el patrón State.
            evento.publicar();
        }
    }

    /**
     * Aplica la transición del patrón State para cancelar
     */
    public void cancelarEvento(Evento evento) {
        if (facade.getUsuarioAutenticado() instanceof Administrador admin && evento != null) {
            // ❌ ERROR SOLUCIONADO: Ya no usamos admin.cancelarEvento(evento);
            // Delega la responsabilidad de cancelar directamente al objeto Evento usando el patrón State.
            evento.cancelar();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}