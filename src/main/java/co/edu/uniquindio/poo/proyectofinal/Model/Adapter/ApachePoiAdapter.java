package co.edu.uniquindio.poo.proyectofinal.Model.Adapter;

public class ApachePoiAdapter implements IReporteAdapter {

    @Override
    public void exportarDatos() {

        System.out.println("Reporte Excel exportado");
    }
}
