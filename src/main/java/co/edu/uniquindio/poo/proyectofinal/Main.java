package co.edu.uniquindio.poo.proyectofinal;

import co.edu.uniquindio.poo.proyectofinal.Controller.Persistencia;
import co.edu.uniquindio.poo.proyectofinal.Controller.TaquillaVirtualFacade;
import co.edu.uniquindio.poo.proyectofinal.Model.Usuario; // IMPORTANTE: Importamos el Modelo Usuario
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // 1. Conectamos con TU navegador usando el método exacto de tu clase: setStagePrincipal
        Navegador.setStagePrincipal(stage);

        // 2. 🔥 CORRECCIÓN CRÍTICA: Intentamos cargar el sistema guardado en el disco duro
        TaquillaVirtualFacade sistemaCargado = Persistencia.cargarSistema();

        if (sistemaCargado != null) {
            // Si el archivo existía y contenía datos, inyectamos esos datos en el Singleton
            TaquillaVirtualFacade.setInstancia(sistemaCargado);
            System.out.println("👥 [MAIN] Base de datos recuperada. Usuarios activos: "
                    + TaquillaVirtualFacade.getInstancia().getUsuarios().size());
        } else {
            // Si el archivo NO existía o era antiguo/incompatible, forzamos la creación de una base de datos limpia
            System.out.println("⚠️ [MAIN] No se detectaron datos previos. Creando entorno limpio con Admin por defecto.");

            // Quemamos un Administrador inicial para que nunca te quedes por fuera del sistema
            TaquillaVirtualFacade.getInstancia().getUsuarios().add(
                    new Usuario("admin", "Administrador Principal", "admin@mail.com", "admin123", "ADMIN")
            );

            // Guardamos inmediatamente para crear físicamente el archivo nexus_tickets_db.dat limpio
            Persistencia.guardarSistema(TaquillaVirtualFacade.getInstancia());
        }

        // 3. Cargamos la pantalla inicial usando TU formato de ruta absoluta
        Navegador.cambiarPantalla("/co/edu/uniquindio/poo/proyectofinal/LoginView.fxml", "Nexus Tickets - Iniciar Sesión");

        // 4. Propiedades de la ventana
        stage.setResizable(false);
    }

    // 🎯 EL GATILLO MÁGICO DE LA PERSISTENCIA
    // Este método de JavaFX se ejecuta automáticamente cuando el usuario cierra el programa (clic en la 'X')
    @Override
    public void stop() throws Exception {
        System.out.println("🛑 [SISTEMA] Cerrando aplicación... Iniciando proceso de salvaguarda de datos.");

        // Atrapamos la instancia de la fachada con toda la información en memoria
        TaquillaVirtualFacade fachadaActual = TaquillaVirtualFacade.getInstancia();

        // La enviamos a la clase Persistencia para que reescriba el archivo .dat
        Persistencia.guardarSistema(fachadaActual);

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}