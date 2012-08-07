package hof.net;

import hof.net.userMessages.AbstractMessage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

/**
 * This Class is used to send AbstractMessages via an UDP-Network. <br>
 * It is designed as an Singleton and normally it runs until somebody closes the game. <p>
 * 
 * To use this class you have to call the prepareMessage-Method and then the Thread will automatically send the Message to the given InetAddress.
 */
public class UdpClientThread extends Thread {
	/**
	 * This is the port used to send the Messages.
	 */
	private final int port = 4711;
	/**
	 * The only instance of this class.
	 */
	private static UdpClientThread instance;
	/**
	 * The packet which will be sent.
	 */
	private DatagramPacket packet;
	/**
	 * Socket to send packages.
	 */
	private DatagramSocket toSocket;
	/**
	 * Indicates if Thread is still active.
	 */
	private boolean isActive = true;
	/**
	 * The Queue for the packages to be sent
	 */
	private LinkedList<DatagramPacket> queue;
	
	/**
	 * Constructor
	 * Creates the queue for the packets and the socket.
	 */
	private UdpClientThread() {
		queue = new LinkedList<DatagramPacket>();

		try {
			toSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Socket nicht gefunden");
		}
	}

	/**
	 * This method is the only way to get an instance of this class.
	 * If there is no instance of this class or the Thread is already Terminated,
	 * a new instance will be created and the Thread started.
	 * 
	 * @return the instance of this class.
	 */
	public static UdpClientThread getInstance() {
		if (instance == null || instance.getState() == Thread.State.TERMINATED) {
			instance = new UdpClientThread();
			instance.start();
		}
		return instance;
	}
	
	/**
	 * Main part of the Thread. <br>
	 * The Thread sends Messages from the Queue, as long it is isActive.
	 */
	public void run() {
//		System.out.println("Thread gestartet -" + isActive);

		while (isActive || !queue.isEmpty()) {
			try {
				while (!queue.isEmpty()) {
					toSocket.send(queue.removeFirst());
//					System.out.println("Paket wurde gesendet");
				}
//				System.out.println("Thread schläft!");
				synchronized (this) {
					this.wait();
				}
			} catch (IOException e) {
				e.printStackTrace();
				this.isActive = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.toSocket.close();
		System.out.println("Thread finished");
	}

	/**
	 * Sets if the thread is active or not. Then the Thread will be notified.
	 * @param isActive - active or inactive
	 */
	public synchronized void setActive(boolean isActive) {
		this.isActive = isActive;
		notify();
	}

	/**
	 * Prepares a new Message to be send. <br>
	 * The AbstractMessage gets serialized and is loaded into a DatagrammPacket.
	 * This packet gets added to the Queue.
	 * @param message the AbstractMessage to send
	 * @param inetaddress the InetAddress to where the message will be send
	 */
	public synchronized void prepareMessage(AbstractMessage message, InetAddress inetaddress) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			DataOutputStream stream = new DataOutputStream(baos);
			message.serialize(stream);
			byte[] data = baos.toByteArray();
			packet = new DatagramPacket(data, data.length, inetaddress, port);
			queue.add(packet);
			notify();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}