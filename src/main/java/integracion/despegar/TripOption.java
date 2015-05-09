/**
 *
 */

package integracion.despegar;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripOption {
	
	private PriceDetail price_detail;
	
	private List<Itinerary> outbound_choices;
	
	private List<Itinerary> inbound_choices;
	
	public TripOption() {
	
		super();
		
		this.outbound_choices = new ArrayList<Itinerary>();
		this.inbound_choices = new ArrayList<Itinerary>();
		
	}
	
	public List<Itinerary> getInbound_choices() {
	
		return this.inbound_choices;
	}
	
	public List<Itinerary> getOutbound_choices() {
	
		return this.outbound_choices;
	}
	
	public PriceDetail getPrice_detail() {
	
		return this.price_detail;
	}
	
	public void setInbound_choices(final List<Itinerary> inbound_choices) {
	
		this.inbound_choices = inbound_choices;
	}
	
	public void setOutbound_choices(final List<Itinerary> outbound_choices) {
	
		this.outbound_choices = outbound_choices;
	}
	
	public void setPrice_detail(final PriceDetail price_detail) {
	
		this.price_detail = price_detail;
	}
	
}
