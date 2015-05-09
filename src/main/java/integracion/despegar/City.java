
package integracion.despegar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
	
	private String code;
	
	private String description;
	
	private Geolocation geolocation;
	
	public City() {
	
		// TODO Auto-generated constructor stub
	}
	
	public String getCode() {
	
		return this.code;
	}
	
	public String getDescription() {
	
		return this.description;
	}
	
	public void setCode(final String code) {
	
		this.code = code;
	}
	
	public void setDescription(final String description) {
	
		this.description = description;
	}
	
	public Geolocation getGeolocation() {
	
		return this.geolocation;
	}
	
	public void setGeolocation(Geolocation geolocation) {
	
		this.geolocation = geolocation;
	}
	
}
