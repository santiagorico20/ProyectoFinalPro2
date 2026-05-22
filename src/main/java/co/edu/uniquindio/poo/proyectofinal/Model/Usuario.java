package co.edu.uniquindio.poo.proyectofinal.Model;
import co.edu.uniquindio.poo.proyectofinal.Model.Observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements IObserver {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;

    private List<Compra> Listcompras;
    private List<MetodoPago> ListmetodosPago;

    public Usuario(String idUsuario, String nombre, String correo, String telefono) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;

        Listcompras = new ArrayList<>();
        ListmetodosPago = new ArrayList<>();
    }

    public void modificarPerfil(String nombre, String correo, String telefono){

        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public void agregarMetodoPago(MetodoPago metodoPago){

        ListmetodosPago.add(metodoPago);
    }

    public void eliminarMetodoPago(MetodoPago metodoPago){
        ListmetodosPago.remove(metodoPago);
    }
    public List<Compra> consultarCompras(){
        return Listcompras;
    }


    @Override
    public void actualizarEstado(String mensaje) {
        // SRP: Su única responsabilidad de notificación es mostrar la alerta al usuario
        System.out.println("Notificación enviada a " + correo + ": " + mensaje);
    }


    //GETS Y SETS

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Compra> getListcompras() {
        return Listcompras;
    }

    public void setListcompras(List<Compra> listcompras) {
        Listcompras = listcompras;
    }

    public List<MetodoPago> getListmetodosPago() {
        return ListmetodosPago;
    }

    public void setListmetodosPago(List<MetodoPago> listmetodosPago) {
        ListmetodosPago = listmetodosPago;
    }
}
