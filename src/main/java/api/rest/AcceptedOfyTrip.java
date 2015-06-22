package api.rest;

import java.util.Date;

import services.DateSerializer;
import services.OfyRecommendation;
import services.OfyTrip;
import services.OfyUser;
import services.TripDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * No tiene ningun valor, es una clase para mostrar una "vista" y diferenciar un
 * viaje creado de uno aceptado 
 * Usen los campos que sirvan en el front. Los demas los vamos sacando. 
 * NO es una entidad
 *
 */

@JsonPropertyOrder({ "trip_details", "recommended_by", "received_on",
		"accepted_on" })
public class AcceptedOfyTrip {

	private OfyTrip trip;

	private OfyRecommendation recommendation;

	public AcceptedOfyTrip(OfyTrip trip, OfyRecommendation recommendation) {

		this.trip = trip;
		this.recommendation = recommendation;
	}

	@JsonProperty("recommended_by")
	public OfyUser getOwner() {

		return this.trip.getOwner();
	}

	@JsonProperty("trip_details")
	public TripDetails getTripDetails() {

		return this.trip.getTripDetails();
	}

	@JsonProperty("received_on")
	@JsonSerialize(using = DateSerializer.class)
	public Date getCreationDate() {

		return this.recommendation.getCreationDate();
	}

	@JsonProperty("accepted_on")
	@JsonSerialize(using = DateSerializer.class)
	public Date getPatchDate() {

		return this.recommendation.getPatchDate();
	}

}
