
package model;

public class Vuelo {
	
	private String descripcion;
	
	public Vuelo(String descripcion) {
	
		this.setDescripcion(descripcion);
		
	}
	
	
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
	
		return this.descripcion;
	}
	
	
	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
	
		if (descripcion != null) {
			
			this.descripcion = descripcion;
		}
		
		else {
			throw new NullPointerException("La descripcion debe existir");
		}
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	
		return "Vuelo [descripcion=" + this.descripcion + "]";
	}
	
	
}
