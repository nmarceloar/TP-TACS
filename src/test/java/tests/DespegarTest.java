/**
 * 
 */

package tests;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import api.Despegar;
import api.OpcionDeViaje;

/**
 * @author nmarcelo.ar
 *
 */
public class DespegarTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	
	/**
	 * Test method for
	 * {@link api.Despegar#findOpcionesDeViaje(java.lang.String, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime)}
	 * .
	 */
	@Test
	public void testFindOpcionesDeViaje() {
	
		Despegar despegar = new Despegar();
		
		List<OpcionDeViaje> opciones =
		    despegar.findOpcionesDeViaje("BUE", "MIA", new DateTime(2015, 4,
		        22, 0,
		        0), new DateTime(2015, 4, 30, 0, 0));
		
		Assert.assertNotNull(opciones);
		
		for (OpcionDeViaje opcion : opciones) {
			
			System.out.println(opcion.toString() + "\n");
			
		}
		
	}
	
}
