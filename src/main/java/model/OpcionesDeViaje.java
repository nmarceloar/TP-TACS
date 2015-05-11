
package model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpcionesDeViaje {
	
	@JsonProperty("items")
	private List<OpcionDeViaje> opcionDeViajes = new ArrayList<OpcionDeViaje>();
	
	/**
	 * 
	 * @return The opcionDeViajes
	 */
	@JsonProperty("items")
	public List<OpcionDeViaje> getItems() {
	
		return opcionDeViajes;
	}
	
	/**
	 * 
	 * @param opcionDeViajes
	 *            The opcionDeViajes
	 */
	@JsonProperty("items")
	public void setItems(List<OpcionDeViaje> opcionDeViajes) {
	
		this.opcionDeViajes = opcionDeViajes;
	}
	
	@Override
	public String toString() {
	
		return ToStringBuilder.reflectionToString(this);
	}
	
}
