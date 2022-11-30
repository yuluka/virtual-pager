package controlGatehouse;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainWindow {
	
	@FXML
    private ImageView BTTN_ANNOUNCE_VISITOR;

    @FXML
    void announceVisitor(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiGatehouse/announce-visitor.fxml"));
    	loader.setController(new AnnounceVisitor());
    	Parent root = loader.load();
    	
    	Stage stage = new Stage();
    	Scene sc = new Scene(root);
    	stage.setScene(sc);
    	stage.show();
    	
    	Stage auxSt = (Stage) BTTN_ANNOUNCE_VISITOR.getScene().getWindow();
    	auxSt.close();
    }

    @FXML
    void showTooltipAnnounceVisitor(MouseEvent event) {
    	Tooltip t = new Tooltip("Anunciar visitante");
    	Tooltip.install(BTTN_ANNOUNCE_VISITOR, t);
    }
    
    public static void showPanicAlert(String panicMessage) {
    	Alert alert = new Alert(AlertType.WARNING);
		
		alert.setTitle("Botón de pánico presionado");
		alert.setContentText(panicMessage);
		alert.show();
	}
    
    public static void showApartmentAnswer(String answer) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Respuesta a petición de entrada");
		alert.setHeaderText("El apartamento ha respondido:");
		alert.setContentText(answer);
		alert.show();
	}
}
