package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import model.City;


public class LogicHandler {
	
	private ArrayList<City> cities = new ArrayList<City>();
	
	public void loadCityData() {
		String filePath = "./res/worldcities.csv";
		String currentLineData = "";
		int count = 1;
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
				count++;
			}
			long end = System.nanoTime();
			long total = end - start;
			System.out.println("\nThe database containing " + "(" + cities.size() + ") cities has been parsed successfully in " + (total / 1_000_000) + "ms");
			System.out.println("Parsing speed: " + (cities.size() / (total / 1_000_000)) + " cities/ms");
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
	
	public String convertCapitalStatus(String status) {
		String convertedStatus = "";
		switch(status) {
		case "Capital City":
			convertedStatus = "primary";
			break;
		case "Administrative":
			convertedStatus = "admin";
			break;
		case "No Status":
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
	public ArrayList<City> searchDatabase(long minPop, long maxPop, String countryName, String cityName, String capitalStatus) {
		ArrayList<City> results = new ArrayList<City>();
		System.out.println("Converted capital status: " + convertCapitalStatus(capitalStatus) + " vs " + capitalStatus);
		// top condition - everything is left to the default values
		for (City i: cities) {
			if (i.getPopulation() >= minPop && i.getPopulation() <= maxPop && i.getCountry().contains(countryName) 
					&& i.getName().contains(cityName) && i.getCapitalStatus().equals(convertCapitalStatus(capitalStatus))) {
				System.out.println(i);
				results.add(i);
			}
		}
		return results;
	}
}
