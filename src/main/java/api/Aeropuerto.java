
package api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "icao",
    "iata",
    "name",
    "city",
    "country",
    "elevation",
    "lat",
    "lon",
    "tz"
})
public class Aeropuerto {
	
	@JsonProperty("icao")
	private String icao;
	
	@JsonProperty("iata")
	private String iata;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("elevation")
	private Integer elevation;
	
	@JsonProperty("lat")
	private Double lat;
	
	@JsonProperty("lon")
	private Double lon;
	
	@JsonProperty("tz")
	private String tz;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties =
	    new HashMap<String, Object>();
	
	/**
	 * 
	 * @return The icao
	 */
	@JsonProperty("icao")
	public String getIcao() {
	
		return icao;
	}
	
	/**
	 * 
	 * @param icao
	 *            The icao
	 */
	@JsonProperty("icao")
	public void setIcao(String icao) {
	
		this.icao = icao;
	}
	
	/**
	 * 
	 * @return The iata
	 */
	@JsonProperty("iata")
	public String getIata() {
	
		return iata;
	}
	
	/**
	 * 
	 * @param iata
	 *            The iata
	 */
	@JsonProperty("iata")
	public void setIata(String iata) {
	
		this.iata = iata;
	}
	
	/**
	 * 
	 * @return The name
	 */
	@JsonProperty("name")
	public String getName() {
	
		return name;
	}
	
	/**
	 * 
	 * @param name
	 *            The name
	 */
	@JsonProperty("name")
	public void setName(String name) {
	
		this.name = name;
	}
	
	/**
	 * 
	 * @return The city
	 */
	@JsonProperty("city")
	public String getCity() {
	
		return city;
	}
	
	/**
	 * 
	 * @param city
	 *            The city
	 */
	@JsonProperty("city")
	public void setCity(String city) {
	
		this.city = city;
	}
	
	/**
	 * 
	 * @return The country
	 */
	@JsonProperty("country")
	public String getCountry() {
	
		return country;
	}
	
	/**
	 * 
	 * @param country
	 *            The country
	 */
	@JsonProperty("country")
	public void setCountry(String country) {
	
		this.country = country;
	}
	
	/**
	 * 
	 * @return The elevation
	 */
	@JsonProperty("elevation")
	public Integer getElevation() {
	
		return elevation;
	}
	
	/**
	 * 
	 * @param elevation
	 *            The elevation
	 */
	@JsonProperty("elevation")
	public void setElevation(Integer elevation) {
	
		this.elevation = elevation;
	}
	
	/**
	 * 
	 * @return The lat
	 */
	@JsonProperty("lat")
	public Double getLat() {
	
		return lat;
	}
	
	/**
	 * 
	 * @param lat
	 *            The lat
	 */
	@JsonProperty("lat")
	public void setLat(Double lat) {
	
		this.lat = lat;
	}
	
	/**
	 * 
	 * @return The lon
	 */
	@JsonProperty("lon")
	public Double getLon() {
	
		return lon;
	}
	
	/**
	 * 
	 * @param lon
	 *            The lon
	 */
	@JsonProperty("lon")
	public void setLon(Double lon) {
	
		this.lon = lon;
	}
	
	/**
	 * 
	 * @return The tz
	 */
	@JsonProperty("tz")
	public String getTz() {
	
		return tz;
	}
	
	/**
	 * 
	 * @param tz
	 *            The tz
	 */
	@JsonProperty("tz")
	public void setTz(String tz) {
	
		this.tz = tz;
	}
	
	@Override
	public String toString() {
	
		return ToStringBuilder
		                .reflectionToString(this);
	}
	
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	
		return this.additionalProperties;
	}
	
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	
		this.additionalProperties
		                .put(name, value);
	}
	
	public boolean cityNameLike(String cityName) {
	
		return this.getCity().toLowerCase().contains(cityName.toLowerCase());
		
	}
	
}
