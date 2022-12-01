package controlApto1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import modelApto1.ApartmentOne;

public class Chat implements Initializable {

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
     * Gets the text in the text field and sends it to the apartment 2, when the mouse is 
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
		
		ApartmentOne.sendMessage(message);
	}
    
    /**
     * Displays an incoming message, from the apartment 2, in the text area.
     * 
     * @param message the incoming message.
     */
    public void displayIncomingMessage(String message) {
    	TXTA_CHAT.appendText("(Apto 2): " + message + "\n");
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
     * Initializes a chat object, in the ApartmentOne class, with the Chat in use.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ApartmentOne.initializeChat(this);
	}
}
