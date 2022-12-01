package modelGatehouse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import controlGatehouse.MainWindow;
import javafx.application.Platform;

public class Gatehouse {
	
	private static final String ANSWER_MESSAGE_ID = "0";
	private static final String ALERT_MESSAGE_ID = "1";
	
	public static boolean panic = false;
	
	private static int portNumberApto1 = 6667;
	private static int portNumberApto2 = 6666;
	
	private static DatagramSocket socket;
	private static InetAddress apartmentOneIp;	
	private static InetAddress apartmentTwoIp;
	private static byte[] buf = new byte[256];
	
	public Gatehouse() throws IOException {
		
	}
	
	/**
	 * Initialize the socket where the gatehouse will receive messages and the apartments
	 * IP addresses.
	 * 
	 * @throws IOException
	 */
	public static void initializeThings() throws IOException {
		socket = new DatagramSocket(6665);
		
		apartmentOneIp = InetAddress.getByName("192.168.18.5");
		apartmentTwoIp = InetAddress.getByName("192.168.18.5");
		
		receiveMessagesThread();
	}
	
	/**
	 * Announces a visitor in the specified destination apartment. 
	 * 
	 * @param visitorName the visitor's name.
	 * @param destinationApto the apartment where the visitor wants to go.
	 * @throws IOException
	 */
	public static void announceVisitor(String visitorName, int destinationApto) throws IOException {
		buf = new byte[256];
		
		buf = visitorName.getBytes();
		
		if(destinationApto == 1) {
			DatagramPacket packetToSend = new DatagramPacket(buf, buf.length, apartmentOneIp, portNumberApto1);
			socket.send(packetToSend);
		} else {
			DatagramPacket packetToSend = new DatagramPacket(buf, buf.length, apartmentTwoIp, portNumberApto2);
			socket.send(packetToSend);
		}
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
		
		if(messageID.equals(ANSWER_MESSAGE_ID)) {
			showVisitorAnswer(message);
		} else if(messageID.equals(ALERT_MESSAGE_ID)) {
			showPanicAlert(message);
		}
	}
	
	/**
	 * Calls a method, in the graphical thread, that shows an alert with the apartment's
	 * answer to the visitor's announcement.
	 * 
	 * @param answer the apartment's answer.
	 */
	public static void showVisitorAnswer(String answer) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				MainWindow.showApartmentAnswer(answer);;
			}
		});
	}
	
	/**
	 * Calls a method, in the graphical thread, that shows an alert saying that one 
	 * apartment has pressed the panic button.
	 * 
	 * @param answer the apartment's panic message.
	 */
	public static void showPanicAlert(String panicMessage) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				MainWindow.showPanicAlert(panicMessage);
			}
		});
	}
}
