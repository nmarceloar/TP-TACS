
package model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Itinerario {
	
	@JsonProperty("choice")
	private Integer choice;
	
	@JsonProperty("duration")
	private String duration;
	
	@JsonProperty("segments")
	private List<Segment> segments = new ArrayList<Segment>();
	
	/**
	 * 
	 * @return The choice
	 */
	@JsonProperty("choice")
	public Integer getChoice() {
	
		return choice;
	}
	
	/**
	 * 
	 * @param choice
	 *            The choice
	 */
	@JsonProperty("choice")
	public void setChoice(Integer choice) {
	
		this.choice = choice;
	}
	
	/**
	 * 
	 * @return The duration
	 */
	@JsonProperty("duration")
	public String getDuration() {
	
		return duration;
	}
	
	/**
	 * 
	 * @param duration
	 *            The duration
	 */
	@JsonProperty("duration")
	public void setDuration(String duration) {
	
		this.duration = duration;
	}
	
	/**
	 * 
	 * @return The segments
	 */
	@JsonProperty("segments")
	public List<Segment> getSegments() {
	
		return segments;
	}
	
	/**
	 * 
	 * @param segments
	 *            The segments
	 */
	@JsonProperty("segments")
	public void setSegments(List<Segment> segments) {
	
		this.segments = segments;
	}
	
	@Override
	public String toString() {
	
		return ToStringBuilder.reflectionToString(this);
	}
	
}
