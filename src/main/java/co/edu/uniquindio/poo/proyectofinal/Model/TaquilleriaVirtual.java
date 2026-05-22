package co.edu.uniquindio.poo.proyectofinal.Model;

import java.util.ArrayList;
import java.util.List;

public class TaquilleriaVirtual {


        private static TaquilleriaVirtual instance;

        private List<Usuario> usuarios;

        private List<EventoBuilder> eventoBuilders;

        private List<Compra> compras;

        private List<Incidencia> incidencias;

        private TaquilleriaVirtual() {

            usuarios = new ArrayList<>();
            eventoBuilders = new ArrayList<>();
            compras = new ArrayList<>();
            incidencias = new ArrayList<>();
        }

        /**
         * Metodo que permite obtener la unica instancia de la clase notificacion
         * @return instancia
         */
        public static TaquilleriaVirtual getInstance() {
            if (instance == null) {
                instance = new TaquilleriaVirtual();
            }
            return instance;
        }
/// METODOS DEL PATRON FACADE
        public void agregarUsuario(Usuario usuario) {

            usuarios.add(usuario);
        }

        public void agregarEvento(EventoBuilder eventoBuilder) {

            eventoBuilders.add(eventoBuilder);
        }

        public void agregarCompra(Compra compra) {

            compras.add(compra);
        }

        public void agregarIncidencia(Incidencia incidencia) {

            incidencias.add(incidencia);
        }

        /// GETS Y SETS

    public static void setInstance(TaquilleriaVirtual instance) {
        TaquilleriaVirtual.instance = instance;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<EventoBuilder> getEventos() {
        return eventoBuilders;
    }

    public void setEventos(List<EventoBuilder> eventoBuilders) {
        this.eventoBuilders = eventoBuilders;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }


}
