package hof.net.android;

import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.ValidationInfoMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AndroidServer extends Thread {
	private DatagramSocket socket;
	private DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	private ObjectInputStream ois;
	private AbstractMessage message;
	private boolean isActive;
	private MessageProcessing processing;

	public AndroidServer(int port) {
		super();
		isActive = true;
		processing = MessageProcessing.getInstance();
		try {
			this.socket = new DatagramSocket(port);
		} catch (IOException e) {
			System.out.println("Server funktioniert nicht!");
			System.out.println(e.getMessage());
		}

	}

	public void run() {
		System.out.println("Server wurde gestartet");
		while (isActive) {
			try {
				socket.receive(packet);
				InetAddress address = packet.getAddress();
				byte[] data = packet.getData();
				ois = new ObjectInputStream(new ByteArrayInputStream(data));
				message = (AbstractMessage) ois.readObject();
				messageProcessing(message);
				ois.close();
				System.out.println(message);
			} catch (IOException e) {
				System.out.println("Fehler beim Empfang");
				System.out.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.getCause();
				e.getMessage();
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

	private void messageProcessing(AbstractMessage e) {
		switch (e.getType()) {
		case ValidationInfo:
			UdpClientThread c = UdpClientThread.getInstance();
			c.sendObject(new ValidationInfoMessage());
			System.out.println("Validatio succeded");
			break;
		default:
			System.out.println("Kein passender Input");
			break;
		}
	}
}
