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
	private static InetAddress ip;
	private static byte[] buf = new byte[256];
	
	public Gatehouse() throws IOException {
		
	}
	
	public static void initializeThings() throws IOException {
		socket = new DatagramSocket(6665);
		
		ip = InetAddress.getByName("192.168.18.5");
		
		receiveMessagesThread();
	}
	
	public static void announceVisitor(String visitorName, int destinatioApto) throws IOException {
		buf = new byte[256];
		
		buf = visitorName.getBytes();
		
		if(destinatioApto == 1) {
			DatagramPacket packetToSend = new DatagramPacket(buf, buf.length, ip, portNumberApto1);
			socket.send(packetToSend);
		} else {
			DatagramPacket packetToSend = new DatagramPacket(buf, buf.length, ip, portNumberApto2);
			socket.send(packetToSend);
		}
	}
	
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
	
	public static void evaluateMessage(String message) {
		String messageID = message.substring(0,1);
		message = message.substring(1,message.length());
		
		if(messageID.equals(ANSWER_MESSAGE_ID)) {
			showVisitorAnswer(message);
		} else if(messageID.equals(ALERT_MESSAGE_ID)) {
			showPanicAlert(message);
		}
	}
	
	public static void showVisitorAnswer(String answer) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				MainWindow.showApartmentAnswer(answer);;
			}
		});
	}
	
	public static void showPanicAlert(String panicMessage) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				MainWindow.showPanicAlert(panicMessage);
			}
		});
	}
}
