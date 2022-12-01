package controlApto1;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelApto1.ApartmentOne;

public class MainWindow implements Initializable {

    @FXML
    private ImageView BTTN_CHAT;

    @FXML
    private ImageView BTTN_PANIC;

    /**
     * Opens a window where the apartment can chats with the other apartment, when the
     * user clicks over the begin chat button.
     * 
     * @param event the mouse click.
     * @throws IOException
     */
    @FXML
    public void beginChat(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiApto1/chat-window.fxml"));
    	loader.setController(new Chat());
    	Parent root = loader.load();
    	
    	Stage st = new Stage();
    	Scene sc = new Scene(root);
    	st.setScene(sc);
    	st.show();
    	
    	Stage auxSt = (Stage) BTTN_CHAT.getScene().getWindow();
    	auxSt.close();
    }
    
    /**
     * Sends a panic alert to the gatehouse and shows an alert saying that the panic alert
     * was sent, when the user clicks over the press panic button.
     * 
     * @param event the mouse click.
     * @throws IOException
     */
    @FXML
    void pressPanicButton(MouseEvent event) throws IOException {
    	ApartmentOne.pressPanicButton();
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Alert sent");
    	alert.setContentText("La alerta ha sido enviada.");
    	alert.show();
    }

    /**
     * Shows a label, with a description of what the button does, when the mouse is moved
     * over the begin chat button.
     * 
     * @param event the mouse moving over the button.
     */
    @FXML
    void showChatTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Iniciar chat");
    	Tooltip.install(BTTN_CHAT, t);
    }

    /**
     * Shows a label, with a description of what the button does, when the mouse is moved
     * over the press panic button.
     * 
     * @param event the mouse moving over the button.
     */
    @FXML
    void showPanicButtonTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Presionar el botón de pánico");
    	Tooltip.install(BTTN_PANIC, t);
    }
    
    /**
     * Shows an alert with a visitor's announcement and allows the user to choose if 
     * he/she wants to allow the visitor's entering.
     * 
     * @param message the visitor's announcement.
     * @throws IOException
     */
    public static void showConfirmationDialog(String message) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Visitante anunciado");
		alert.setHeaderText("Un visitante, identificado con el nombre " + message 
				+ ", ha solicitado ir a su apartamento.");
		alert.setContentText("¿Desea permitir su ingreso?");
		
		ButtonType bttnYes = new ButtonType("Yup");
		ButtonType bttnNo = new ButtonType("Noup");
		
		alert.getButtonTypes().setAll(bttnYes,bttnNo);
		
		Optional<ButtonType> answer = alert.showAndWait();
		
		if(answer.get() == bttnYes) {
			sendAnswer(true);
		} else {
			sendAnswer(false);
		}
	}
    
    /**
     * Sends the user's answer to the gatehouse.
     * 
     * @param answer the user's answer.
     * @throws IOException
     */
    public static void sendAnswer(boolean answer) throws IOException {
    	ApartmentOne.sendAnswer(answer);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ApartmentOne.initializeMainWindow(this);
	}
}
