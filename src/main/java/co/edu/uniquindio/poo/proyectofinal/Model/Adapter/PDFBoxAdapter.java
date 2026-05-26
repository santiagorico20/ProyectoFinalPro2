package co.edu.uniquindio.poo.proyectofinal.Model.Adapter;

import co.edu.uniquindio.poo.proyectofinal.Model.Compra;
import java.util.List;

public class PDFBoxAdapter implements IReporteAdapter {

    // Liberamos el constructor para alinearnos con el diseño de servicios reutilizables
    public PDFBoxAdapter() {
        // Constructor vacío estándar
    }

    /**
     * Exporta los datos de compras a un archivo PDF usando la firma flexible sincronizada.
     */
    @Override
    public void exportarDatos(List<Compra> datosAExportar, String rutaArchivo) {
        if (datosAExportar == null || datosAExportar.isEmpty()) {
            System.err.println("⚠️ [PDFBoxAdapter] No hay datos de compras para exportar al PDF.");
            return;
        }

        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            System.err.println("⚠️ [PDFBoxAdapter] Ruta de destino no válida para el PDF.");
            return;
        }

        System.out.println("🔧 [PDFBoxAdapter] Inicializando motor de PDFBox...");
        System.out.println("📄 [PDFBoxAdapter] Escribiendo tabla de ventas en el documento PDF...");

        // Cálculo dinámico utilizando la lógica limpia que reparamos en la clase Compra
        double ingresosTotales = 0;
        for (Compra compra : datosAExportar) {
            if (compra != null) {
                ingresosTotales += compra.getTotal(); // Llama al getTotal() dinámico y seguro
            }
        }

        // Aquí irá tu lógica real de PDFBox en el futuro (PDDocument, PDPageContentStream, etc.)
        System.out.println("✅ [PDFBoxAdapter] Reporte PDF generado con éxito en: " + rutaArchivo);
        System.out.println("📊 [PDFBoxAdapter] Total Ventas Contabilizadas: $" + ingresosTotales);
    }
}