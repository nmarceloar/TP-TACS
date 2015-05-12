
package integracion.despegar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {
	
	private String code;
	
	private String description;
	
	private Geolocation geolocation;
	
	public Airport() {
	
	}
	
	public boolean codeEqualsTo(final String iataCode) {
	
		return (iataCode != null) && this.getCode().equalsIgnoreCase(iataCode);
		
	}
	
	public String getCode() {
	
		return this.code;
	}
	
	public String getDescription() {
	
		return this.description;
	}
	
	public Geolocation getGeolocation() {
	
		return this.geolocation;
	}
	
	public void setCode(final String code) {
	
		this.code = code;
	}
	
	public void setDescription(final String description) {
	
		this.description = description;
	}
	
	public void setGeolocation(final Geolocation geolocation) {
	
		this.geolocation = geolocation;
		
	}
	
}
