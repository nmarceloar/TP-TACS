package config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class JerseyApp extends ResourceConfig {

	public JerseyApp() {

		super();

		this.packages("api");

		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);

	}
}
