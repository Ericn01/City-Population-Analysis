package model;

public class City{
	
	private String name;
	private double latitude;
	private double longitude;
	private String country;
	private long population;
	private String capitalStatus;
	private String populationAsString;
	
	public City(String name, String latitude, String longitude, String country, String populationAsString, String capitalStatus) {
		setName(name);
		setCountry(country);
		setLatitude(latitude);
		setLongitude(longitude);
		setCapitalStatus(capitalStatus);
		setPopulationAsString(populationAsString); 
	}
	
	// city name getters and setters
	public void setName(String name) {
		this.name = name;
	}
	public String getName() { 
		return removeQuotationMarks(name); 
	}
	
	// country name getters and setters
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitudeAsString) {
		String properlyFormattedLatitude = removeQuotationMarks(latitudeAsString);
		this.latitude = Double.parseDouble(properlyFormattedLatitude);
	}
	

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitudeAsString) {
		String properlyFormattedLongitude = removeQuotationMarks(longitudeAsString);
		this.longitude = Double.parseDouble(properlyFormattedLongitude);
	}
	
	public void setPopulationAsString(String populationAsString) {
		if (removeQuotationMarks(populationAsString).equals("") || removeQuotationMarks(populationAsString) == null) {
			this.populationAsString = "0";
		}
		else {
			String properlyFormattedPopulationString = removeQuotationMarks(populationAsString);
			this.populationAsString = properlyFormattedPopulationString;
		}
		this.population = Long.parseLong(this.populationAsString);
	}
	public String getPopulationAsString(){
		return populationAsString;
	}
	
	// population getters and setters
	public long getPopulation() {
		return population;
	}
	// capital Status getters and setters
	public String getCapitalStatus() {
		return capitalStatus;
	}

	public void setCapitalStatus(String capitalStatus) {
		
		this.capitalStatus = removeQuotationMarks(capitalStatus);
	}
	public String toString() {
		String presentedName = removeQuotationMarks(name);
		String presentedCountry = removeQuotationMarks(country);
		
		String info = String.format("%-1s%1s%-5s%1s%,1d%1s", presentedName, ", ", presentedCountry, ": ", population, " residents");
		return info;
	}
	/**
	 * All data within the csv file is enclosed in quotation marks, making it harder to interpret.
	 * This method removes the quotation marks to make the data easier to deal with.
	 * @param s the inputted string
	 * @return the string without quotation marks
	 */
	public String removeQuotationMarks(String s) {
		return s.substring(1, (s.length() - 1));
	}
}
