
package model;

public class Aeropuerto {
	
	private String codigo;
	
	private String pais;
	
	private String ciudad;
	
	public Aeropuerto() {
	
	}
	
	public Aeropuerto(final String codigo, final String pais,
	    final String ciudad) {
	
		super();
		
		this.codigo = codigo;
		this.pais = pais;
		this.ciudad = ciudad;
		
	}
	
	public String getCiudad() {
	
		return this.ciudad;
	}
	
	public String getCodigo() {
	
		return this.codigo;
	}
	
	public String getPais() {
	
		return this.pais;
	}
	
	public void setCiudad(final String ciudad) {
	
		this.ciudad = ciudad;
	}
	
	public void setCodigo(final String codigo) {
	
		this.codigo = codigo;
	}
	
	public void setPais(final String pais) {
	
		this.pais = pais;
	}
	
}
