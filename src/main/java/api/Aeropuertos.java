
package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	"aeropuertos"
})
public class Aeropuertos {
	
	@JsonProperty("aeropuertos")
	private List<Aeropuerto> aeropuertos = new ArrayList<Aeropuerto>();
	
	@JsonIgnore
	private Map<String, Object> additionalProperties =
	    new HashMap<String, Object>();
	
	/**
	 * 
	 * @return The aeropuertos
	 */
	@JsonProperty("aeropuertos")
	public List<Aeropuerto> getAeropuertos() {
	
		return aeropuertos;
	}
	
	/**
	 * 
	 * @param aeropuertos
	 *            The aeropuertos
	 */
	@JsonProperty("aeropuertos")
	public void setAeropuertos(List<Aeropuerto> aeropuertos) {
	
		this.aeropuertos = aeropuertos;
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
	
	public void addAeropuerto(Aeropuerto aero) {
	
		this.getAeropuertos().add(aero);
	}
	
}
