package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import controller.LogicHandler;
import exceptions.MinGreaterThanMaxException;
import exceptions.NegativeValueException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.City;

public class OverviewController implements Initializable{
	
	/**
	 * All text fields must have a default value set to them in order to avoid a NumberFormatException.
	 * This program should be able to handle blank inputs.
	 */
	@FXML
	private TextField minPopulation = new TextField();
	@FXML
	private TextField maxPopulation;
	@FXML
	private TextField countryName;
	@FXML 
	private TextField cityName;
	@FXML
	private Label resultsLabel;
	@FXML 
	private Label exceptionLabel;
	@FXML
	private ComboBox<String> capitalStatus;
	private String[] options = {"Capital City", "Administrative", "Minor", "No Status", "Don't Care"};
	
	@FXML 
	private ListView<City> resultsList = new ListView<City>();
	
	@FXML
	private Button queryButton;
	@FXML
	private Button clearButton;
	@FXML 
	private Button topButton1;
	
	
	public void query(ActionEvent e) {
		try {
			
			LogicHandler dataHandler = new LogicHandler();
			
			dataHandler.clearData(); // no repetition of data
			dataHandler.loadCityData();
			// Initializing local input variables
			long minPopulationInput = Long.parseLong(minPopulation.getText());
			long maxPopulationInput = Long.parseLong(maxPopulation.getText());
			String countryNameInput = countryName.getText();
			String cityNameInput = cityName.getText();
			String capitalStatusInput = capitalStatus.getValue();
			
			// exception checks
			checkMinAndMax(minPopulationInput, maxPopulationInput);
			checkPositiveValue(minPopulationInput);
			checkPositiveValue(maxPopulationInput);
			
			System.out.println("Querying...");
			exceptionLabel.setText("Querying the database...");
			exceptionLabel.setTextFill(Color.GREEN);
			exceptionLabel.setFont(new Font("System", 14));
		
			ArrayList<City> matches;
			ArrayList<City> cities = dataHandler.getCities();
		
			dataHandler.printAnswers(minPopulationInput, maxPopulationInput, countryNameInput, cityNameInput, capitalStatusInput);
			matches = dataHandler.searchDatabaseFull(minPopulationInput, maxPopulationInput, countryNameInput, cityNameInput, capitalStatusInput, cities);
		
			resultsList.getItems().addAll(matches); // adds all of the matches to the list view
			if (matches.size() > 1) {
				resultsLabel.setText(matches.size() + " results were found!");
			}
			else if (matches.size() == 1) {
				resultsLabel.setText("1 match was found!");
			}
			else {
				resultsLabel.setText("No results were found...");
			}
		}
		
		catch(NumberFormatException error) {
			exceptionLabel.setText("Enter a value for both the minimum and maximum population.");
			exceptionLabel.setTextFill(Color.RED);
			exceptionLabel.setFont((new Font("System", 14)));
		}
		catch(NegativeValueException error) {
			exceptionLabel.setText("Enter values greater than 0 in the population fields.");
			exceptionLabel.setFont((new Font("System", 14)));
		}
		catch(MinGreaterThanMaxException error) {
			exceptionLabel.setText("The maximum population must be greater than the minimum population.");
			exceptionLabel.setTextFill(Color.RED);
			exceptionLabel.setFont((new Font("System", 14)));
		}
	}
	
	public static void checkMinAndMax(long min, long max) throws MinGreaterThanMaxException{
		if (min > max) {
			throw new MinGreaterThanMaxException("Minimum value cannot be greater than the maximum value!");
		}
	}
	
	public static void checkPositiveValue(long n) throws NegativeValueException{
		if (n < 0) {
			throw new NegativeValueException("The entered value cannot be less than 0.");
		}
	}
	public void clear(ActionEvent e) {
		minPopulation.setText("0");
		maxPopulation.setText("0");
		countryName.setText("");
		cityName.setText("");
		resultsList.getItems().clear();
		resultsLabel.setText("Results will appear here...");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		capitalStatus.getItems().addAll(options);
		
		// setting up some default values
		minPopulation.setText("0");
		maxPopulation.setText("10000");
		countryName.setText("");
		cityName.setText("");
		capitalStatus.getSelectionModel().select(4);
	}
	
	
}
