package co.edu.uniquindio.poo.proyectofinal.Controller;

import java.io.*;

public class Persistencia {

    private static final String RUTA_ARCHIVO = "nexus_tickets_db.dat";

    // 📥 Guarda toda la estructura de la aplicación en el archivo
    public static void guardarSistema(TaquillaVirtualFacade facade) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(facade);
            System.out.println("💾 [PERSISTENCIA] Datos guardados exitosamente en " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("❌ [ERROR] No se pudieron guardar los datos: " + e.getMessage());
            e.printStackTrace(); // 🔥 IMPORTANTE: Esto te dirá exactamente qué clase interna está rompiendo el guardado
        }
    }

    // 📤 Carga el sistema desde el archivo al iniciar
    public static TaquillaVirtualFacade cargarSistema() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("⚠️ [PERSISTENCIA] No se encontró base de datos previa. Se iniciará un sistema en blanco.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO))) {
            TaquillaVirtualFacade facadeCargada = (TaquillaVirtualFacade) ois.readObject();

            // 🔥 TRUCO MAESTRO: Reinyectamos la instancia cargada al Singleton de la Fachada
            if (facadeCargada != null) {
                // Para poder hacer esto, necesitamos añadir un setter o asignarlo mediante un método espejo si fuera necesario,
                // pero la forma directa es asegurar que la app use esta referencia.
                System.out.println("📂 [PERSISTENCIA] Datos cargados correctamente.");
                return facadeCargada;
            }
        } catch (Exception e) {
            System.err.println("❌ [ERROR] Fallo crítico al leer el archivo de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}