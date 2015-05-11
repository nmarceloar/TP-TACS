
package model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Segment {
	
	@JsonProperty("from")
	private String from;
	
	@JsonProperty("to")
	private String to;
	
	@JsonProperty("departure_datetime")
	private String departureDatetime;
	
	@JsonProperty("arrival_datetime")
	private String arrivalDatetime;
	
	@JsonProperty("duration")
	private String duration;
	
	@JsonProperty("airline")
	private String airline;
	
	@JsonProperty("flight_id")
	private String flightId;
	
	/**
	 * 
	 * @return The from
	 */
	@JsonProperty("from")
	public String getFrom() {
	
		return from;
	}
	
	/**
	 * 
	 * @param from
	 *            The from
	 */
	@JsonProperty("from")
	public void setFrom(String from) {
	
		this.from = from;
	}
	
	/**
	 * 
	 * @return The to
	 */
	@JsonProperty("to")
	public String getTo() {
	
		return to;
	}
	
	/**
	 * 
	 * @param to
	 *            The to
	 */
	@JsonProperty("to")
	public void setTo(String to) {
	
		this.to = to;
	}
	
	/**
	 * 
	 * @return The departureDatetime
	 */
	@JsonProperty("departure_datetime")
	public String getDepartureDatetime() {
	
		return departureDatetime;
	}
	
	/**
	 * 
	 * @param departureDatetime
	 *            The departure_datetime
	 */
	@JsonProperty("departure_datetime")
	public void setDepartureDatetime(String departureDatetime) {
	
		this.departureDatetime = departureDatetime;
	}
	
	/**
	 * 
	 * @return The arrivalDatetime
	 */
	@JsonProperty("arrival_datetime")
	public String getArrivalDatetime() {
	
		return arrivalDatetime;
	}
	
	/**
	 * 
	 * @param arrivalDatetime
	 *            The arrival_datetime
	 */
	@JsonProperty("arrival_datetime")
	public void setArrivalDatetime(String arrivalDatetime) {
	
		this.arrivalDatetime = arrivalDatetime;
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
	 * @return The airline
	 */
	@JsonProperty("airline")
	public String getAirline() {
	
		return airline;
	}
	
	/**
	 * 
	 * @param airline
	 *            The airline
	 */
	@JsonProperty("airline")
	public void setAirline(String airline) {
	
		this.airline = airline;
	}
	
	/**
	 * 
	 * @return The flightId
	 */
	@JsonProperty("flight_id")
	public String getFlightId() {
	
		return flightId;
	}
	
	/**
	 * 
	 * @param flightId
	 *            The flight_id
	 */
	@JsonProperty("flight_id")
	public void setFlightId(String flightId) {
	
		this.flightId = flightId;
	}
	
	@Override
	public String toString() {
	
		return ToStringBuilder
		                .reflectionToString(this);
	}
	
}
