package api.rest.views;

import java.util.Date;

import utils.DateDeserializer;
import utils.DateSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "fromAirport",
	"toAirport",
	"airline",
	"flightid",
	"departure",
	"arrival",
	"duration" })
public class Segment {

	private Airport fromAirport;

	private Airport toAirport;

	private Airline airline;

	private String flightid;

	@JsonDeserialize(using = DateDeserializer.class)
	private Date departure;

	@JsonDeserialize(using = DateDeserializer.class)
	private Date arrival;

	private String duration;

	private Segment() {

	}

	@JsonCreator
	public Segment(@JsonProperty("fromAirport") Airport fromAirport,
		@JsonProperty("toAirport") Airport toAirport,
		@JsonProperty("airline") Airline airline,
		@JsonProperty("flightid") String flightid,
		@JsonProperty("departure") Date departure,
		@JsonProperty("arrival") Date arrival,
		@JsonProperty("duration") String duration) {

		this.fromAirport = fromAirport;
		this.toAirport = toAirport;
		this.airline = airline;
		this.flightid = flightid;
		this.departure = departure;
		this.arrival = arrival;
		this.duration = duration;

	}

	public Airline getAirline() {

		return this.airline;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getArrival() {

		return this.arrival;

	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getDeparture() {

		return this.departure;

	}

	public String getDuration() {

		return this.duration;
	}

	public String getFlightid() {

		return this.flightid;
	}

	public Airport getFromAirport() {

		return this.fromAirport;
	}

	public Airport getToAirport() {

		return this.toAirport;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Segment [fromAirport=");
		builder.append(this.fromAirport);
		builder.append(", toAirport=");
		builder.append(this.toAirport);
		builder.append(", airline=");
		builder.append(this.airline);
		builder.append(", flightid=");
		builder.append(this.flightid);
		builder.append(", departure=");
		builder.append(this.departure);
		builder.append(", arrival=");
		builder.append(this.arrival);
		builder.append("]");
		return builder.toString();
	}
}
