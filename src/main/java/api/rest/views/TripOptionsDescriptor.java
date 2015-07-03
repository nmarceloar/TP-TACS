/**
 *
 */

package api.rest.views;

import integracion.despegar.Paging;
import integracion.despegar.TripOption;
import integracion.despegar.TripOptions;

import java.util.List;

import api.rest.Airline;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonAutoDetect(fieldVisibility = Visibility.NONE)
@JsonPropertyOrder({ "items", "paging", "airlines", "airports" })
public class TripOptionsDescriptor {

	private final TripOptions tripOptions;

	private final List<Airport> airports;

	private final List<Airline> airlines;

	public TripOptionsDescriptor(final TripOptions tripOptions,
		final List<Airport> airports, final List<Airline> airlines) {

		this.tripOptions = tripOptions;
		this.airports = airports;
		this.airlines = airlines;

	}

	public List<Airline> getAirlines() {

		return this.airlines;
	}

	public List<Airport> getAirports() {

		return this.airports;
	}

	public List<TripOption> getItems() {

		return this.tripOptions.getItems();
	}

	public Paging getPaging() {

		return this.tripOptions.getPaging();
	}

}
