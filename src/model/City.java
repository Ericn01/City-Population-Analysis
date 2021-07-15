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
		if(populationAsString.equals("")) { // if the string is empty, give it the default population of 1
			System.out.println("Accessed!");
			setPopulation("0");
		}
		else {
			System.out.println(populationAsString.equals("") || populationAsString == null);
			String properlyFormattedPopulationString = populationAsString.substring(1, (populationAsString.length() - 1));
			setPopulation(properlyFormattedPopulationString);
		}
	}
	
	// city name getters and setters
	public void setName(String name) {
		this.name = name;
	}
	public String getName() { 
		return name; 
	}
	
	// country name getters and setters
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setPopulationAsString(String populationAsString) {
		this.populationAsString = populationAsString;
	}
	public String getPopulationAsString(){
		return populationAsString;
	}
	
	// population getters and setters
	public long getPopulation() {
		return population;
	}

	public void setPopulation(String populationAsString) {
		this.population = Long.parseLong(populationAsString);
	}

	// capital Status getters and setters
	public String getCapitalStatus() {
		return capitalStatus;
	}

	public void setCapitalStatus(String capitalStatus) {
		
		this.capitalStatus = capitalStatus;
	}
	public String toString() {
		String presentedName = name.substring(1, name.length() - 1);
		String presentedCountry = country.substring(1, country.length() - 1);
		
		String info = String.format("%-1s%1s%-5s%1s%,1d%1s", presentedName, ", ", presentedCountry, ": ", population, " residents.");
		return info;
	}
	
	
	

}
