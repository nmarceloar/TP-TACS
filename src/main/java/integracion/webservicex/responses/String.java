
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
			
				return this.airportCode;
			}
			
			/**
			 * Gets the value of the cityOrAirportName property.
			 *
			 * @return possible object is {@link java.lang.String }
			 *
			 */
			public java.lang.String getCityOrAirportName() {
			
				return this.cityOrAirportName;
			}
			
			/**
			 * Gets the value of the country property.
			 *
			 * @return possible object is {@link java.lang.String }
			 *
			 */
			public java.lang.String getCountry() {
			
				return this.country;
			}
			
			/**
			 * Gets the value of the countryAbbrviation property.
			 *
			 * @return possible object is {@link java.lang.String }
			 *
			 */
			public java.lang.String getCountryAbbrviation() {
			
				return this.countryAbbrviation;
			}
			
			/**
			 * Gets the value of the countryCode property.
			 *
			 */
			public short getCountryCode() {
			
				return this.countryCode;
			}
			
			/**
			 * Gets the value of the gmtOffset property.
			 *
			 */
			public byte getGMTOffset() {
			
				return this.gmtOffset;
			}
			
			/**
			 * Gets the value of the latitudeDegree property.
			 *
			 */
			public byte getLatitudeDegree() {
			
				return this.latitudeDegree;
			}
			
			/**
			 * Gets the value of the latitudeMinute property.
			 *
			 */
			public byte getLatitudeMinute() {
			
				return this.latitudeMinute;
			}
			
			/**
			 * Gets the value of the latitudeNpeerS property.
			 *
			 * @return possible object is {@link java.lang.String }
			 *
			 */
			public java.lang.String getLatitudeNpeerS() {
			
				return this.latitudeNpeerS;
			}
			
			/**
			 * Gets the value of the latitudeSecond property.
			 *
			 */
			public byte getLatitudeSecond() {
			
				return this.latitudeSecond;
			}
			
			/**
			 * Gets the value of the longitudeDegree property.
			 *
			 */
			public byte getLongitudeDegree() {
			
				return this.longitudeDegree;
			}
			
			/**
			 * Gets the value of the longitudeEperW property.
			 *
			 * @return possible object is {@link java.lang.String }
			 *
			 */
			public java.lang.String getLongitudeEperW() {
			
				return this.longitudeEperW;
			}
			
			/**
			 * Gets the value of the longitudeMinute property.
			 *
			 */
			public byte getLongitudeMinute() {
			
				return this.longitudeMinute;
			}
			
			/**
			 * Gets the value of the longitudeSeconds property.
			 *
			 */
			public byte getLongitudeSeconds() {
			
				return this.longitudeSeconds;
			}
			
			/**
			 * Gets the value of the runwayElevationFeet property.
			 *
			 */
			public short getRunwayElevationFeet() {
			
				return this.runwayElevationFeet;
			}
			
			/**
			 * Gets the value of the runwayLengthFeet property.
			 *
			 */
			public short getRunwayLengthFeet() {
			
				return this.runwayLengthFeet;
			}
			
			/**
			 * Sets the value of the airportCode property.
			 *
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 *
			 */
			public void setAirportCode(final java.lang.String value) {
			
				this.airportCode = value;
			}
			
			/**
			 * Sets the value of the cityOrAirportName property.
			 *
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 *
			 */
			public void setCityOrAirportName(final java.lang.String value) {
			
				this.cityOrAirportName = value;
			}
			
			/**
			 * Sets the value of the country property.
			 *
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 *
			 */
			public void setCountry(final java.lang.String value) {
			
				this.country = value;
			}
			
			/**
			 * Sets the value of the countryAbbrviation property.
			 *
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 *
			 */
			public void setCountryAbbrviation(final java.lang.String value) {
			
				this.countryAbbrviation = value;
			}
			
			/**
			 * Sets the value of the countryCode property.
			 *
			 */
			public void setCountryCode(final short value) {
			
				this.countryCode = value;
			}
			
			/**
			 * Sets the value of the gmtOffset property.
			 *
			 */
			public void setGMTOffset(final byte value) {
			
				this.gmtOffset = value;
			}
			
			/**
			 * Sets the value of the latitudeDegree property.
			 *
			 */
			public void setLatitudeDegree(final byte value) {
			
				this.latitudeDegree = value;
			}
			
			/**
			 * Sets the value of the latitudeMinute property.
			 *
			 */
			public void setLatitudeMinute(final byte value) {
			
				this.latitudeMinute = value;
			}
			
			/**
			 * Sets the value of the latitudeNpeerS property.
			 *
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 *
			 */
			public void setLatitudeNpeerS(final java.lang.String value) {
			
				this.latitudeNpeerS = value;
			}
			
			/**
			 * Sets the value of the latitudeSecond property.
			 *
			 */
			public void setLatitudeSecond(final byte value) {
			
				this.latitudeSecond = value;
			}
			
			/**
			 * Sets the value of the longitudeDegree property.
			 *
			 */
			public void setLongitudeDegree(final byte value) {
			
				this.longitudeDegree = value;
			}
			
			/**
			 * Sets the value of the longitudeEperW property.
			 *
			 * @param value
			 *            allowed object is {@link java.lang.String }
			 *
			 */
			public void setLongitudeEperW(final java.lang.String value) {
			
				this.longitudeEperW = value;
			}
			
			/**
			 * Sets the value of the longitudeMinute property.
			 *
			 */
			public void setLongitudeMinute(final byte value) {
			
				this.longitudeMinute = value;
			}
			
			/**
			 * Sets the value of the longitudeSeconds property.
			 *
			 */
			public void setLongitudeSeconds(final byte value) {
			
				this.longitudeSeconds = value;
			}
			
			/**
			 * Sets the value of the runwayElevationFeet property.
			 *
			 */
			public void setRunwayElevationFeet(final short value) {
			
				this.runwayElevationFeet = value;
			}
			
			/**
			 * Sets the value of the runwayLengthFeet property.
			 *
			 */
			public void setRunwayLengthFeet(final short value) {
			
				this.runwayLengthFeet = value;
			}
			
		}
		
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
		
			if (this.airports == null) {
				this.airports = new ArrayList<String.Airports.Airport>();
			}
			return this.airports;
		}
		
	}
	
	@XmlElement(name = "Airports", required = true)
	protected String.Airports airportsDataSet;
	
	/**
	 * Gets the value of the airportsDataSet property.
	 *
	 * @return possible object is {@link String.Airports }
	 *
	 */
	public String.Airports getNewDataSet() {
	
		return this.airportsDataSet;
	}
	
	/**
	 * Sets the value of the airportsDataSet property.
	 *
	 * @param value
	 *            allowed object is {@link String.Airports }
	 *
	 */
	public void setNewDataSet(final String.Airports value) {
	
		this.airportsDataSet = value;
	}
	
}
