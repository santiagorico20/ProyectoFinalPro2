package co.edu.uniquindio.poo.proyectofinal.Model.Observer;

public interface IObservable {
    void registrarObservador(IObserver observador);
    void removerObservador(IObserver observador);
    void notificarObservadores(String mensaje);
}