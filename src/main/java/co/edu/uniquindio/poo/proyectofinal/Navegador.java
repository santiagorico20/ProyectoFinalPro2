package co.edu.uniquindio.poo.proyectofinal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * UTILERÍA DE ARQUITECTURA: Gestiona el enrutamiento de pantallas.
 * Utiliza rutas absolutas modulares basadas en la jerarquía de paquetes.
 */
public class Navegador {

    private static Stage stagePrincipal;

    public static void setStagePrincipal(Stage stage) {
        stagePrincipal = stage;
    }

    /**
     * Cambia la escena del Stage principal.
     * @param rutaFxml Ruta absoluta (Ej: "/co/edu/uniquindio/poo/proyectofinal/LoginView.fxml")
     * @param titulo Título de la ventana
     */
    public static void cambiarPantalla(String rutaFxml, String titulo) {
        if (stagePrincipal == null) {
            System.err.println("❌ Error: Stage principal no inicializado.");
            return;
        }

        try {
            System.out.println("🎬 Navegando hacia recurso: " + rutaFxml);

            // Carga el FXML usando el cargador de recursos del sistema modular
            FXMLLoader loader = new FXMLLoader(Navegador.class.getResource(rutaFxml));
            Parent raiz = loader.load();

            Scene nuevaEscena = new Scene(raiz);
            stagePrincipal.setScene(nuevaEscena);
            stagePrincipal.setTitle(titulo);
            stagePrincipal.centerOnScreen();
            stagePrincipal.show();

        } catch (IOException e) {
            System.err.println("🚨 Falla crítica al cargar la interfaz: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("❌ Error: No se encontró el archivo FXML en la ruta especificada. Verifica tus carpetas en resources.");
            e.printStackTrace();
        }
    }
}