package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.Compra;
import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.TarjetaStrategy;
import co.edu.uniquindio.poo.proyectofinal.Model.Strategy.TransferenciaStrategy;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PagoController {

    @FXML private ComboBox<String> comboMetodoPago;
    @FXML private Label lblMontoAPagar;

    // Contenedores visuales para habilitar/deshabilitar según la estrategia
    @FXML private TextField txtDatoPrincipal; // Número de tarjeta o Banco
    @FXML private TextField txtDatoSecundario; // Titular o Número de Cuenta

    private Compra compraAProcesar;
    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    public void initialize() {
        comboMetodoPago.getItems().addAll("Tarjeta Bancaria", "Transferencia Directa (PSE)");
    }

    /**
     * Inyecta la información de la compra desde la ventana del carrito.
     */
    public void prepararTransaccion(Compra compra) {
        this.compraAProcesar = compra;
        this.lblMontoAPagar.setText("$" + compra.getTotal());
    }

    /**
     * 🔥 Aplica el patrón STRATEGY inyectando polimórficamente la pasarela seleccionada.
     */
    @FXML
    public void onEjecutarPago() {
        if (compraAProcesar == null) return;

        String seleccion = comboMetodoPago.getValue();
        if (seleccion == null) {
            mostrarAlerta("Validación", "Seleccione un método de pago.", Alert.AlertType.WARNING);
            return;
        }

        // 💉 INYECCIÓN DE LA ESTRATEGIA EN TIEMPO DE EJECUCIÓN
        if ("Tarjeta Bancaria".equals(seleccion)) {
            compraAProcesar.setMetodoPago(new TarjetaStrategy(txtDatoPrincipal.getText(), txtDatoSecundario.getText()));
        } else {
            compraAProcesar.setMetodoPago(new TransferenciaStrategy(txtDatoPrincipal.getText(), txtDatoSecundario.getText()));
        }

        // 🚀 Ejecución polimórfica del pago
        boolean exito = compraAProcesar.procesarPagoConEstrategia();

        if (exito) {
            // Guardamos la compra en el histórico global de la Fachada
            facade.getListaCompras().add(compraAProcesar);
            mostrarAlerta("Éxito", "Transacción confirmada. ¡Disfruta tu evento!", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Transacción rechazada por la pasarela de pagos.", Alert.AlertType.ERROR);
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