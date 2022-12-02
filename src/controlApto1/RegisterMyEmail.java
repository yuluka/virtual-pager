package controlApto1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelApto1.ApartmentOne;

public class RegisterMyEmail implements Initializable {

	private final String DATA_FILE_PATH = "dataApto1/My email data.txt";
	
    @FXML
    private ImageView BTTN_BACK;

    @FXML
    private Button BTTN_SEE_INFO;
    
    @FXML
    private Button BTTN_SAVE_EMAIL;

    @FXML
    private TextField TXT_MY_EMAIL;

    @FXML
    private TextField TXT_PASSWORD;

    /**
     * Opens the main window again and closes the register my email window, when 
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

    @FXML
    void saveMyEmail(ActionEvent event) throws IOException {
    	String myEmail = TXT_MY_EMAIL.getText();
    	String password = TXT_PASSWORD.getText();
    	
    	if(myEmail.isEmpty() || password.isEmpty()) {
    		showInsuficientDataAlert();
    	} else {
    		checkCredentials(myEmail, password);
    	}
    }
    
    /**
     * Shows an alert that says that the entered information is not complete.
     */
    private void showInsuficientDataAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Informaci�n incompleta");
		alert.setHeaderText("La informaci�n est� incompleta.");
		alert.setContentText("No has ingresado toda la informaci�n necesaria, por lo que "
				+ "la acci�n no pudo ser completada.");
		alert.show();
	}
    
    private void showWrongCredentialsAlert() {
    	Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Credenciales err�neas");
		alert.setHeaderText("Las credenciales son incorrectas.");
		alert.setContentText("Las credenciales de correo que has ingresado son err�neas "
				+ "pues no permiten el inicio de la sesi�n.");
		alert.show();
	}
    
    private void showCorrectCredentialsAlert() {
    	Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Credenciales correctas");
		alert.setHeaderText("Las credenciales son correctas.");
		alert.setContentText("Las credenciales de correo que has ingresado son correctas "
				+ "pues permiten el inicio de la sesi�n.");
		alert.show();
	}
    
    @SuppressWarnings("unused")
	private void showWaitingAlert() {
    	Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Iniciando sesi�n");
		alert.setHeaderText("Tratando de iniciar sesi�n. Por favor espere");
		alert.setContentText("Espere un momento, por favor. Estamos tratando de iniciar "
				+ "la sesi�n con las credenciales proporcionadas.");
		alert.show();
	}
    
    @FXML
    void seeInfo(ActionEvent event) {
    	showInformativeAlert();
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
     * Shows a label, with a description of what is text field purpose, when the mouse is moved
     * over the text field to write my email.
     * 
     * @param event the mouse moving over the text field.
     */
    @FXML
    void showMyEmailTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Escribe el correo desde el que deseas que sea enviado un "
    			+ "mensaje de emergencia en caso de tener que presionar el bot�n de "
    			+ "p�nico.");
    	Tooltip.install(TXT_MY_EMAIL, t);
    }

    /**
     * Shows a label, with a description of what is text field purpose, when the mouse is moved
     * over the text field to write the generated password.
     * 
     * @param event the mouse moving over the text field.
     */
    @FXML
    void showMyPasswordTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Escribe la contrase�a generada, para el correo ingresado, "
    			+ "despu�s de haber seguido los pasos de la URL especificada.");
    	Tooltip.install(TXT_PASSWORD, t);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showInformativeAlert();
	}
	
	/**
	 * Shows an alert informing the steps to follow to use an email correctly.
	 */
	private void showInformativeAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Informaci�n importante");
		alert.setHeaderText("�Informaci�n importante!");
		alert.setContentText("En esta ventana podr�s registrar un correo personal desde "
				+ "el que se enviar� la alerta en caso de tener que presionar el bot�n de "
				+ "p�nico. Por ahora, solo podr�s resgitrar emails de gmail y, para que "
				+ "este funcione, tendr�s que seguir los pasos especificados en el "
				+ "siguiente enlace:\n"
				+ "https://stackoverflow.com/questions/26594097/javamail-exception-javax-mail-authenticationfailedexception-534-5-7-9-applicatio/72592946#72592946\n"
				+ "El prop�sito de esto es que le permitas, a esta aplicaci�n, iniciar "
				+ "sesi�n en la cuenta que digites.\n"
				+ "En el campo 'Mi correo' debes escribir tu correo gmail de manera "
				+ "normal.\n"
				+ "En el campo 'Contrase�a generada' debes escribir la contrase�a que "
				+ "haya sido generada despu�s de seguir los pasos de la URL anterior.\n"
				+ "\nEn caso de querer volver a ver este mensaje, presiona el bot�n "
				+ "'Ver informaci�n'.");
		
		alert.show();
	}
	
	/**
	 * Checks that the entered credentials are correct by trying to connect with the 
	 * account.
	 * 
	 * @param myEmail the account's email.
	 * @param password the generated password.
	 * @throws IOException 
	 */
	private void checkCredentials(String myEmail, String password) throws IOException {
		Properties properties = new Properties();
		
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", 25);
		properties.put("mail.smtp.user", "username");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug","true");
		properties.put("mail.smtp.EnableSSL.enable", "true");
		
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
 
		Session session = Session.getDefaultInstance(properties);
		
		try {
			Transport t = session.getTransport("smtp");

			t.connect(myEmail, password);
			
			saveData(myEmail, password);
			showCorrectCredentialsAlert();
			back(null);
		} catch (MessagingException e) {
			showWrongCredentialsAlert();
		} finally {
			TXT_MY_EMAIL.clear();
			TXT_PASSWORD.clear();
		}
	}
	
	/**
     * Saves the data in a .txt file in the appropriate folder. 
     * 
     * If there is, yet, a directory with this data, the method deletes it and creates a
     * new file with the new information.
     * 
     * @param emergencyEmail the email where the alert will be sent in an emergency case.
     * @param emergencyMessage the message that will be sent in an emergency case.
     */
	private void saveData(String myEmail, String password) {
		try {
			String allData = "\n" + myEmail + "\n" + password;
			
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
