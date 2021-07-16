package application;

import java.util.Collections;
import java.util.Comparator;

import controller.LogicHandler;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.Viewer;


/**
 * This class is in charge of deploying the application
 * @author Eric Nielsen
 * @version 1.0
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		LogicHandler handler = new LogicHandler();
		handler.loadCityData(); // loads the arrayList 
		//handler.printCities();
		// Used anchor pane - non resizable
		primaryStage.setResizable(false);
		// Setting application title
		primaryStage.setTitle("City Analysis");
		// Creating the icon for the project
		
		Image icon = new Image("icon.jpg");
		primaryStage.getIcons().add(icon);
		
		Viewer gui = new Viewer(primaryStage);
	}
	public static void main(String[] args) {
		launch(args);
	}
}