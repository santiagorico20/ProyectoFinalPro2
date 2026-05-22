package co.edu.uniquindio.poo.proyectofinal.Model;

import co.edu.uniquindio.poo.proyectofinal.Model.State.AsientoDisponible;
import co.edu.uniquindio.poo.proyectofinal.Model.State.AsientoReservado;
import co.edu.uniquindio.poo.proyectofinal.Model.State.AsientoVendido;
import co.edu.uniquindio.poo.proyectofinal.Model.State.IEstadoAsientoState;

public class Asiento {
    private String idAsiento;
    private String fila;
    private int numero;

    private IEstadoAsientoState estadoActual;
    private Zona zona;

    public Asiento(String idAsiento, String fila, int numero,Zona zona) {

        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.zona = zona;

        estadoActual = new AsientoDisponible();
    }

    public void reservar(){

        estadoActual.reservar();

        estadoActual = new AsientoReservado();
    }

    public void vender(){

        estadoActual.vender();

        estadoActual = new AsientoVendido();
    }

    public void liberar(){

        estadoActual.liberar();

        estadoActual = new AsientoDisponible();
    }

    public void cambiarEstado(IEstadoAsientoState nuevoEstado){

        estadoActual = nuevoEstado;
    }
/// GETS Y SETS
    public String getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(String idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public IEstadoAsientoState getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(IEstadoAsientoState estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }



}
