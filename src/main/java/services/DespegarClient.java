package services;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.glassfish.jersey.client.ClientProperties;

import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class DespegarClient {

	private static final Client INSTANCE = ClientBuilder.newClient()
		.property(ClientProperties.CONNECT_TIMEOUT, 0)
		.property(ClientProperties.READ_TIMEOUT, 0)
		.register(new JacksonJaxbJsonProvider(new JodaMapper(), null))
		.register(new ClientRequestFilter() {

			@Override
			public void filter(final ClientRequestContext requestContext)
				throws IOException {

				requestContext.getHeaders().add("X-ApiKey",
					"19638437094c4892a8af7cdbed49ee43");
			}
		});

	public static Client getInstance() {

		return INSTANCE;

	}

	private DespegarClient() {

		Logger.getLogger(this.getClass().getCanonicalName())
			.info("DespegarClient Ok.");

	}

}
