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
    private ImageView BTTN_CHANGE_IPS;
	
    @FXML
    private ImageView BTTN_CHAT;

    @FXML
    private ImageView BTTN_PANIC;
    
    @FXML
    private ImageView BTTN_REGISTER_EMERGENCY_EMAIL;

    @FXML
    private ImageView BTTN_REGISTER_MY_EMAIL;

    /**
     * Opens a window where the apartment can changes the IP addresses of the other 
     * apartment and the gatehouse, when the user clicks over the change IP button.
     * 
     * @param event the mouse click.
     * @throws IOException
     */
    @FXML
    void changeIP(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiApto1/register-ips.fxml"));
    	loader.setController(new RegisterIPs());
    	Parent root = loader.load();
    	
    	Stage stage = new Stage();
    	Scene sc = new Scene(root);
    	stage.setScene(sc);
    	stage.show();
    	
    	Stage auxSt = (Stage) BTTN_CHANGE_IPS.getScene().getWindow();
    	auxSt.close();
    }
    
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
     * Opens a window where the apartment register an emergency email, when the user 
     * clicks over the register emergency email button.
     * 
     * @param event the mouse click.
     * @throws IOException
     */
    @FXML
    void registerEmergencyEmail(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiApto1/register-emergency-email.fxml"));
    	loader.setController(new RegisterEmergencyEmail());
    	Parent root = loader.load();
    	
    	Stage st = new Stage();
    	Scene sc = new Scene(root);
    	st.setScene(sc);
    	st.show();
    	
    	Stage auxSt = (Stage) BTTN_REGISTER_EMERGENCY_EMAIL.getScene().getWindow();
    	auxSt.close();
    }

    /**
     * Opens a window where the apartment register his/her email, when the user 
     * clicks over the register my email button.
     * 
     * @param event the mouse click.
     * @throws IOException
     */
    @FXML
    void registerMyEmail(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiApto1/register-my-email.fxml"));
    	loader.setController(new RegisterMyEmail());
    	
    	Parent root = loader.load();
    	
    	Stage st = new Stage();
    	Scene sc = new Scene(root);
    	st.setScene(sc);
    	st.show();
    	
    	Stage auxSt = (Stage) BTTN_REGISTER_MY_EMAIL.getScene().getWindow();
    	auxSt.close();
    }

    /**
     * Shows a label, with a description of what the button does, when the mouse is moved
     * over the change IP button.
     * 
     * @param event the mouse moving over the button.
     */
    @FXML
    void showChangeIPTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Registrar/Cambiar direcciones IP");
    	Tooltip.install(BTTN_CHANGE_IPS, t);
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
     * Shows a label, with a description of what the button does, when the mouse is moved
     * over the register emergency email button.
     * 
     * @param event the mouse moving over the button.
     */
    @FXML
    void showEmergencyEmailTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Registrar un correo de emergencia");
    	Tooltip.install(BTTN_REGISTER_EMERGENCY_EMAIL, t);
    }

    /**
     * Shows a label, with a description of what the button does, when the mouse is moved
     * over the register my email button.
     * 
     * @param event the mouse moving over the button.
     */
    @FXML
    void showMyEmailTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Registrar mi correo");
    	Tooltip.install(BTTN_REGISTER_MY_EMAIL, t);
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
     * Shows an alert saying that the required information to send the panic mail haven't
     * been entered.
     */
    public static void showNoEmailDataAlert() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	
    	alert.setTitle("Información de envío de correo incompleta");
    	alert.setHeaderText("Información de envío de correo de emergencia incompleta.");
    	alert.setContentText("La información necesaria para enviar el correo de "
    			+ "emergencia no ha sido ingresada completamente, por lo que no se puede "
    			+ "hacer el envío del correo.");
    	alert.show();
	}
    
    /**
     * Shows an alert saying that the IP addresses have not been introduced yet.
     */
    public static void showNoIPsAlert() {
    	Alert alert = new Alert(AlertType.INFORMATION);
		
    	alert.setTitle("Sin información de IPs");
		alert.setHeaderText("No hay información de IPs.");
		alert.setContentText("No se han asignado las direcciones IP del Apto 2 y de la "
				+ "portería, o las direcciones proporcionadas no son válidas. "
				+ "Por favor hacerlo cuanto antes.");
		alert.show();
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

    /**
     * Initializes the things inside the apartment.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ApartmentOne.initializeMainWindow(this);
		ApartmentOne.loadIPs();
	}
}
