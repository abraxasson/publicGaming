package hof.net;

import hof.net.userMessages.AbstractMessage;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * This Server is required to receive AbstractMessages via the UDP-Network.
 * 
 */
public final class UdpServerThread extends Thread {
	/**
	 * This is the port used to send the Messages.
	 */
	private static final int PORT = 4711;
	/**
	 * The only instance of this class.
	 */
	private static UdpServerThread instance;
	
	/**
	 * Socket to receive packages.
	 */
	private DatagramSocket socket;
	/**
	 * The packet where the received packages will be stored.
	 */
	private DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	
	/**
	 * Indicates if Thread is still active.
	 */
	private boolean isActive;
	
	/**
	 * Used to process the received Messages.
	 */
	private MessageProcessing processing;
	
	

	/**
	 * This method is the only way to get an instance of this class.
	 * If there is no instance of this class or the Thread is already Terminated,
	 * a new instance will be created and the Thread started.
	 * 
	 * @return the instance of this class.
	 */
	public static UdpServerThread getInstance() {
		if (instance == null || instance.getState() == Thread.State.TERMINATED) {
			instance = new UdpServerThread();
			instance.start();
		}		
		return instance;
	}
	
	/**
	 * Constructor
	 * Creates the queue for the packets and the socket.
	 * If the socket creation fails, an alternative port is created.
	 */
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

	/**
	 * The Main Part of the Thread. <br>
	 * The Thread receives Packets as long it is active.
	 * The received Packages are deserialized and added to the MessageQueue in the MessageProcessing.
	 */
	public void run() {
		System.out.println("Server wurde gestartet");
		DataInputStream ois;
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
	
	/**
	 * If port is already in use this method gets another port and tries to create a socket.
	 * If the creation fails this method is called recursively.
	 */
	private void getAlternativePort() {
		try {
			final int port =(int) (Math.random() * 4000) + 4000;
			socket = new DatagramSocket(port);
		} catch (SocketException e1) {
			System.out.println("Ersatzport nicht gefunden.");
			e1.printStackTrace();
			getAlternativePort();
		}
	}

	/**
	 * 
	 * @param isActive - active or inactive
	 */
	public void setActive(final boolean isActive) {
		this.isActive = isActive;
	}
}