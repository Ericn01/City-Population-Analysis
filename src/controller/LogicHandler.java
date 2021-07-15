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
				long cityPopulation = city.getPopulation();	
				
				updateFile.printf("%1s%1s%1s, %1d", cityName, countryName, capitalStatus, cityPopulation);
			}
			
			
			updateFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
