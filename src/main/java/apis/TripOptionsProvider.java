/**
 *
 */

package apis;


import integracion.despegar.TripOptions;

import org.joda.time.DateTime;

public interface TripOptionsProvider {
	
	public TripOptions findTripOptions(String fromCity, String toCity,
	    DateTime startDate, DateTime endDate, int offset, int limit)
	    throws Exception;
	
}
