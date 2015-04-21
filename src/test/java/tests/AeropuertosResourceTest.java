
package tests;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import api.AeropuertosResource;

public class AeropuertosResourceTest extends JerseyTest {
	
	@Override
	protected Application configure() {
	
		this.enable(TestProperties.LOG_TRAFFIC);
		this.enable(TestProperties.DUMP_ENTITY);
		
		return new ResourceConfig(AeropuertosResource.class);
		
	}
	
	@Test
	public void testFindOpcionesDeViaje() {
	
		// create target
		
		final WebTarget target =
		    this.target("/aeropuertos").queryParam("cityName", "buenos");
		
		// create request
		
		final Invocation.Builder invocationBuilder =
		    target.request()
		        .accept(MediaType.APPLICATION_JSON)
		        .header("X-ApiKey", "19638437094c4892a8af7cdbed49ee43");
		
		// invoke
		
		Response response = invocationBuilder.get();
		
		Assert.assertTrue(response != null);
		Assert.assertTrue(response.getStatusInfo().getStatusCode() == 200);
		
	}
}
