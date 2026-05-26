package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.Compra;
import co.edu.uniquindio.poo.proyectofinal.Model.EstadoCompra;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReporteController {

    @FXML private Label lblIngresosTotales;
    @FXML private Label lblBoletasVendidas;

    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    /**
     * Genera y muestra las estadísticas de negocio consolidadas en el sistema.
     */
    @FXML
    public void onGenerarReporte() {
        double ingresosAcumulados = 0;
        int contadorBoletas = 0;

        // Recorremos todas las compras confirmadas a través de la fachada
        for (Compra c : facade.getListaCompras()) {
            if (c.getEstadoCompra() == EstadoCompra.CONFIRMADA) {
                ingresosAcumulados += c.getTotal();
                contadorBoletas += c.getEntradas().size();
            }
        }

        // Sincronizamos los datos con las etiquetas de la interfaz gráfica de usuario
        lblIngresosTotales.setText("$" + ingresosAcumulados);
        lblBoletasVendidas.setText(String.valueOf(contadorBoletas));
    }
}