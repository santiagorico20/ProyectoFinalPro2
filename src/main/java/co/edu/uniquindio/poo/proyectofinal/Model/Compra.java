package co.edu.uniquindio.poo.proyectofinal.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private String idCompra;

    private LocalDate fechaCompra;

    private double total;

    private EstadoCompra estadoCompra;

    private Usuario usuario;

    private Pago pago;

    private List<Entrada> entradas;

    private List<ServicioAdicional> servicios;

    public Compra(String idCompra, LocalDate fechaCompra, Usuario usuario) {

        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.usuario = usuario;

        this.estadoCompra = EstadoCompra.CREADA;

        entradas = new ArrayList<>();
        servicios = new ArrayList<>();
    }

    public void agregarEntrada(Entrada entrada){

        entradas.add(entrada);
    }

    public void agregarServicio(ServicioAdicional servicio){

        servicios.add(servicio);
    }

    public double calcularTotal(){

        total = 0;

        for (Entrada entrada : entradas){

            total += entrada.getPrecioFinal();
        }

        for (ServicioAdicional servicio : servicios){

            total += servicio.getPrecio();
        }

        return total;
    }

    public void confirmarCompra(){

        estadoCompra = EstadoCompra.CONFIRMADA;
    }

    public void cancelarCompra(){

        estadoCompra = EstadoCompra.CANCELADA;
    }


    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public List<ServicioAdicional> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioAdicional> servicios) {
        this.servicios = servicios;
    }
}
