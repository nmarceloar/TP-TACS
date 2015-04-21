
package model;

import java.util.List;

public class Pasajero {
	
	private String nombre;
	
	private String apellido;
	
	private long dni;
	
	private List<Pasajero> amigos;
	
	public Pasajero(String nombre, String apellido, long dni,
	    List<Pasajero> amigos) {
	
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.amigos = amigos;
	}
	
	public Pasajero() {
	
	}
	
	public String getNombre() {
	
		return nombre;
	}
	
	public void setNombre(String nombre) {
	
		this.nombre = nombre;
	}
	
	public String getApellido() {
	
		return apellido;
	}
	
	public void setApellido(String apellido) {
	
		this.apellido = apellido;
	}
	
	public long getDni() {
	
		return dni;
	}
	
	public void setDni(long dni) {
	
		this.dni = dni;
	}
	
	public List<Pasajero> getAmigos() {
	
		return amigos;
	}
	
	public void setAmigos(List<Pasajero> amigos) {
	
		this.amigos = amigos;
	}
	
	public boolean esAmigo(Pasajero pj) {
	
		boolean resul = false;
		for (Pasajero p : getAmigos()) {
			if (p.equals(pj)) {
				resul = true;
			}
		}
		return resul;
	}
	
}
