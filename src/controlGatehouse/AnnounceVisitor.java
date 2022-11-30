package controlGatehouse;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelGatehouse.Gatehouse;

public class AnnounceVisitor {
	
	@FXML
    private Button BTTN_ANNOUNCE_VISITOR;

    @FXML
    private RadioButton RBUTTON_APTO1;

    @FXML
    private RadioButton RBUTTON_APTO2;

    @FXML
    private TextField TXT_VISITOR_LASTNAME;

    @FXML
    private TextField TXT_VISITOR_NAME;
    
    @FXML
    void announceVisitor(ActionEvent event) throws IOException {
    	String visitorName = TXT_VISITOR_NAME.getText() + " " + TXT_VISITOR_LASTNAME.getText();
    	int destinationApto = 0;
    	
    	if(RBUTTON_APTO1.isSelected()) {
    		destinationApto = 1;
    	} else {
    		destinationApto = 2;
    	}
    	
//    	Gatehouse.initializeThings();
    	Gatehouse.announceVisitor(visitorName, destinationApto);
    	
    	goToMainWindow();
    }
    
    public void goToMainWindow() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiGatehouse/main-window.fxml"));
    	loader.setController(new MainWindow());
    	Parent root = loader.load();
    	
    	Stage stage = new Stage();
    	Scene sc = new Scene(root);
    	stage.setScene(sc);
    	stage.show();
    	
    	Stage auxSt = (Stage) BTTN_ANNOUNCE_VISITOR.getScene().getWindow();
    	auxSt.close();
	}
    
//    public static void showApartmentAnswer(String answer) {
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Respuesta a petición de entrada");
//		alert.setHeaderText("El apartamento ha respondido:");
//		alert.setContentText(answer);
//		alert.show();
//	}
}
