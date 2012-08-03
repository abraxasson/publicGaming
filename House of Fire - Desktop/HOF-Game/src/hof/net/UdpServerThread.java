package hof.net;

import hof.net.userMessages.AbstractMessage;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class UdpServerThread extends Thread {
	private static final int PORT = 4711;
	
	private DatagramSocket socket;
	private DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	
	private DataInputStream ois;
	private boolean isActive;
	
	private MessageProcessing processing;
	
	private static UdpServerThread instance;

	public static UdpServerThread getInstance() {
		if (instance == null || instance.getState() == Thread.State.TERMINATED) {
			instance = new UdpServerThread();
			instance.start();
		}		
		return instance;
	}
	
	private UdpServerThread() {
		super();
		isActive = true;
		processing = MessageProcessing.getInstance();
		try {
			this.socket = new DatagramSocket(PORT);
		} catch (IOException e) {
			getAlternativePort();
		}

	}

	public void run() {
		System.out.println("Server wurde gestartet");
		while (isActive) {
			try {
				socket.receive(packet);

				InetAddress address = packet.getAddress();
				byte[] data = packet.getData();
				
				ois = new DataInputStream(new ByteArrayInputStream(data));
				AbstractMessage message = AbstractMessage.deserialize(ois); 
				message.setIa(address);
				
				ois.close();
				
				processing.addMessage(message); 
				
			} catch (NullPointerException e) {
				getAlternativePort();
			} catch (IOException e) {
				System.out.println("Fehler beim Empfang");
				System.out.println(e.getMessage());
			} 
		}
	}	
	
	private void getAlternativePort() {
		try {
			int port =(int) (Math.random() * 8000) + 4000;
			socket = new DatagramSocket(port);
		} catch (SocketException e1) {
			System.out.println("Ersatzport nicht gefunden.");
			e1.printStackTrace();
			getAlternativePort();
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}