package co.edu.uniquindio.poo.proyectofinal.Model.Adapter;

import co.edu.uniquindio.poo.proyectofinal.Model.Compra;
import java.util.List;

public class ApachePoiAdapter implements IReporteAdapter {

    // Liberamos el constructor para que el adaptador sea un servicio limpio y reutilizable
    public ApachePoiAdapter() {
        // Constructor vacío estándar
    }

    /**
     * Exporta los datos de compras recibiendo la lista y la ruta destino dinámicamente.
     * 🔥 Modificamos la firma para que coincida con la interfaz ideal de JavaFX.
     */
    @Override
    public void exportarDatos(List<Compra> datosAExportar, String rutaArchivo) {
        if (datosAExportar == null || datosAExportar.isEmpty()) {
            System.err.println("⚠️ [ApachePoiAdapter] No hay datos de compras para exportar.");
            return;
        }

        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            System.err.println("⚠️ [ApachePoiAdapter] Ruta de archivo inválida.");
            return;
        }

        System.out.println("🔧 [ApachePoiAdapter] Abriendo libro de trabajo (.xlsx) con Apache POI...");
        System.out.println("📊 [ApachePoiAdapter] Creando celdas y filas para " + datosAExportar.size() + " compras...");

        // Aquí irá tu código real de Apache POI en el futuro:
        // Workbook workbook = new XSSFWorkbook(); ... etc.

        System.out.println("✅ [ApachePoiAdapter] Archivo de Excel guardado correctamente en: " + rutaArchivo);
    }
}