package co.edu.uniquindio.poo.proyectofinal.Model.Adapter;

import co.edu.uniquindio.poo.proyectofinal.Model.Compra;
import java.util.List;

public interface IReporteAdapter {

    /**
     * Contrato genérico para la exportación de reportes de compras.
     * * @param datosAExportar Lista de compras obtenida del sistema (TaquilleriaVirtual).
     * @param rutaArchivo Ruta absoluta en el disco seleccionada por el usuario para guardar el documento.
     */
    void exportarDatos(List<Compra> datosAExportar, String rutaArchivo);
}