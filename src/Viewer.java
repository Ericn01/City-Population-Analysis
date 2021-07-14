import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Viewer {
	
	public Viewer(Stage primaryStage){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Overview.fxml"));
			Scene scene = new Scene(root,1000,535);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
