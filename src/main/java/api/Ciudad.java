
package api;

public class Ciudad {
	
	private String cityName;
	
	private String cityCode;
	
	private String countryName;
	
	/**
	 * @param cityName
	 * @param cityCode
	 * @param countryName
	 */
	public Ciudad(String cityName, String cityCode, String countryName) {
	
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.countryName = countryName;
	}
	
	public String getCityCode() {
	
		return this.cityCode;
	}
	
	public String getCityName() {
	
		return this.cityName;
	}
	
	public String getCountryName() {
	
		return this.countryName;
	}
	
	public void setCityCode(String cityCode) {
	
		this.cityCode = cityCode;
	}
	
	public void setCityName(String cityName) {
	
		this.cityName = cityName;
	}
	
	public void setCountryName(String countryName) {
	
		this.countryName = countryName;
	}
	
}
