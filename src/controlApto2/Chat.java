package controlApto2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelApto2.ApartmentTwo;

public class Chat implements Initializable {
	
	@FXML
    private ImageView BTTN_BACK;
	
    @FXML
    private ImageView BTTN_SEND;

    @FXML
    private TextArea TXTA_CHAT;

    @FXML
    private TextField TXT_MESSAGE;

    /**
     * Gets each key pressed when the user is writing in the text field, and checks if the
     * pressed key is the ENTER key. If that is the case, executes the sendMessage method.
     * 
     * @param event the key pressed.
     * @throws IOException
     */
    @FXML
    void checkKey(KeyEvent event) throws IOException {
    	if(event.getCode().equals(KeyCode.ENTER)) {
    		sendMessage(null);
    	}
    }

    /**
     * Gets the text in the text field and sends it to the apartment 1, when the mouse is 
     * clicked over the send message button.
     * 
     * @param event the mouse click.
     * @throws IOException
     */
    @FXML
    void sendMessage(MouseEvent event) throws IOException {
    	String message = "1" + TXT_MESSAGE.getText();
    	
    	displayMessage(message);
    	
    	TXT_MESSAGE.clear();
    }
    
    /**
     * Displays the last sent message in the text area.
     * 
     * @param message the message sent.
     * @throws IOException
     */
    public void displayMessage(String message) throws IOException {
    	String auxMessage = message.substring(1,message.length());
    	
		TXTA_CHAT.appendText("(Ud): " + auxMessage + "\n");
		
		ApartmentTwo.sendMessage(message);
	}
    
    /**
     * Displays an incoming message, from the apartment 1, in the text area.
     * 
     * @param message the incoming message.
     */
    public void displayIncomingMessage(String message) {
    	TXTA_CHAT.appendText("(Apto 1): " + message + "\n");
	}
    
    /**
     * Opens the main window again and closes the chat window, when the user clicks over
     * the back button.
     * 
     * @param event the mouse click.
     */
    @FXML
    void back(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiApto2/main-window.fxml"));
    	loader.setController(new MainWindow());
    	Parent root = loader.load();
    	
    	Stage st = new Stage();
    	Scene sc = new Scene(root);
    	st.setScene(sc);
    	st.show();
    	
    	ApartmentTwo.initializeChat(null);
    	
    	Stage auxSt = (Stage) BTTN_BACK.getScene().getWindow();
    	auxSt.close();
    }

    /**
     * Shows a label, with a description of what the button does, when the mouse is moved
     * over the send message button.
     * 
     * @param event the mouse moving over the button.
     */
    @FXML
    void showSendTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Enviar mensaje");
    	Tooltip.install(BTTN_SEND, t);
    }
    
    /**
     * Shows a label, with a description of what the button does, when the mouse is moved
     * over the back button.
     * 
     * @param event the mouse moving over the button.
     */
    @FXML
    void showBackTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Volver a la ventana principal");
    	Tooltip.install(BTTN_BACK, t);
    }
    
    /**
     * Initializes a chat object, in the ApartmentTwo class, with the Chat in use.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ApartmentTwo.initializeChat(this);
	}
}
