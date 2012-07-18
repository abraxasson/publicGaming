package hof.net.android;

import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.ValidationInfoMessage;
import house.of.fire.UdpClientThread;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AndroidServer extends Thread {
	private static final int PORT = 4711;
	
	private DatagramSocket socket;
	private DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	private ObjectInputStream ois;
	private AbstractMessage message;
	private boolean isActive;

	public AndroidServer() {
		super();
		isActive = true;
		try {
			this.socket = new DatagramSocket(PORT);
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

	private void messageProcessing(AbstractMessage message) {
		switch (message.getType()) {
		case ValidationInfo:
			UdpClientThread c = UdpClientThread.getInstance();
			c.sendObject(new ValidationInfoMessage());
			System.out.println(message.toString());
			break;
		case LevelFinished:
			System.out.println(message.toString());
			break;
		case Achievement:
			System.out.println(message.toString());
			break;
		case GameFinished:
			System.out.println(message.toString());
			break;
		default:
			System.out.println("Kein passender Input");
			break;
		}
	}
}
