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

	/**
     * Opens a new window where the doorman can enter the visitor's information and the 
     * destination apartment, when the user clicks over the button. 
     * 
     * @param event the mouse click.
     * @throws IOException
     */
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

    /**
     * Shows a label, with a description of what the button does, when the mouse is moved
     * over the announce visitor button.
     * 
     * @param event the mouse moving over the button.
     */
    @FXML
    void showTooltipAnnounceVisitor(MouseEvent event) {
    	Tooltip t = new Tooltip("Anunciar visitante");
    	Tooltip.install(BTTN_ANNOUNCE_VISITOR, t);
    }
    
    /**
     * Shows an alert informing that an apartment has pressed the panic button.
     * 
     * @param panicMessage the panic alert message.
     */
    public static void showPanicAlert(String panicMessage) {
    	Alert alert = new Alert(AlertType.WARNING);
		
		alert.setTitle("Botón de pánico presionado");
		alert.setContentText(panicMessage);
		alert.show();
	}
    
    /**
     * Shows an alert with the apartment's answer to a visitor announcement.
     * 
     * @param answer the apartment's answer.
     */
    public static void showApartmentAnswer(String answer) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Respuesta a petición de entrada");
		alert.setHeaderText("El apartamento ha respondido:");
		alert.setContentText(answer);
		alert.show();
	}
}
