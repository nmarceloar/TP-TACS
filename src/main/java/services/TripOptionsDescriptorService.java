package services;

import org.joda.time.DateTime;

import api.rest.views.TripOptionsDescriptor;

public interface TripOptionsDescriptorService {

	public TripOptionsDescriptor findTripOptions(String fromCity,
		String toCity, DateTime startDate, DateTime endDate, int offset,
		int limit);

}
