package modelApto2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import controlApto2.MainWindow;
import javafx.application.Platform;

public class ApartmentTwo {
	
	private static int numberPortGateHouse = 6665;

	private static DatagramSocket socket;
	private static InetAddress ip;
	private static byte[] buf = new byte[256];
	
	public ApartmentTwo() {
		
	}
	
	public static void initializeThings() throws IOException {
		socket = new DatagramSocket(6666);
		
		ip = InetAddress.getByName("192.168.18.5");
		
		receiveMessagesThread();
	}
	
	public static void pressPanicButton() throws IOException {
		buf = new byte[256];
		
		String panicMessage = "1¡Ayuda! Hay una emergencia en el apartamento 2.";
		
		buf = panicMessage.getBytes();
		
		DatagramPacket panicPacket = new DatagramPacket(buf, buf.length, ip, numberPortGateHouse);
		socket.send(panicPacket);
	}
	
	public static void receiveMessagesThread() {
		new Thread(() -> {
			try {
				while(true) {
					buf = new byte[256];
					
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);
					
					String message = new String(packet.getData(), 0, packet.getLength());
					
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public static void sendAnswer(boolean answer) throws IOException {
		buf = new byte[256];
		
		String answerStr = "0";
		
		if(answer) {
			answerStr += "Déjelo entrar, por favor.";
		} else {
			answerStr += "No lo deje entrar, por favor.";
		}
		
		buf = answerStr.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, numberPortGateHouse);
		socket.send(packet);
	}
}
