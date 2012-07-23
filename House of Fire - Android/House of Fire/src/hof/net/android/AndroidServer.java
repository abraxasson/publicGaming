package hof.net.android;

import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.ValidationInfoMessage;
import house.of.fire.LogInActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

public class AndroidServer extends Thread {
	
	public static int R;
	public static int G;
	public static int B;
	
	
	private DatagramSocket socket;
	private DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	private ObjectInputStream ois;
	private AbstractMessage message;
	private boolean isActive;
	private static InetAddress ia;
	private int port = 4711;
	private Context context;
	

	public AndroidServer(int port) {
		super();
		isActive = true;
		try {
			this.socket = new DatagramSocket(this.port);
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
				AndroidServer.ia = packet.getAddress();
				
				Log.w("Android Server", "Message received");
				
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
				return InetAddress.getByName("192.168.1.105");
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

	
	public void setContext(Context context) {
		this.context = context;
	}

	private void messageProcessing(AbstractMessage message) {
		switch (message.getType()) {
		case ValidationInfo:
			
			Log.w("Android Server", "Validation received");
			LogInActivity logIn = (LogInActivity) context;
			logIn.startGame(context);
			System.out.println(message.toString());
			ValidationInfoMessage val = (ValidationInfoMessage) message;
			R = (int)(val.getR()*255);
			G = (int)(val.getG()*255);
			B = (int)(val.getB()*255);
		
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
