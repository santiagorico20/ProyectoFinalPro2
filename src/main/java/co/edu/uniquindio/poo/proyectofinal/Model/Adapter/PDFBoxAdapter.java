package co.edu.uniquindio.poo.proyectofinal.Model.Adapter;

public class PDFBoxAdapter implements IReporteAdapter {

    @Override
    public void exportarDatos() {

        System.out.println("Reporte PDF exportado");
    }
}
