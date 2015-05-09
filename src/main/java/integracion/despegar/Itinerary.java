
package integracion.despegar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	public Set<String> getAirportCodesAsSet() {
	
		Set<String> set = new HashSet<>();
		
		for (Segment s : this.segments) {
			
			set.add(s.getFrom());
			set.add(s.getTo());
			
		}
		
		return set;
		
	}
	
}
