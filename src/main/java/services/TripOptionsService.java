/**
 *
 */

package services;

import integracion.despegar.TripOptions;

import org.joda.time.DateTime;

public interface TripOptionsService {

	public TripOptions findTripOptions(String fromCity, String toCity,
		DateTime startDate, DateTime endDate, int offset, int limit);

}
