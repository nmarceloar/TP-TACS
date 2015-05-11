/**
 *
 */

package apis;

import integracion.despegar.TripOption;
import java.util.List;

import org.joda.time.DateTime;

public interface TripOptionsProvider {
	
	public List<TripOption> findTripOptions(
	    String fromCity,
	    String toCity,
	    DateTime startDate,
	    DateTime endDate) throws Exception;
	
}
