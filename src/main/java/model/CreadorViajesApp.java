package model;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase con los metodos que funcionan como conectores entre los pedidos al
 * server y la logica de la aplicacion.
 * 
*/
public class CreadorViajesApp {
    
    // Lista que simula la persistencia 
    public static List<Viaje> viajesRegistrados;

    // Setter y getter de la lista a persistir
    public static List<Viaje> getViajesRegistrados() {
        return viajesRegistrados;
    }

    public static void setViajesRegistrados(List<Viaje> viajesRegistrados) {
        CreadorViajesApp.viajesRegistrados = viajesRegistrados;
    }

    // Metodo para grabar en la lista de persistencia un viaje instanciado
    public static void registrarViaje(Viaje viaje){
        getViajesRegistrados().add(viaje);
    }
        
    // Metodo que retorna la lista de viajes de los amigos de un pasajero
    public List<Viaje> getViajesDeAmigosByPasajero(Pasajero passenger){
        List<Viaje> viajesAmigo = new ArrayList<>();
        for (Viaje v : viajesAmigo){
            if (v.getViajante().esAmigo(passenger)){
                viajesAmigo.add(v);
            }
        }
        return viajesAmigo;
    }
    
    // Metodo que instancia y retorna un viaje
    public static Viaje crearViaje(Pasajero pj, List<Trayecto> trayectos){
        Viaje viaje = new Viaje(pj, trayectos);
        return viaje;
    }
}
