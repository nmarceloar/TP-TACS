/**
 *
 */

package services;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {

	static {

		OfyService.factory()
			.register(OfyUser.class);

		OfyService.factory()
			.register(OfyTrip.class);

		OfyService.factory()
			.register(OfyRecommendation.class);

	}

	public static ObjectifyFactory factory() {

		return ObjectifyService.factory();

	}

	public static Objectify ofy() {

		return ObjectifyService.ofy();

	}

}
