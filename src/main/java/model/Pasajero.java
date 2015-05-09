
package model;

import java.util.ArrayList;
import java.util.List;

public class Pasajero {
	
	private static int contadorId = 0;
	
	private String nombre;
	
	private String apellido;
	
	private long dni;
	
	private int idUser;
	
	private List<Pasajero> amigos;
	
	private List<Recomendacion> recomendaciones;
	
	public Pasajero() {
	
		this.idUser = Pasajero.contadorId++;
		this.recomendaciones = new ArrayList<>();
	}
	
	public Pasajero(final String nombre, final String apellido, final long dni,
	    final List<Pasajero> amigos) {
	
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.amigos = amigos;
		this.idUser = Pasajero.contadorId++;
		this.recomendaciones = new ArrayList<>();
	}
	
	public void aceptarRecomendacion(final Recomendacion rec) {
	
		this.getRecomendaciones()
		    .add(rec);
	}
	
	public boolean esAmigo(final Pasajero pj) {
	
		boolean resul = false;
		for (final Pasajero p : this.getAmigos()) {
			if (p.equals(pj)) {
				resul = true;
			}
		}
		return resul;
	}
	
	public List<Pasajero> getAmigos() {
	
		return this.amigos;
	}
	
	public String getApellido() {
	
		return this.apellido;
	}
	
	public long getDni() {
	
		return this.dni;
	}
	
	public int getIdUser() {
	
		return this.idUser;
	}
	
	public String getNombre() {
	
		return this.nombre;
	}
	
	public List<Recomendacion> getRecomendaciones() {
	
		return this.recomendaciones;
	}
	
	public void setAmigos(final List<Pasajero> amigos) {
	
		this.amigos = amigos;
	}
	
	public void setApellido(final String apellido) {
	
		this.apellido = apellido;
	}
	
	public void setDni(final long dni) {
	
		this.dni = dni;
	}
	
	public void setIdUser(final int idUser) {
	
		this.idUser = idUser;
	}
	
	public void setNombre(final String nombre) {
	
		this.nombre = nombre;
	}
	
	public void setRecomendaciones(final List<Recomendacion> recomendaciones) {
	
		this.recomendaciones = recomendaciones;
	}
	
}
