
package tests;

import static org.junit.Assert.assertTrue;
import integracion.geobytes.GeobytesCiudadesProvider;

import org.junit.Test;

import api.CiudadesProvider;

public class GeobytesCiudadesProviderTest {
	
	@Test
	public void testFindCiudadesByName() {
	
		CiudadesProvider provider = new GeobytesCiudadesProvider();
		
		assertTrue(provider.findCiudadesByName("madrid")
		    .toLowerCase()
		    .contains("madrid"));
		
		assertTrue(provider.findCiudadesByName("barcelona")
		    .toLowerCase()
		    .contains("barcelona"));
		
		assertTrue(provider.findCiudadesByName("sdgg45gf4g5fd5g")
		    .equals("[\"\"]"));
	}
	
}
