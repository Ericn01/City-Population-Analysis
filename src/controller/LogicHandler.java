package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;

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
	 * This method queries the database based off of the given inputs
	 * @param minPop minimum population of city
	 * @param maxPop maximum population of city
	 * @param countryName the name of the country to be searched
	 * @param cityName    the name of the city to be searched
	 * @param capitalStatus the capital status of the city to be searched
	 */
	public ArrayList<City> searchDatabaseFull(long minPop, long maxPop, String countryName, String cityName, String capitalStatus) {
		ArrayList<City> results = new ArrayList<City>();
		if (countryName.equals("") && cityName.equals("")) {
			System.out.println("Country and City have not been selected - Clause 1 has been accessed.");
			for (City i: cities) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {
					System.out.println(i);
					results.add(i);
				}
			}
		}
		else if(countryName.equals("") && !cityName.equals("")) {
			System.out.println("Country has not been selected - Clause 2 has been accessed.");
			for (City i: cities) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && cityName.contains(i.getName()) && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {
					System.out.println(i);
					results.add(i);
				}
			}
		}
		else if(!countryName.equals("") && cityName.equals("")) {
			System.out.println("City has not been selected - Clause 3 has been accessed.");
			for (City i: cities) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && countryName.contains(i.getName()) && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {
					System.out.println(i);
					results.add(i);
				}
			}
		}
		// top condition - everything is left to the default values
		else {
			System.out.println("All fields have a value - Clause 4 has been accessed.");
			for (City i: cities) {
				if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && countryName.contains(i.getCountry())
					&& cityName.contains(i.getName()) && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {
					System.out.println(i);
					results.add(i);
				}
			}
		}
		System.out.println(results);
		return results;
	}
	
	@Override
	public int compare(City o1, City o2) {
		return Long.valueOf(o1.getPopulation()).compareTo(Long.valueOf(o2.getPopulation()));
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}
	public void printCities() {
		for (City i: cities) {
			System.out.println(i);
		}
	}
}
