package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import model.City;


public class LogicHandler implements Comparator<City>{
	
	private ArrayList<City> cities = new ArrayList<City>();
	
	public void loadCityData() {
		String filePath = "./res/worldcities.csv";
		String currentLineData = "";
		int count = 1;
		int citiesWithoutAPopulation = 0;
		long start = System.nanoTime();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			br.readLine(); // skip over the first line: it cannot be interpreted properly.
			while ((currentLineData = br.readLine()) != null) {
				String[] values = currentLineData.split(","); // different values depending on position
				
				String cityName = values[0]; // declares the first value in the text file as the serial number.
				String countryName = values[4]; // fifth value in the text file is the country's name.
				String capitalStatus = values[8]; // the ninth value in the file holds the capital status
				String cityPopulation = values[9]; // the tenth value in the csv file holds the population
				
				City currentCity = new City(cityName, countryName, cityPopulation, capitalStatus);
				System.out.println("[" + count + "] " + currentCity); // test to see that everything is working.
				cities.add(currentCity); // add the city to the arrayList
				
				// this code is simply for testing - curious to see discrepancies in the database and what kind of action should be taken
				if (currentCity.getPopulation() == 0) {
					citiesWithoutAPopulation++;
				}
				count++;
			}
			long end = System.nanoTime();
			long total = end - start;
			
			// prints out a report about the database parsing from info that was collected.
			parseReport(listSize(), total, citiesWithoutAPopulation);
		
			br.close();
		} 
		catch (IOException e) {
			System.out.println("An error occured while parsing through the file");
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			System.out.println("\nCannot convert null to a value - please add a value to the empty string");
			e.getMessage();
		}
	}
	/**
	 * Prints a report about the parsing process of the database
	 * @param databaseSize  The size of database
	 * @param parsingTime   The amount of time it took to parse the file (in ms).
	 */
	public void parseReport(int databaseSize, long parsingTime, int citiesNoPop) {
		final int NANO_TO_MILLISECOND = 1_000_000;
		String percentNoPopulation = String.format("%1.2f", (((double) citiesNoPop / databaseSize) * 100));
		System.out.println("\nThe database containing " + "(" + databaseSize + ") cities has been parsed successfully in " + (parsingTime / NANO_TO_MILLISECOND) + "ms");
		System.out.println("Parsing speed: " + (databaseSize / (parsingTime / NANO_TO_MILLISECOND)) + " cities/ms");
		System.out.println("There are " + citiesNoPop + " (" + percentNoPopulation + "%) cities with a reported population of zero in the database");
	}
	
	public int listSize() {
		return cities.size();
	}
	
	public void updateCities() {
		FileWriter openFile;
		try {
			openFile = new FileWriter("res/worldcities.csv");
			PrintWriter updateFile = new PrintWriter(openFile);
			for (City city : cities) {
				// Setting up all the default attributes
				String cityName = city.getName();
				String countryName = city.getCountry();
				String capitalStatus = city.getCapitalStatus();
				String cityPopulation = city.getPopulationAsString();
			
				updateFile.printf("%1s%1s%1s, %1s", cityName, countryName, capitalStatus, cityPopulation);
			}
			updateFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Gives a detailed report of all the inputs that the user has given - mostly used for troubleshooting
	 * @param minPopulation the minimum population selected
	 * @param maxPopulation the maximum population selected
	 * @param countryName   the desired country's name
	 * @param cityName      the desired city's name
	 * @param capitalStatus the capital status of the selected location
	 */
	public void printAnswers(long minPopulation, long maxPopulation, String countryName, String cityName, String capitalStatus) {
		System.out.println("\nUser Query Inputs");
		for (int i = 0; i < 40; i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.format("%-5s%-20s%,1d%n", "(1)", "Minimum Population: ", minPopulation);
		System.out.format("%-5s%-20s%,1d%n", "(2)", "Maximum Population: ", maxPopulation);
		System.out.format("%-5s%-20s%1s%n", "(3)", "Country Name: ", countryName);
		System.out.format("%-5s%-20s%1s%n", "(4)", "City Name: ", cityName);
		System.out.format("%-5s%-20s%1s%n", "(5)", "Capital Status: ", convertCapitalStatus(capitalStatus));
		for (int i = 0; i < 40; i++) {
			System.out.print("-");
		}
		System.out.println("\n\nEnd of Report");
	}
	
	public String convertCapitalStatus(String status) {
		String convertedStatus = "";
		switch(status) {
		case "Capital City":
			convertedStatus = "primary";
			break;
		case "Administrative":
			convertedStatus = "admin";
			break;
		case "Minor":
			convertedStatus = "minor";
			break;
		default:
			break;
		}
		return convertedStatus;
	}
	/**
	 * This method queries the database based off of the given inputs. 
	 * Beware, there's some terrible code in here, it works, but everything has been repeated - definitely needs a cleanup
	 * @param minPop minimum population of city
	 * @param maxPop maximum population of city
	 * @param countryName the name of the country to be searched
	 * @param cityName    the name of the city to be searched
	 * @param capitalStatus the capital status of the city to be searched
	 */
	public ArrayList<City> searchDatabaseFull(long minPop, long maxPop, String countryName, String cityName, String capitalStatus, ArrayList<City> cityCollection) {
		ArrayList<City> results = new ArrayList<City>();
		// no field has been selected - this is the most general search possible (000)
		if (countryName.equals("") && cityName.equals("") && capitalStatus.equals("Don't Care")) {
			System.out.println("Country and City and Capital Status have not been selected - Clause 1 has been accessed.");
			for (City i: cityCollection) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop) {
					results.add(i);
				}
			}
		}
		// the country's name and the city's name have not been selected. Capital status is selected (001)
		else if (countryName.equals("") && cityName.equals("") && !capitalStatus.equals("Don't Care")) {
			System.out.println("Country and city have not been selected - Clause 2 has been accessed");
			for (City i: cityCollection) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {
					results.add(i);
				}
			}
		}
		// the country's name and capital status have not been selected. city name has a value. (010)
		else if (countryName.equals("") && !cityName.equals("") && capitalStatus.equals("Don't Care")) {
			System.out.println("Country name and capital status have not been selected - Clause 3 has been accessed");
			for (City i: cityCollection) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getName().contains(cityName)) {
					results.add(i);
				}
			}
		}
		// the city's name and the capital status have not been selected. Country name is selected (100)
		else if (!countryName.equals("") && cityName.equals("") && capitalStatus.equals("Don't Care")) {
			System.out.println("City and capital status have not been selected - Clause 4 has been accessed");
			for (City i: cityCollection) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getCountry().contains(countryName)) {
					results.add(i);
				}
			}
		}
		// Capital status has not been selected, everything else has a value (110)
		else if(!countryName.equals("") && !cityName.equals("") && capitalStatus.equals("Don't Care")) {
			System.out.println("Capital status has not been selected - Clause 5 has been accessed.");
			for (City i: cityCollection) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getName().contains(cityName) && i.getCountry().contains(countryName)) {
					results.add(i);
				}
			}
		}
		// City name does not have a value. Everything else is filled out (101)
		else if(!countryName.equals("") && cityName.equals("") && !capitalStatus.equals("Don't Care")) {
			System.out.println("City name has not been selected - Clause 6 has been accessed.");
			for (City i: cityCollection) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getCountry().contains(countryName) && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {

					results.add(i);
				}
			}
		}
		// Country name does not have a value - everything else has one. (011)
		else if(countryName.equals("") && !cityName.equals("") && !capitalStatus.equals("Don't Care")) {
			System.out.println("Country name has not been selected - Clause 7 has been accessed.");
			for (City i: cityCollection) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getName().contains(cityName) && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {
					results.add(i);
				}
			}
		}
		// Everything has a value associated with it (111)
		else {
			System.out.println("All fields have a value - Clause 8 has been accessed.");
			for (City i: cityCollection) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getCountry().contains(countryName)
					&& i.getName().contains(cityName) && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {
					results.add(i);
				}
			}
		}
		System.out.println(results);
		return results;
	}
	
	public void clearData() {
		cities.clear();
	}
	
	@Override
	public int compare(City o1, City o2) {
		return Long.valueOf(o1.getPopulation()).compareTo(Long.valueOf(o2.getPopulation()));
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}
	
	public ArrayList<String> loadCountrySuggestions() {
		ArrayList<String> countries = new ArrayList<String>();
		for (City i: cities) {
			int usableLength = i.getCountry().length() - 1;
			String country = i.getCountry().substring(1, usableLength);
			countries.add(country);
		}
		ArrayList<String> countriesNoDuplicates = (ArrayList<String>) countries.stream().distinct().collect(Collectors.toList());
		return countriesNoDuplicates;
	}
	
	public void printCities() {
		for (City i: cities) {
			System.out.println(i);
		}
	}
}
