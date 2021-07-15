package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.LogicHandler;
import javafx.beans.value.ChangeListener;
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
	private String[] options = {"Capital City", "Administrative", "Minor", "No Status"};
	
	@FXML 
	private ListView<String> resultsList;
	
	@FXML
	private Button queryButton;
	@FXML
	private Button clearButton;
	
	
	public void query(ActionEvent e) {
		System.out.println("Querying...");
		exceptionLabel.setText("Querying the database...");
		exceptionLabel.setTextFill(Color.GREEN);
		exceptionLabel.setFont(new Font("System", 20));
		
		ArrayList<City> matches;
		LogicHandler dataHandler = new LogicHandler();

		matches = dataHandler.searchDatabase(Long.parseLong(minPopulation.getText()), Long.parseLong(maxPopulation.getText()), 
				                   countryName.getText(), cityName.getText(), capitalStatus.getValue());
		resultsList.getItems().addAll(matches.toString());
		resultsLabel.setText(matches.size() + " results were found!");
	}
	
	public void clear(ActionEvent e) {
		minPopulation.clear();
		maxPopulation.clear();
		countryName.clear();
		cityName.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		capitalStatus.getItems().addAll(options);
		
		// setting up some default values
		minPopulation.setText("0");
		maxPopulation.setText("1500000");
		countryName.setText("Canada");
		cityName.setText("Calgary");
		capitalStatus.setPromptText("No Status");
	}
	
	
}
