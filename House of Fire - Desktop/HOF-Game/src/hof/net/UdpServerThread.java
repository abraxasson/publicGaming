package hof.net;

import hof.net.userMessages.AbstractMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UdpServerThread extends Thread {
	private static final int PORT = 4711;
	
	private DatagramSocket socket;
	private DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	
	private ObjectInputStream ois;
	private AbstractMessage message;
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
			System.out.println("Server funktioniert nicht!");
			System.out.println(e.getMessage());
		}

	}

	public void run() {
		System.out.println("Server wurde gestartet");
		System.out.println("Server wurde gestartet");
		while (isActive) {
			try {
				socket.receive(packet);

				InetAddress address = packet.getAddress();
				byte[] data = packet.getData();
				
				ois = new ObjectInputStream(new ByteArrayInputStream(data));
				
				message =  (AbstractMessage) ois.readObject();
				ois.close();
				
				//list.add(message);
				processing.processMessage(message, address);
				System.out.println(message);
				

			} catch (IOException e) {
				System.out.println("Fehler beim Empfang");
				System.out.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}