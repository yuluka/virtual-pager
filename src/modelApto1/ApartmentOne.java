package modelApto1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import controlApto1.Chat;
import controlApto1.MainWindow;
import javafx.application.Platform;

public class ApartmentOne {
	
	private static final String VISITOR_MESSAGE_ID = "0";
	private static final String CHAT_MESSAGE_ID = "1";

	private static Chat chat;
	
	private static int numberPortGateHouse = 6665;
	private static int portNumberApto2 = 6666;

	private static DatagramSocket socket;
	private static InetAddress gatehouseIp;
	private static InetAddress apartmentTwoIp;
	private static byte[] buf = new byte[256];
	
	public ApartmentOne() {
		
	}
	
	/**
	 * Initialize the socket where the apartment will receive messages, and the apartment 
	 * 2, and the gatehouse, IP addresses.
	 * 
	 * @throws IOException
	 */
	public static void initializeThings() throws IOException {
		socket = new DatagramSocket(6667);
		
		gatehouseIp = InetAddress.getByName("192.168.18.5");
		apartmentTwoIp = InetAddress.getByName("192.168.18.5");
		
		receiveMessagesThread();
	}
	
	/**
	 * Sends a panic message to the gatehouse.
	 * 
	 * @throws IOException
	 */
	public static void pressPanicButton() throws IOException {
		buf = new byte[256];
		
		String panicMessage = "1¡Ayuda! Hay una emergencia en el apartamento 1.";
		
		buf = panicMessage.getBytes();
		
		DatagramPacket panicPacket = new DatagramPacket(buf, buf.length, gatehouseIp, numberPortGateHouse);
		socket.send(panicPacket);
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
				chat.displayIncomingMessage(message);
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
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, apartmentTwoIp, portNumberApto2);
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
}
