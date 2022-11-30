package controlApto2;

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
import modelApto2.ApartmentTwo;

public class Chat implements Initializable {
	
    @FXML
    private ImageView BTTN_SEND;

    @FXML
    private TextArea TXTA_CHAT;

    @FXML
    private TextField TXT_MESSAGE;

    @FXML
    void checkKey(KeyEvent event) throws IOException {
    	if(event.getCode().equals(KeyCode.ENTER)) {
    		sendMessage(null);
    	}
    }

    @FXML
    void sendMessage(MouseEvent event) throws IOException {
    	String message = "1" + TXT_MESSAGE.getText();
    	
    	displayMessage(message);
    	
    	TXT_MESSAGE.clear();
    }
    
    public void displayMessage(String message) throws IOException {
    	String auxMessage = message.substring(1,message.length());
    	
		TXTA_CHAT.appendText("(Ud): " + auxMessage + "\n");
		
		ApartmentTwo.sendMessage(message);
	}
    
    public void displayIncomingMessage(String message) {
    	TXTA_CHAT.appendText("(Apto 1): " + message + "\n");
	}

    @FXML
    void showSendTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Enviar mensaje");
    	Tooltip.install(BTTN_SEND, t);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ApartmentTwo.initializeChat(this);
	}
}
