package view;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import controller.LogicHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DistanceController implements Initializable{
	
	@FXML 
	private TextField cityOne;
	@FXML
	private TextField cityTwo;
	@FXML
	private Button computeDistance;
	@FXML
	private Label showDistance;
	
	private LogicHandler dataHandler = new LogicHandler();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TextFields.bindAutoCompletion(cityOne, dataHandler.loadCountrySuggestions()); // binds the textfield "country name" to the database.
		
	}

}
