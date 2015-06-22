package integracion.despegar;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Segment {

	private String from;

	private String to;

	@JsonSerialize(using = JodaDateTimeSerializer.class)
	private DateTime departure_datetime;

	@JsonSerialize(using = JodaDateTimeSerializer.class)
	private DateTime arrival_datetime;

	private String duration;

	private String airline;

	private String flight_id;

	public String getAirline() {

		return this.airline;
	}

	public DateTime getArrival_datetime() {

		return this.arrival_datetime;
	}

	public DateTime getDeparture_datetime() {

		return this.departure_datetime;
	}

	public String getFlight_id() {

		return this.flight_id;
	}

	public String getFrom() {

		return this.from;
	}

	public String getTo() {

		return this.to;
	}

	public void setAirline(final String airline) {

		this.airline = airline;
	}

	public void setArrival_datetime(final DateTime arrival_datetime) {

		this.arrival_datetime = arrival_datetime;
	}

	public void setDeparture_datetime(final DateTime departure_datetime) {

		this.departure_datetime = departure_datetime;

	}

	public void setFlight_id(final String flight_id) {

		this.flight_id = flight_id;
	}

	public void setFrom(final String from) {

		this.from = from;
	}

	public void setTo(final String to) {

		this.to = to;
	}

	public String getDuration() {

		return this.duration;
	}

	public void setDuration(String duration) {

		this.duration = duration;
	}

}
