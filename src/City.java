
public class City {
	
	private String name;
	private String country;
	private long population;
	private String capitalStatus;
	
	public City(String name, String country, long population, String capitalStatus) {
		setName(name);
		setCountry(country);
		setPopulation(population);
		setCapitalStatus(capitalStatus);
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

	// population getters and setters
	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	// capital Status getters and setters
	public String getCapitalStatus() {
		return capitalStatus;
	}

	public void setCapitalStatus(String capitalStatus) {
		
		this.capitalStatus = capitalStatus;
	}
	

}
