/**
 *
 */

package integracion.despegar;

import java.util.List;

import org.joda.time.DateTime;

public interface TripOptionsProvider {
	
	public TripOptions findTripOptions(String fromCity, String toCity,
	    DateTime startDate, DateTime endDate, int offset) throws Exception;
	
}
