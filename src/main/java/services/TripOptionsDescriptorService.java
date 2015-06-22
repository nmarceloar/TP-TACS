package services;

import org.joda.time.DateTime;

public interface TripOptionsDescriptorService {

	public TripOptionsDescriptor findTripOptions(String fromCity,
			String toCity, DateTime startDate, DateTime endDate, int offset,
			int limit);

}
