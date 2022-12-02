package uiApto2;

import java.io.IOException;

import controlApto2.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelApto2.ApartmentTwo;

public class Main extends Application {

	public static void main(String[] args) throws IOException {
		ApartmentTwo.initializeThings();
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("main-window.fxml"));
		loader.setController(new MainWindow());
		Parent root = loader.load();
		
		Stage st = new Stage();
		Scene sc = new Scene(root);
		st.setScene(sc);
		st.show();
	}
}
