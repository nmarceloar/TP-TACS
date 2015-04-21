
package model;

public class Aeropuerto {
	
	private String codigo;
	
	private String pais;
	
	private String ciudad;
	
	public Aeropuerto() {
	
	}
	
	public Aeropuerto(String codigo, String pais, String ciudad) {
	
		super();
		
		this.codigo = codigo;
		this.pais = pais;
		this.ciudad = ciudad;
		
	}
	
	public String getCodigo() {
	
		return codigo;
	}
	
	public void setCodigo(String codigo) {
	
		this.codigo = codigo;
	}
	
	public String getPais() {
	
		return pais;
	}
	
	public void setPais(String pais) {
	
		this.pais = pais;
	}
	
	public String getCiudad() {
	
		return ciudad;
	}
	
	public void setCiudad(String ciudad) {
	
		this.ciudad = ciudad;
	}
	
}
