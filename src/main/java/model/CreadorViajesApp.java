
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
	
	// Metodo que instancia y retorna un viaje
	public static Viaje crearViaje(
	    final Pasajero pj,
	    final List<Trayecto> trayectos) {
	
		final Viaje viaje = new Viaje(pj, trayectos);
		return viaje;
	}
	
	// Metodo para retornar la lista de viajes de un usuario por su id
	public static List<Viaje> getViajesDeUsuarioById(final int idUser) {
	
		final List<Viaje> viajes = new ArrayList<>();
		for (final Viaje v : CreadorViajesApp.getViajesRegistrados()) {
			if (v.getViajante()
			    .getIdUser() == idUser) {
				viajes.add(v);
			}
		}
		return viajes;
	}
	
	// Setter y getter de la lista a persistir
	public static List<Viaje> getViajesRegistrados() {
	
		return CreadorViajesApp.viajesRegistrados;
	}
	
	// Metodo para registrar un usuario. Retorna su id
	public static int registrarUsuario(
	    final String nombre,
	    final String apellido) {
	
		final Pasajero pj = new Pasajero();
		pj.setNombre(nombre);
		pj.setApellido(apellido);
		return pj.getIdUser();
	}
	
	// Metodo para grabar en la lista de persistencia un viaje instanciado
	public static void registrarViaje(final Viaje viaje) {
	
		CreadorViajesApp.getViajesRegistrados()
		    .add(viaje);
	}
	
	public static
	    void
	    setViajesRegistrados(final List<Viaje> viajesRegistrados) {
	
		CreadorViajesApp.viajesRegistrados = viajesRegistrados;
	}
	
	// Metodo que retorna la lista de viajes de los amigos de un pasajero
	public List<Viaje> getViajesDeAmigosByPasajero(final Pasajero passenger) {
	
		final List<Viaje> viajesAmigo = new ArrayList<>();
		for (final Viaje v : viajesAmigo) {
			if (v.getViajante()
			    .esAmigo(passenger)) {
				viajesAmigo.add(v);
			}
		}
		return viajesAmigo;
	}
}
