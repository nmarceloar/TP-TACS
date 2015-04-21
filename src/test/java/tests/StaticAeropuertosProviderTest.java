/**
 * 
 */

package tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import api.Aeropuerto;
import api.StaticAeropuertosProvider;

public class StaticAeropuertosProviderTest {
	
	/**
	 * Test method for
	 * {@link api.StaticAeropuertosProvider#findAeropuertosByCity(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindAeropuertosByCity() {
	
		StaticAeropuertosProvider provider = new StaticAeropuertosProvider();
		
		final List<Aeropuerto> byName =
		    provider.findAeropuertosByCity("buenos");
		
		Assert.assertTrue(byName.size() == 1);
		
	}
	
	/**
	 * Test method for
	 * {@link api.StaticAeropuertosProvider#StaticAeropuertosProvider()}.
	 *
	 * @throws Exception
	 */
	@Test
	public void testStaticAeropuertosProvider() {
	
		StaticAeropuertosProvider provider = new StaticAeropuertosProvider();
		
		Assert.assertTrue(provider.hasAeropuertos());
		
	}
	
}
