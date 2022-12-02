package modelApto2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import controlApto2.Chat;
import controlApto2.MainWindow;
import javafx.application.Platform;

public class ApartmentTwo {
	
	private final static String IPs_DATA_PATH = "dataApto2/IPs.txt";
	
	private static final String EMERGENCY_DATA_PATH = "dataApto2/Emergency data.txt";
	private static final String MY_EMAIL_DATA_PATH = "dataApto2/My email data.txt";
	
	private static final String VISITOR_MESSAGE_ID = "0";
	private static final String CHAT_MESSAGE_ID = "1";

	private static Chat chat;
	private static MainWindow mainWindow;
	
	private static String myEmail;
	private static String emailPassword;
	private static String emergencyEmail;
	private static String emergencyMessage;
	
	private static int numberPortGateHouse = 6665;
	private static int portNumberApto1 = 6667;

	private static DatagramSocket socket;
	private static InetAddress gatehouseIp;
	private static InetAddress apartmentOneIp;
	private static byte[] buf = new byte[256];
	
	public ApartmentTwo() {
		
	}
	
	/**
	 * Initialize the socket where the apartment will receive messages, and the apartment 
	 * 1, and the gatehouse, IP addresses.
	 * 
	 * @throws IOException
	 */
	public static void initializeThings() throws IOException {
		socket = new DatagramSocket(6666);
		
		myEmail = null;
		emailPassword = null;
		emergencyEmail = null;
		emergencyMessage = null;
		
		receiveMessagesThread();
	}
	
	/**
	 * Initializes the IP addresses of the other apartment and of the gatehouse with
	 * the values stored in the data.
	 */
	public static void loadIPs() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(IPs_DATA_PATH));
			
			String line = br.readLine();
			
			if(line != null) {
				apartmentOneIp = InetAddress.getByName(br.readLine());
				gatehouseIp = InetAddress.getByName(br.readLine());
			} else {
				MainWindow.showNoIPsAlert();
			}
			
			br.close();
			
		} catch (Exception e) {
			MainWindow.showNoIPsAlert();
		}
		
	}
	
	/**
	 * Sends a panic message to the gatehouse.
	 * 
	 * @throws IOException
	 */
	public static void pressPanicButton() throws IOException {
		buf = new byte[256];
		
		String panicMessage = "1¡Ayuda! Hay una emergencia en el apartamento 2.";
		
		buf = panicMessage.getBytes();
		
		DatagramPacket panicPacket = new DatagramPacket(buf, buf.length, gatehouseIp, numberPortGateHouse);
		socket.send(panicPacket);
		
		if(loadEmailData()) {
			sendEmergencyEmailThread();
		}
	}
	
	/**
	 * Uses an express Thread to send an email, to the specified email address, informing
	 * that there is an emergency in the apartment.
	 */
	private static void sendEmergencyEmailThread() {
		new Thread(() -> {
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
			
			try{
				MimeMessage message = new MimeMessage(session);

				message.setFrom(new InternetAddress(myEmail));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(emergencyEmail));
				message.setSubject("¡Emergencia!");
				message.setText(emergencyMessage);
				Transport t = session.getTransport("smtp");
				
				t.connect(myEmail, emailPassword);
				
				t.sendMessage(message, message.getAllRecipients());
			}catch (MessagingException me){
				System.out.println(me.getMessage());
				me.printStackTrace();
				return;
			}
		}).start();
	}
	
	/**
	 * Waits, constantly, an incoming message that saves in one string. When it catches a
	 * message, calls a method that evaluates the type of the incoming message.
	 */
	public static void receiveMessagesThread() {
		new Thread(() -> {
			try {
				while(true) {
					buf = new byte[256];
					
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);
					
					String message = new String(packet.getData(), 0, packet.getLength());
					
					evaluateMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	/**
	 * Evaluates the type of the incoming message and decides what to do with it.
	 * 
	 * @param message the incoming message.
	 */
	public static void evaluateMessage(String message) {
		String messageID = message.substring(0,1);
		message = message.substring(1,message.length());
		
		if(messageID.equals(VISITOR_MESSAGE_ID)) {
			showConfirmationDialog(message);
		} else if(messageID.equals(CHAT_MESSAGE_ID)) {
			displayIncomingChatMessage(message);
		}
	}
	
	/**
	 * Calls a method, in the graphical thread, that shows an alert asking the apartment
	 * to decide if the announced visitor, whose information is in the incoming message,
	 * can enter or not.
	 * 
	 * @param message the message whit the visitor's information.
	 */
	public static void showConfirmationDialog(String message) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					MainWindow.showConfirmationDialog(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Calls a method, in the graphical thread, that displays the incoming message.
	 * 
	 * @param message the incoming message.
	 */
	public static void displayIncomingChatMessage(String message) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					if(chat == null) {
						mainWindow.beginChat(null);
						chat.displayIncomingMessage(message);
					} else {
						chat.displayIncomingMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Sends a message, to the gatehouse, with the decision of the visitor's announcement.
	 * 
	 * @param answer the apartment's answer of the visitor's announcement.
	 * @throws IOException
	 */
	public static void sendAnswer(boolean answer) throws IOException {
		buf = new byte[256];
		
		String answerStr = "0";
		
		if(answer) {
			answerStr += "Déjelo entrar, por favor.";
		} else {
			answerStr += "No lo deje entrar, por favor.";
		}
		
		buf = answerStr.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, gatehouseIp, numberPortGateHouse);
		socket.send(packet);
	}
	
	/**
	 * Sends a message to the apartment 2.
	 * 
	 * @param message the message to send.
	 * @throws IOException
	 */
	public static void sendMessage(String message) throws IOException {
		buf = new byte[256];
		
		buf = message.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, apartmentOneIp, portNumberApto1);
		socket.send(packet);
	}
	
	/**
	 * Initializes a Chat object with the value received.
	 * 
	 * @param chatSent the value to initialize the Chat object.
	 */
	public static void initializeChat(Chat chatSent) {
		chat = chatSent;
	}
	
	/**
	 * Initializes a MainWindow object with the value received.
	 * 
	 * @param mainWindowSent the value to initialize the MainWindow object.
	 */
	public static void initializeMainWindow(MainWindow mainWindowSent) {
		mainWindow = mainWindowSent;
	}
	
	/**
	 * Loads the required information to send the alert email.
	 */
	private static boolean loadEmailData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(MY_EMAIL_DATA_PATH));
			
			String line = br.readLine();
			
			if(line != null) {
				myEmail = br.readLine();
				emailPassword = br.readLine();
				
				br.close();
			} else {
				MainWindow.showNoEmailDataAlert();
				
				br.close();
				
				return false;
			}
			
			BufferedReader br1 = new BufferedReader(new FileReader(EMERGENCY_DATA_PATH));
			
			line = br1.readLine();
			
			if(line != null) {
				emergencyEmail = br1.readLine();
				emergencyMessage = br1.readLine();
				
				br1.close();
			} else {
				MainWindow.showNoEmailDataAlert();
				
				br1.close();
				
				return false;
			}			
			
			return true;
		} catch (FileNotFoundException e) {
			MainWindow.showNoEmailDataAlert();
			
			return false;
		} catch (IOException e) {
			MainWindow.showNoEmailDataAlert();
			
			return false;
		}
	}
}
