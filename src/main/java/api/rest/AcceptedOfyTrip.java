package api.rest;

import java.util.Date;

import model2.Recommendation;
import model2.Trip;
import model2.User;
import model2.impl.OfyRecommendation;
import utils.DateSerializer;
import api.rest.views.TripDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * No tiene ningun valor, es una clase para mostrar una "vista" y diferenciar un
 * viaje creado de uno aceptado Usen los campos que sirvan en el front. Los
 * demas los vamos sacando. NO es una entidad
 *
 */

@JsonPropertyOrder({ "trip_id",
	"trip_details",
	"recommended_by",
	"received_on",
	"accepted_on" })
public class AcceptedOfyTrip implements AcceptedTrip {

	private Trip trip;
	private Recommendation recommendation;

	public AcceptedOfyTrip(OfyRecommendation recommendation) {

		this.trip = recommendation.getTrip();
		this.recommendation = recommendation;
	}

	@Override
	@JsonProperty("recommended_by")
	public User getOwner() {

		return this.trip.getOwner();
	}

	@Override
	@JsonProperty("trip_details")
	public TripDetails getTripDetails() {

		return this.trip.getTripDetails();

	}

	@Override
	@JsonProperty("received_on")
	@JsonSerialize(using = DateSerializer.class)
	public Date getCreationDate() {

		return this.recommendation.getCreationDate();
	}

	@Override
	@JsonProperty("accepted_on")
	@JsonSerialize(using = DateSerializer.class)
	public Date getPatchDate() {

		return this.recommendation.getPatchDate();
	}

	@Override
	@JsonProperty("trip_id")
	public String getTripId() {

		return this.trip.getId();

	}

}
