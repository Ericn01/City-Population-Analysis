
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * This class is in charge of deploying the application
 * @author Eric Nielsen
 * @version 1.0
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		// Used anchor pane - non resizable
		primaryStage.setResizable(true);
		// Setting application title
		primaryStage.setTitle("City Analysis");
		// Creating the icon for the project
		Image icon = new Image("res/icon.jpg");
		primaryStage.getIcons().add(icon);
		
		Viewer gui = new Viewer(primaryStage);
	}
	public static void main(String[] args) {
		launch(args);
	}
}