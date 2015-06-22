package services;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientProperties;

import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Named
@Singleton
public class FacebookClient {

	private static final Client INSTANCE = ClientBuilder.newClient()
		.property(ClientProperties.CONNECT_TIMEOUT, 0)
		.property(ClientProperties.READ_TIMEOUT, 0)
		.register(new JacksonJaxbJsonProvider(new JodaMapper(), null));

	public static Client getInstance() {

		return FacebookClient.INSTANCE;

	}

}
