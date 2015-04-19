/**
 * 
 */

package tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import api.Aeropuerto;
import api.StaticAeropuertosProvider;

/**
 * @author nmarcelo.ar
 *
 */
public class StaticAeropuertosProviderTest {
	
	StaticAeropuertosProvider provider;
	
	@Before()
	public void before() {
	
		provider = new StaticAeropuertosProvider();
		
	}
	
	/**
	 * Test method for
	 * {@link api.StaticAeropuertosProvider#StaticAeropuertosProvider()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStaticAeropuertosProvider() {
	
		Assert.assertTrue(provider.hasAeropuertos());
		
	}
	
	/**
	 * Test method for
	 * {@link api.StaticAeropuertosProvider#findAeropuertosByCity(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindAeropuertosByCity() {
	
		List<Aeropuerto> byName =
		    provider.findAeropuertosByCity("bariloche");
		
		Assert.assertTrue(byName.size() == 1);
		
	}
	
}
