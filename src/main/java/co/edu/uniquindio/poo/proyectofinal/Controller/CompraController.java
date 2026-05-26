package co.edu.uniquindio.poo.proyectofinal.Controller;

import co.edu.uniquindio.poo.proyectofinal.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.util.UUID;

public class CompraController {

    @FXML private Label lblIdCompra;
    @FXML private Label lblTotalCalculado;

    private Compra compraEnProgreso;
    private final TaquillaVirtualFacade facade = TaquillaVirtualFacade.getInstancia();

    @FXML
    public void initialize() {
        // Inicializamos una nueva orden para el usuario logueado en la sesión
        Usuario actual = facade.getUsuarioAutenticado();
        String idUnico = "COM-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        compraEnProgreso = new Compra(idUnico, LocalDate.now(), actual);

        lblIdCompra.setText(compraEnProgreso.getIdCompra());
        actualizarPantallaTotal();
    }

    public void agregarEntradaAlCarrito(Entrada entrada) {
        if (entrada != null) {
            compraEnProgreso.agregarEntrada(entrada);
            actualizarPantallaTotal();
        }
    }

    public void agregarServicioAlCarrito(ServicioAdicional servicio) {
        if (servicio != null) {
            compraEnProgreso.agregarServicio(servicio);
            actualizarPantallaTotal();
        }
    }

    private void actualizarPantallaTotal() {
        lblTotalCalculado.setText("$" + compraEnProgreso.getTotal());
    }

    public Compra getCompraEnProgreso() {
        return compraEnProgreso;
    }
}