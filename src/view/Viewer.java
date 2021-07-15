package view;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Viewer {
	
	public Viewer(Stage primaryStage){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("overview.fxml"));
			Scene scene = new Scene(root,1000,725);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
