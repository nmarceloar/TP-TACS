package integracion.despegar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Itinerary {

	private long choice;

	private String duration;

	private List<Segment> segments;

	public Itinerary() {

		this.segments = new ArrayList<Segment>();

	}

	public List<Segment> getSegments() {

		return this.segments;
	}

	public void setSegments(final List<Segment> segments) {

		this.segments = segments;

	}

	@JsonIgnore(value = true)
	public Set<String> getAirportCodesAsSet() {

		Set<String> set = new HashSet<>();

		for (Segment s : this.segments) {

			set.add(s.getFrom());
			set.add(s.getTo());

		}

		return set;

	}

	@JsonIgnore(value = true)
	public Set<String> getAirlinesCodesAsSet() {

		Set<String> set = new HashSet<>();

		for (Segment s : this.segments) {

			set.add(s.getAirline());

		}

		return set;
	}

	public long getChoice() {

		return this.choice;
	}

	public void setChoice(long choice) {

		this.choice = choice;
	}

	public String getDuration() {

		return this.duration;
	}

	public void setDuration(String duration) {

		this.duration = duration;
	}

}
