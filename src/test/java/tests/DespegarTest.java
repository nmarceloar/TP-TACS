/**
 * 
 */

package tests;

import integracion.despegar.Despegar;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import api.OpcionDeViaje;

/**
 * @author nmarcelo.ar
 *
 */
public class DespegarTest {
	
	@Test
	public void testFindOpcionesDeViaje() {
	
		Despegar despegar = new Despegar();
		
		DateTime fechaIda = DateTime.now().plusDays(5);
		DateTime fechaVuelta = fechaIda.plusDays(30);
		
		List<OpcionDeViaje> opciones =
		    despegar.findOpcionesDeViaje("BUE", "MIA", fechaIda, fechaVuelta);
		
		Assert.assertNotNull(opciones);
		Assert.assertTrue(opciones.size() > 0);
		
	}
}
