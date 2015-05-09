
package integracion.despegar;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Itinerary {
	
	private List<Segment> segments;
	
	public Itinerary() {
	
		super();
		
		this.segments = new ArrayList<Segment>();
		
	}
	
	public List<Segment> getSegments() {
	
		return this.segments;
	}
	
	public void setSegments(final List<Segment> segments) {
	
		this.segments = segments;
		
	}
	
}
