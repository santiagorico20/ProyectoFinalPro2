package co.edu.uniquindio.poo.proyectofinal.Model;

import co.edu.uniquindio.poo.proyectofinal.Model.Observer.IObserver;
import co.edu.uniquindio.poo.proyectofinal.Model.State.EventoPublicado;
import co.edu.uniquindio.poo.proyectofinal.Model.State.IEstadoEventoState;
import javafx.util.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoBuilder {

    private String idEvento;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String ciudad;
    private LocalDate fecha;
    private Recinto recinto;
    private IEstadoEventoState estadoEventoActual;
    private List<Usuario> ListUsuarios;
    private List<IObserver> listObservadores;

    public EventoBuilder(Builder builder) {

        this.idEvento = builder.idEvento;
        assert idEvento != null;
        this.nombre = builder.nombre;
        this.categoria = builder.categoria;
        this.descripcion = builder.descripcion;
        this.ciudad = builder.ciudad;
        this.fecha = builder.fecha;
        this.recinto=recinto;

        this.estadoEventoActual = new EventoPublicado();

        ListUsuarios = new ArrayList<>();
    }

    public void cambiarEstado(IEstadoEventoState nuevoEstado) {
        this.estadoEventoActual = nuevoEstado;
        System.out.println("El evento '" + this.nombre + "' ha cambiado de estado.");
    }

    public void agregarUsuarios(Usuario usuario){

        ListUsuarios.add(usuario);
    }

    public void eliminarUsuarios(Usuario usuario){

        ListUsuarios.remove(usuario);
    }

    public void agregarObservador(IObserver obs) { listObservadores.add(obs); }

    public void notificarObservadores(String mensaje) {
        for (IObserver obs : listObservadores) {
            obs.actualizarEstado(mensaje);
        }
    }

    public void cancelar() {
        estadoEventoActual.cancelarEvento(this);
    }

    public boolean permitirVenta(){

        return estadoEventoActual.permitirVenta();
    }




    //GETS Y SETS

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    public IEstadoEventoState getEstadoEventoActual() {
        return estadoEventoActual;
    }

    public void setEstadoEventoActual(IEstadoEventoState estadoEventoActual) {
        this.estadoEventoActual = estadoEventoActual;
    }

    public List<Usuario> getListUsuarios() {
        return ListUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        ListUsuarios = listUsuarios;
    }

    public List<IObserver> getListObservadores() {
        return listObservadores;
    }

    public void setListObservadores(List<IObserver> listObservadores) {
        this.listObservadores = listObservadores;
    }


    public static class Builder{
        private String idEvento;
        private String nombre;
        private String categoria;
        private String descripcion;
        private String ciudad;
        private LocalDate fecha;

        public Builder IdEvento(String idEvento) {
            this.idEvento = idEvento;
            return this;
        }

        public Builder nombre(String nombre){
            this.nombre = nombre;
            return this;
        }

        public Builder categoria(String categoria){
            this.categoria = categoria;
            return this;
        }

        public Builder descripcion(String descripcion){
            this.descripcion = descripcion;
            return this;
        }

        public Builder ciudad(String ciudad){
            this.ciudad = ciudad;
            return this;
        }

        public Builder fecha(LocalDate fecha){
            this.fecha = fecha;
            return this;
        }


        public EventoBuilder build(){
            return new EventoBuilder(this);
        }
    }

}
