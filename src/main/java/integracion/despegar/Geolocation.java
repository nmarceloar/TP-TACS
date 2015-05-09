
package integracion.despegar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geolocation {
	
	private double latitude;
	
	private double longitude;
	
	public Geolocation() {
	
	}
	
	public double getLatitude() {
	
		return this.latitude;
	}
	
	public double getLongitude() {
	
		return this.longitude;
	}
	
	public void setLatitude(final double latitude) {
	
		this.latitude = latitude;
	}
	
	public void setLongitude(final double longitude) {
	
		this.longitude = longitude;
	}
	
}
