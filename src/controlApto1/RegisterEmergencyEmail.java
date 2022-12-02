package controlApto1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelApto1.ApartmentOne;

public class RegisterEmergencyEmail {
	
	private final String DATA_FILE_PATH = "dataApto1/Emergency data.txt";
	
    @FXML
    private Button BTTN_SAVE_EMAIL;

    @FXML
    private ImageView BTTN_BACK;
    
    @FXML
    private TextArea TXTA_EMERGENCY_MESSAGE;

    @FXML
    private TextField TXT_EMERGENCY_EMAIL;

    @FXML
    void saveEmergencyEmail(ActionEvent event) throws IOException {
    	String emergencyEmail = TXT_EMERGENCY_EMAIL.getText();
    	String emergencyMessage = TXTA_EMERGENCY_MESSAGE.getText();
    	
    	if(emergencyEmail.isEmpty() || emergencyMessage.isEmpty()) {
    		showInsuficientDataAlert();
    	} else {
    		saveData(emergencyEmail, emergencyMessage);
    		back(null);
    	}
    }
    
    /**
     * Opens the main window again and closes the register emergency email window, when 
     * the user clicks over the back button.
     * 
     * @param event the mouse click.
     */
    @FXML
    void back(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiApto1/main-window.fxml"));
    	loader.setController(new MainWindow());
    	Parent root = loader.load();
    	
    	Stage st = new Stage();
    	Scene sc = new Scene(root);
    	st.setScene(sc);
    	st.show();
    	
    	ApartmentOne.initializeChat(null);
    	
    	Stage auxSt = (Stage) BTTN_BACK.getScene().getWindow();
    	auxSt.close();
    }
    
    /**
     * Shows an alert that says that the entered information is not complete.
     */
    private void showInsuficientDataAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Información incompleta");
		alert.setHeaderText("La información está incompleta.");
		alert.setContentText("No has ingresado toda la información necesaria, por lo que "
				+ "la acción no pudo ser completada.");
		alert.show();
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
     * Shows a label, with a description of what is text field purpose, when the mouse is 
     * moved over the text field to write the emergency email.
     * 
     * @param event the mouse moving over the text field.
     */
    @FXML
    void showEmergencyEmailTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Escribe el correo al que deseas que sea enviado un "
    			+ "mensaje de emergencia en caso de tener que presionar el botón de "
    			+ "pánico.");
    	Tooltip.install(TXT_EMERGENCY_EMAIL, t);
    }

    @FXML
    void showEmergencyMessageTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Escribe el mensaje que deseas que sea enviado en caso de "
    			+ "tener que presionar el botón de pánico.");
    	Tooltip.install(TXTA_EMERGENCY_MESSAGE, t);
    }

    /**
     * Saves the emergency data in a .txt file in the appropriate folder. 
     * 
     * If there is, yet, a directory with this data, the method deletes it and creates a
     * new file with the new information.
     * 
     * @param emergencyEmail the email where the alert will be sent in an emergency case.
     * @param emergencyMessage the message that will be sent in an emergency case.
     */
	private void saveData(String emergencyEmail, String emergencyMessage) {
		try {
			String allData = "\n" + emergencyEmail + "\n" + emergencyMessage;
			
			File file = new File(DATA_FILE_PATH);
			
			if(file.exists()) {
				file.delete();
			} else if(!file.exists()) {
				file.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(allData);
			
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
