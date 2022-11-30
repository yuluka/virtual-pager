package uiGatehouse;

import java.io.IOException;

import controlGatehouse.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelGatehouse.Gatehouse;

public class Main extends Application {

	public static void main(String[] args) throws IOException {
		Gatehouse.initializeThings();
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("main-window.fxml"));
		loader.setController(new MainWindow());
		Parent root = loader.load();
		
		Stage stage = new Stage();
		Scene sc = new Scene(root);
		stage.setScene(sc);
		stage.show();
	}
	
}
