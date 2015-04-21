
package integracion.webservicex.responses;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Airports">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WebServiceX" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AirportCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CityOrAirportName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CountryAbbrviation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                             &lt;element name="GMTOffset" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="RunwayLengthFeet" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                             &lt;element name="RunwayElevationFeet" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *                             &lt;element name="LatitudeDegree" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="LatitudeMinute" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="LatitudeSecond" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="LatitudeNpeerS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="LongitudeDegree" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="LongitudeMinute" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="LongitudeSeconds" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="LongitudeEperW" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	"airportsDataSet"
})
@XmlRootElement(name = "string")
public class String {
	
	@XmlElement(name = "Airports", required = true)
	protected String.Airports airportsDataSet;
	
	/**
	 * Gets the value of the airportsDataSet property.
	 * 
	 * @return possible object is {@link String.Airports }
	 * 
	 */
	public String.Airports getNewDataSet() {
	
		return airportsDataSet;
	}
	
	/**
	 * Sets the value of the airportsDataSet property.
	 * 
	 * @param value
	 *            allowed object is {@link String.Airports }
	 * 
	 */
	public void setNewDataSet(String.Airports value) {
	
		this.airportsDataSet = value;
	}
	
	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="WebServiceX" maxOccurs="unbounded" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="AirportCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="CityOrAirportName" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="CountryAbbrviation" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
	 *                   &lt;element name="GMTOffset" type="{http://www.w3.org/2001/XMLSchema}byte"/>
	 *                   &lt;element name="RunwayLengthFeet" type="{http://www.w3.org/2001/XMLSchema}short"/>
	 *                   &lt;element name="RunwayElevationFeet" type="{http://www.w3.org/2001/XMLSchema}short"/>
	 *                   &lt;element name="LatitudeDegree" type="{http://www.w3.org/2001/XMLSchema}byte"/>
	 *                   &lt;element name="LatitudeMinute" type="{http://www.w3.org/2001/XMLSchema}byte"/>
	 *                   &lt;element name="LatitudeSecond" type="{http://www.w3.org/2001/XMLSchema}byte"/>
	 *                   &lt;element name="LatitudeNpeerS" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="LongitudeDegree" type="{http://www.w3.org/2001/XMLSchema}byte"/>
	 *                   &lt;element name="LongitudeMinute" type="{http://www.w3.org/2001/XMLSchema}byte"/>
	 *                   &lt;element name="LongitudeSeconds" type="{http://www.w3.org/2001/XMLSchema}byte"/>
	 *                   &lt;element name="LongitudeEperW" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
		"airports"
	})
	public static class Airports {
		
		@XmlElement(name = "WebServiceX")
		protected List<String.Airports.Airport> airports;
		
		/**
		 * Gets the value of the airports property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the airports property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getTable().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link String.Airports.Airport }
		 * 
		 * 
		 */
		public List<String.Airports.Airport> getTable() {
		
			if (airports == null) {
				airports = new ArrayList<String.Airports.Airport>();
			}
			return this.airports;
		}
		
		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content
		 * contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="AirportCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="CityOrAirportName" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="CountryAbbrviation" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="CountryCode" type="{http://www.w3.org/2001/XMLSchema}short"/>
		 *         &lt;element name="GMTOffset" type="{http://www.w3.org/2001/XMLSchema}byte"/>
		 *         &lt;element name="RunwayLengthFeet" type="{http://www.w3.org/2001/XMLSchema}short"/>
		 *         &lt;element name="RunwayElevationFeet" type="{http://www.w3.org/2001/XMLSchema}short"/>
		 *         &lt;element name="LatitudeDegree" type="{http://www.w3.org/2001/XMLSchema}byte"/>
		 *         &lt;element name="LatitudeMinute" type="{http://www.w3.org/2001/XMLSchema}byte"/>
		 *         &lt;element name="LatitudeSecond" type="{http://www.w3.org/2001/XMLSchema}byte"/>
		 *         &lt;element name="LatitudeNpeerS" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="LongitudeDegree" type="{http://www.w3.org/2001/XMLSchema}byte"/>
		 *         &lt;element name="LongitudeMinute" type="{http://www.w3.org/2001/XMLSchema}byte"/>
		 *         &lt;element name="LongitudeSeconds" type="{http://www.w3.org/2001/XMLSchema}byte"/>
		 *         &lt;element name="LongitudeEperW" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
		    "airportCode", "cityOrAirportName", "country",
		    "countryAbbrviation", "countryCode", "gmtOffset",
		    "runwayLengthFeet", "runwayElevationFeet", "latitudeDegree",
		    "latitudeMinute", "latitudeSecond", "latitudeNpeerS",
		    "longitudeDegree", "longitudeMinute", "longitudeSeconds",
		    "longitudeEperW"
		})
		public static class Airport {
			
			@XmlElement(name = "AirportCode", required = true)
			protected java.lang.String airportCode;
			
			@XmlElement(name = "CityOrAirportName", required = true)
			protected java.lang.String cityOrAirportName;
			
			@XmlElement(name = "Country", required = true)
			protected java.lang.String country;
			
			@XmlElement(name = "CountryAbbrviation", required = true)
			protected java.lang.String countryAbbrviation;
			
			@XmlElement(name = "CountryCode")
			protected short countryCode;
			
			@XmlElement(name = "GMTOffset")
			protected byte gmtOffset;
			
			@XmlElement(name = "RunwayLengthFeet")
			protected short runwayLengthFeet;
			
			@XmlElement(name = "RunwayElevationFeet")
			protected short runwayElevationFeet;
			
			@XmlElement(name = "LatitudeDegree")
			protected byte latitudeDegree;
			
			@XmlElement(name = "LatitudeMinute")
			protected byte latitudeMinute;
			
			@XmlElement(name = "LatitudeSecond")
			protected byte latitudeSecond;
			
			@XmlElement(name = "LatitudeNpeerS", required = true)
			protected java.lang.String latitudeNpeerS;
			
			@XmlElement(name = "LongitudeDegree")
			protected byte longitudeDegree;
			
			@XmlElement(name = "LongitudeMinute")
			protected byte longitudeMinute;
			
			@XmlElement(name = "LongitudeSeconds")
			protected byte longitudeSeconds;
			
			@XmlElement(name = "LongitudeEperW", required = true)
			protected java.lang.String longitudeEperW;
			
			/**
			 * Gets the value of the airportCode property.
			 * 
			 * @return possible object is {@link java.lang.String }
			 * 
			 */
			public java.lang.String getAirportCode() {
			
				return airportCode;
			}
			
			/**
			 * Sets the value of the airportCode property.
			 * 
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 * 
			 */
			public void setAirportCode(java.lang.String value) {
			
				this.airportCode = value;
			}
			
			/**
			 * Gets the value of the cityOrAirportName property.
			 * 
			 * @return possible object is {@link java.lang.String }
			 * 
			 */
			public java.lang.String getCityOrAirportName() {
			
				return cityOrAirportName;
			}
			
			/**
			 * Sets the value of the cityOrAirportName property.
			 * 
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 * 
			 */
			public void setCityOrAirportName(java.lang.String value) {
			
				this.cityOrAirportName = value;
			}
			
			/**
			 * Gets the value of the country property.
			 * 
			 * @return possible object is {@link java.lang.String }
			 * 
			 */
			public java.lang.String getCountry() {
			
				return country;
			}
			
			/**
			 * Sets the value of the country property.
			 * 
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 * 
			 */
			public void setCountry(java.lang.String value) {
			
				this.country = value;
			}
			
			/**
			 * Gets the value of the countryAbbrviation property.
			 * 
			 * @return possible object is {@link java.lang.String }
			 * 
			 */
			public java.lang.String getCountryAbbrviation() {
			
				return countryAbbrviation;
			}
			
			/**
			 * Sets the value of the countryAbbrviation property.
			 * 
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 * 
			 */
			public void setCountryAbbrviation(java.lang.String value) {
			
				this.countryAbbrviation = value;
			}
			
			/**
			 * Gets the value of the countryCode property.
			 * 
			 */
			public short getCountryCode() {
			
				return countryCode;
			}
			
			/**
			 * Sets the value of the countryCode property.
			 * 
			 */
			public void setCountryCode(short value) {
			
				this.countryCode = value;
			}
			
			/**
			 * Gets the value of the gmtOffset property.
			 * 
			 */
			public byte getGMTOffset() {
			
				return gmtOffset;
			}
			
			/**
			 * Sets the value of the gmtOffset property.
			 * 
			 */
			public void setGMTOffset(byte value) {
			
				this.gmtOffset = value;
			}
			
			/**
			 * Gets the value of the runwayLengthFeet property.
			 * 
			 */
			public short getRunwayLengthFeet() {
			
				return runwayLengthFeet;
			}
			
			/**
			 * Sets the value of the runwayLengthFeet property.
			 * 
			 */
			public void setRunwayLengthFeet(short value) {
			
				this.runwayLengthFeet = value;
			}
			
			/**
			 * Gets the value of the runwayElevationFeet property.
			 * 
			 */
			public short getRunwayElevationFeet() {
			
				return runwayElevationFeet;
			}
			
			/**
			 * Sets the value of the runwayElevationFeet property.
			 * 
			 */
			public void setRunwayElevationFeet(short value) {
			
				this.runwayElevationFeet = value;
			}
			
			/**
			 * Gets the value of the latitudeDegree property.
			 * 
			 */
			public byte getLatitudeDegree() {
			
				return latitudeDegree;
			}
			
			/**
			 * Sets the value of the latitudeDegree property.
			 * 
			 */
			public void setLatitudeDegree(byte value) {
			
				this.latitudeDegree = value;
			}
			
			/**
			 * Gets the value of the latitudeMinute property.
			 * 
			 */
			public byte getLatitudeMinute() {
			
				return latitudeMinute;
			}
			
			/**
			 * Sets the value of the latitudeMinute property.
			 * 
			 */
			public void setLatitudeMinute(byte value) {
			
				this.latitudeMinute = value;
			}
			
			/**
			 * Gets the value of the latitudeSecond property.
			 * 
			 */
			public byte getLatitudeSecond() {
			
				return latitudeSecond;
			}
			
			/**
			 * Sets the value of the latitudeSecond property.
			 * 
			 */
			public void setLatitudeSecond(byte value) {
			
				this.latitudeSecond = value;
			}
			
			/**
			 * Gets the value of the latitudeNpeerS property.
			 * 
			 * @return possible object is {@link java.lang.String }
			 * 
			 */
			public java.lang.String getLatitudeNpeerS() {
			
				return latitudeNpeerS;
			}
			
			/**
			 * Sets the value of the latitudeNpeerS property.
			 * 
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 * 
			 */
			public void setLatitudeNpeerS(java.lang.String value) {
			
				this.latitudeNpeerS = value;
			}
			
			/**
			 * Gets the value of the longitudeDegree property.
			 * 
			 */
			public byte getLongitudeDegree() {
			
				return longitudeDegree;
			}
			
			/**
			 * Sets the value of the longitudeDegree property.
			 * 
			 */
			public void setLongitudeDegree(byte value) {
			
				this.longitudeDegree = value;
			}
			
			/**
			 * Gets the value of the longitudeMinute property.
			 * 
			 */
			public byte getLongitudeMinute() {
			
				return longitudeMinute;
			}
			
			/**
			 * Sets the value of the longitudeMinute property.
			 * 
			 */
			public void setLongitudeMinute(byte value) {
			
				this.longitudeMinute = value;
			}
			
			/**
			 * Gets the value of the longitudeSeconds property.
			 * 
			 */
			public byte getLongitudeSeconds() {
			
				return longitudeSeconds;
			}
			
			/**
			 * Sets the value of the longitudeSeconds property.
			 * 
			 */
			public void setLongitudeSeconds(byte value) {
			
				this.longitudeSeconds = value;
			}
			
			/**
			 * Gets the value of the longitudeEperW property.
			 * 
			 * @return possible object is {@link java.lang.String }
			 * 
			 */
			public java.lang.String getLongitudeEperW() {
			
				return longitudeEperW;
			}
			
			/**
			 * Sets the value of the longitudeEperW property.
			 * 
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 * 
			 */
			public void setLongitudeEperW(java.lang.String value) {
			
				this.longitudeEperW = value;
			}
			
		}
		
	}
	
}
