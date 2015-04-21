
package tests;

import integracion.webservicex.Airport;
import integracion.webservicex.AirportSoap;

import org.json.XML;
import org.junit.AfterClass;
import org.junit.Test;

public class AirportTest {
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	
	}
	
	@Test
	public void test() {
	
		Airport airportsService = new Airport();
		
		AirportSoap soapClient = airportsService.getAirportSoap();
		
		System.out.println(soapClient.getAirportInformationByCityOrAirportName("barcelona"));
		
		System.out.println(XML.toJSONObject(soapClient.getAirportInformationByCityOrAirportName("barcelona")));
		
		System.out.println(soapClient.getAirportInformationByCityOrAirportName("ciudadquenoexiste"));
		
		System.out.println(XML.toJSONObject(soapClient.getAirportInformationByCityOrAirportName("ciudadquenoexiste")));
		
	}
	
}
