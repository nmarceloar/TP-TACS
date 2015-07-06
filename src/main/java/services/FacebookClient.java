package services;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientProperties;

import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class FacebookClient {

	private static final Client INSTANCE = ClientBuilder.newClient()
		.property(ClientProperties.CONNECT_TIMEOUT, 0)
		.property(ClientProperties.READ_TIMEOUT, 0)
		.register(new JacksonJaxbJsonProvider(new JodaMapper(), null));

	public static Client getInstance() {

		return FacebookClient.INSTANCE;

	}

	private FacebookClient() {

		Logger.getLogger(this.getClass().getCanonicalName())
			.info("Facebook Ok.");
	}

}
