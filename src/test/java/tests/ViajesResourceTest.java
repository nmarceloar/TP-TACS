
package tests;

import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

import api.OpcionDeViaje;
import api.ViajesResource;

public class ViajesResourceTest extends JerseyTest {
	
	@Override
	protected Application configure() {
	
		this.enable(TestProperties.LOG_TRAFFIC);
		this.enable(TestProperties.DUMP_ENTITY);
		
		return new ResourceConfig(ViajesResource.class);
		
	}
	
	@Test
	public void testFindOpcionesDeViaje() {
	
		List<OpcionDeViaje> opciones = null;
		
		DateTime fechaIda = DateTime
		                .now().plusDays(5);
		DateTime fechaVuelta = fechaIda
		                .plusDays(30);
		
		DateTimeFormatter fmt = DateTimeFormat
		                .forPattern("dd/MM/yyyy");
		
		// create target
		
		final WebTarget target = this
		                .target("/viajes")
		                .queryParam("aeroOrigen", "BUE")
		                .queryParam("aeroDestino", "MIA")
		                .queryParam("fechaIda", fmt.print(fechaIda))
		                .queryParam("fechaVuelta", fmt.print(fechaVuelta));
		
		// create request
		
		final Invocation.Builder invocationBuilder =
		    target
		                    .request()
		                    .accept(MediaType.APPLICATION_JSON)
		                    .header("X-ApiKey",
		                        "19638437094c4892a8af7cdbed49ee43");
		
		// invoke
		
		Response response = invocationBuilder
		                .get();
		
		Assert.assertTrue(response != null);
		Assert.assertTrue(response
		                .getStatusInfo().getStatusCode() == 200);
		
	}
}
