
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
	
	public Pasajero(String nombre, String apellido, long dni,
	    List<Pasajero> amigos) {
	
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.amigos = amigos;
                this.idUser = contadorId++;
                this.recomendaciones = new ArrayList<>();
	}
	
	public Pasajero() {
                this.idUser = contadorId++;
                this.recomendaciones = new ArrayList<>();
	}

        public int getIdUser() {
                return idUser;
        }

        public void setIdUser(int idUser) {
                this.idUser = idUser;
        }

        public List<Recomendacion> getRecomendaciones() {
                return recomendaciones;
        }   

        public void setRecomendaciones(List<Recomendacion> recomendaciones) {
                this.recomendaciones = recomendaciones;
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
        
        public void aceptarRecomendacion(Recomendacion rec){
            this.getRecomendaciones().add(rec);
        }
	
}
