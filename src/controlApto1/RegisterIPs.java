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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelApto1.ApartmentOne;

public class RegisterIPs {

	private final String DATA_FILE_PATH = "dataApto1/IPs.txt";
	
    @FXML
    private ImageView BTTN_BACK;

    @FXML
    private Button BTTN_SAVE_IPs;

    @FXML
    private TextField TXT_IP_APTO2;

    @FXML
    private TextField TXT_IP_GATEHOUSE;

    @FXML
    void back(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../uiApto1/main-window.fxml"));
    	loader.setController(new MainWindow());
    	Parent root = loader.load();
    	
    	Stage st = new Stage();
    	Scene sc = new Scene(root);
    	st.setScene(sc);
    	st.show();
    	
    	Stage auxSt = (Stage) BTTN_BACK.getScene().getWindow();
    	auxSt.close();
    }

    @FXML
    void saveIPs(ActionEvent event) throws IOException {
    	String ip1 = TXT_IP_APTO2.getText();
    	String ip2 = TXT_IP_GATEHOUSE.getText();
    	
    	if(ip1.isEmpty() || ip2.isEmpty()) {
    		showIncompleteInfoAlert();
    	} else {
    		saveData(ip1, ip2);
    		ApartmentOne.loadIPs();
    		
    		TXT_IP_APTO2.clear();
    		TXT_IP_GATEHOUSE.clear();
    		
    		back(null);
    	}
    }
    
    private void showIncompleteInfoAlert() {
    	Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Informaci�n incompleta");
		alert.setHeaderText("La informaci�n est� incompleta.");
		alert.setContentText("No has ingresado toda la informaci�n necesaria, por lo que "
				+ "la acci�n no pudo ser completada.");
		alert.show();
	}

    @FXML
    void showApto2IPTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Escribe la IP correspondiente al Apto 2");
    	Tooltip.install(TXT_IP_APTO2, t);
    }

    @FXML
    void showGatehouseIPTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Escribe la IP correspondiente a la porter�a");
    	Tooltip.install(TXT_IP_GATEHOUSE, t);
    }

    @FXML
    void showBackTooltip(MouseEvent event) {
    	Tooltip t = new Tooltip("Volver a la ventana principal");
    	Tooltip.install(BTTN_BACK, t);
    }
    
    /**
     * Saves the emergency data in a .txt file in the appropriate folder. 
     * 
     * If there is, yet, a directory with this data, the method deletes it and creates a
     * new file with the new information.
     * 
     * @param ip1 the ApartmentOne's IP address.
     * @param ip2 the ApartmentTwo's IP address.
     */
	private void saveData(String ip1, String ip2) {
		try {
			String allData = "\n" + ip1 + "\n" + ip2;
			
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
