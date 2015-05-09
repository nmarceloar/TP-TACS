
package integracion.despegar;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripOptions {
	
	private List<TripOption> items;
	
	public TripOptions() {
	
		super();
		
		this.items = new ArrayList<TripOption>();
		
	}
	
	public List<TripOption> getItems() {
	
		return this.items;
	}
	
	public void setItems(final List<TripOption> items) {
	
		this.items = items;
		
	}
	
}
