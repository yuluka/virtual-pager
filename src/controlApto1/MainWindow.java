package controlApto1;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelApto1.ApartmentOne;

public class MainWindow {

    @FXML
    private ImageView BTTN_CHAT;

    @FXML
    private ImageView BTTN_PANIC;

    @FXML
    void beginChat(MouseEvent event) {
    	
    }

    @FXML
    void pressPanicButton(MouseEvent event) throws IOException {
    	ApartmentOne.pressPanicButton();
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Alert sent");
    	alert.setContentText("La alerta ha sido enviada.");
    	alert.show();
    }

    @FXML
    void showChatTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Iniciar chat");
    	Tooltip.install(BTTN_CHAT, t);
    }

    @FXML
    void showPanicButtonTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Presionar el botón de pánico");
    	Tooltip.install(BTTN_PANIC, t);
    }
    
    public static void showConfirmationDialog(String message) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Visitante anunciado");
		alert.setHeaderText("Un visitante, identificado con el nombre " + message 
				+ ", ha solicitado ir a su apartamento.");
		alert.setContentText("¿Desea permitir su ingreso?");
		
		ButtonType bttnYes = new ButtonType("Yup");
		ButtonType bttnNo = new ButtonType("Noup");
		
		alert.getButtonTypes().setAll(bttnYes,bttnNo);
		
//		alert.show();
		
		Optional<ButtonType> answer = alert.showAndWait();
		
		if(answer.get() == bttnYes) {
			sendAnswer(true);
		} else {
			sendAnswer(false);
		}
	}
    
    public static void sendAnswer(boolean answer) throws IOException {
    	ApartmentOne.sendAnswer(answer);
	}
}
