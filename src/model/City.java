package model;

public class City {
	
	private String name;
	private String country;
	private long population;
	private String capitalStatus;
	private String populationAsString;
	
	public City(String name, String country, String populationAsString, String capitalStatus) {
		setName(name);
		setCountry(country);
		setCapitalStatus(capitalStatus);
		setPopulationAsString(populationAsString); // ensures no errors occur - I hope?
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
	
	public void setPopulationAsString(String populationAsString) {
		if (removeQuotationMarks(populationAsString).equals("") || removeQuotationMarks(populationAsString) == null) {
			System.out.println("This is working");
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
		
		String info = String.format("%-1s%1s%-5s%1s%,1d%1s", presentedName, ", ", presentedCountry, ": ", population, " residents.");
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
