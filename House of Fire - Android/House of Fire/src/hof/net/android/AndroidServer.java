package hof.net.android;

import house.of.fire.UdpClientThread;
import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.ValidationInfoMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class AndroidServer extends Thread {
	private DatagramSocket socket;
	private DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	private ObjectInputStream ois;
	private AbstractMessage message;
	private boolean isActive;
	private static InetAddress ia;
	private int port;
	private boolean validation = false;

	public AndroidServer(int port) {
		super();
		isActive = true;
		this.port = port;
		try {
			this.socket = new DatagramSocket();
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
				this.ia = packet.getAddress();
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
			catch (NullPointerException e) {
				
				e.printStackTrace();
			}

		}
	}

	public static InetAddress getIa(){
		if(ia != null){
			return ia;
		}
		else {
			try {
				return InetAddress.getByName("192.168.1.107");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		if(!isActive){
			socket.close();
		}
	}

	public boolean getValidation(){
		return this.validation;
	}
	
	private void messageProcessing(AbstractMessage message) {
		switch (message.getType()) {
		case ValidationInfo:
			UdpClientThread c = new UdpClientThread();
			c.setIa(ia);
			c.sendObject(new ValidationInfoMessage());
			System.out.println(message.toString());
			this.validation = true;
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
		case PlayerInfo:
			System.out.println(message.toString());
			System.out.println("bewirkt nichts");
			break;
		default:
			System.out.println("Kein passender Input");
			break;
		}
	}
}
