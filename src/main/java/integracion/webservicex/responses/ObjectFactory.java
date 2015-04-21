
package integracion.webservicex.responses;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
	
	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: integracion.geobytes
	 * 
	 */
	public ObjectFactory() {
	
	}
	
	/**
	 * Create an instance of {@link String }
	 * 
	 */
	public String createString() {
	
		return new String();
	}
	
	/**
	 * Create an instance of {@link String.Airports }
	 * 
	 */
	public String.Airports createStringNewDataSet() {
	
		return new String.Airports();
	}
	
	/**
	 * Create an instance of {@link String.Airports.Airport }
	 * 
	 */
	public String.Airports.Airport createStringNewDataSetTable() {
	
		return new String.Airports.Airport();
	}
	
}
