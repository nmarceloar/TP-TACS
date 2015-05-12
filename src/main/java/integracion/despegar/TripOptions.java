
package integracion.despegar;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripOptions {
	
	private List<TripOption> items;
        private Paging paging;
	
	private Paging paging;
	
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
        
        public Paging getPaging() {
	
		return this.paging;
	}
	
	public void setPaging(Paging paging) {
	
		this.paging = paging;
	}
	
	public Paging getPaging() {
	
		return this.paging;
	}
	
	public void setPaging(Paging paging) {
	
		this.paging = paging;
	}
	
}
