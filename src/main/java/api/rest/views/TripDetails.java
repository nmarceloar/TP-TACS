package api.rest.views;

import java.util.Date;
import java.util.List;

import utils.DateSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class TripDetails {

	public City fromCity;
	public City toCity;
	public PriceDetail priceDetail;
	public List<Segment> outboundItinerary;
	public List<Segment> inboundItinerary;

	private TripDetails() {
		super();
	}

	@JsonCreator
	public TripDetails(
		@JsonProperty("fromCity") City fromCity,
		@JsonProperty("toCity") City toCity,
		@JsonProperty("priceDetail") PriceDetail priceDetail,
		@JsonProperty("outboundItinerary") List<Segment> outboundItinerary,
		@JsonProperty("inboundItinerary") List<Segment> inboundItinerary) {

		this.fromCity = fromCity;
		this.toCity = toCity;
		this.priceDetail = priceDetail;
		this.outboundItinerary = outboundItinerary;
		this.inboundItinerary = inboundItinerary;

	}

	public City getFromCity() {

		return this.fromCity;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getInboundDate() {

		return this.inboundItinerary.get(0).getDeparture();

	}

	public List<Segment> getInboundItinerary() {

		return this.inboundItinerary;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getOutboundDate() {

		return this.outboundItinerary.get(0).getDeparture();

	}

	public List<Segment> getOutboundItinerary() {

		return this.outboundItinerary;
	}

	public PriceDetail getPriceDetail() {

		return this.priceDetail;
	}

	public City getToCity() {

		return this.toCity;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("TripDetails [getFromCity()=");
		builder.append(this.getFromCity());
		builder.append(", getToCity()=");
		builder.append(this.getToCity());
		builder.append(", getPriceDetail()=");
		builder.append(this.getPriceDetail());
		builder.append(", getOutboundItinerary()=");
		builder.append(this.getOutboundItinerary());
		builder.append(", getInboundItinerary()=");
		builder.append(this.getInboundItinerary());
		builder.append("]");
		return builder.toString();
	}

}
