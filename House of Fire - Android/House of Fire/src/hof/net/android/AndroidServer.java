package hof.net.android;

import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.GameOverInfoMessage;
import hof.net.userMessages.LevelInfoMessage;
import hof.net.userMessages.ValidationInfoMessage;
import house.of.fire.ControllerActivity;
import house.of.fire.GameOverActivity;
import house.of.fire.LevelActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AndroidServer extends Thread {
	
	public static final int PORT = 4711;
	
	public static int r;
	public static int g;
	public static int b;
	
	private int level;
	private int event;
	private int medal = LevelInfoMessage.NO_MEDAL;
	
	private static final String TAG = AndroidServer.class.getSimpleName();
	
	private static AndroidServer instance;
	private static int instanceCounter = 0;
	
	
	private DatagramSocket socket;
	private DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	private ObjectInputStream ois;
	private AbstractMessage message;
	private boolean isActive;
	private static InetAddress ia;
	
	private Context context;
	
	public static AndroidServer getInstance(Context context, int port){
		
		if (instance == null || instance.getState() == Thread.State.TERMINATED){
			instance = new AndroidServer(context, port);
			instance.start();
		}
		
		instanceCounter++;
		
		Log.i(TAG, "AndroidServer instances: " + instanceCounter);
		return instance;
	}
	

	private AndroidServer(Context context, int port) {
		super();
		this.context = context;
		isActive = true;
		try {
			this.socket = new DatagramSocket(port);
			socket.setSoTimeout(3000);
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
				ia = packet.getAddress();
				
				Log.w("Android Server", "Message received");
				System.out.println(message);
				messageProcessing(message);
				ois.close();
				
			} catch (SocketTimeoutException ste) {
				
			} catch (IOException e) {
				System.out.println("Fehler beim Empfang");
				System.out.println(e.getMessage());
				setActive(false);
			} catch (ClassNotFoundException e) {
				e.getCause();
				e.getMessage();
				e.printStackTrace();
			}
			catch (NullPointerException e) {
				setActive(false);
				e.printStackTrace();
			}

		}
		if(socket != null){
			socket.close();
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
	}
	
	public synchronized void close(){
		instanceCounter--;
		Log.i(TAG, "AndroidServer client instances: " + instanceCounter);
		if (instanceCounter <= 0){

			isActive = false;
			
			socket.disconnect();
			socket.close();
			
			Log.d(TAG, "Socket closed");
			this.notifyAll();
		}
	}


	private void messageProcessing(AbstractMessage message) {
		switch (message.getType()) {
		case ValidationInfo:
			Log.w("Android Server", "Validation received");
			
			ValidationInfoMessage val = (ValidationInfoMessage) message;
			r = (int)(val.getR()*255);
			g = (int)(val.getG()*255);
			b = (int)(val.getB()*255);
			Log.d(TAG, message.toString());
			
			Intent intent = new Intent(context, ControllerActivity.class);
			context.startActivity(intent);

			break;
			
		case LevelInfo:
			
			LevelInfoMessage lev = (LevelInfoMessage) message;
			level = lev.getLevel();
			event = lev.getEventType();
			medal = lev.getMedalType();
			
			Log.d(TAG, message.toString());
			
			if (event == LevelInfoMessage.STARTED){
				Intent gameIntent = new Intent(context, ControllerActivity.class);
				gameIntent.putExtra(ControllerActivity.EXTRA_WATER_LEVEL, ControllerActivity.MAX_WATER_LEVEL);
				context.startActivity(gameIntent);
			}
			else if (event == LevelInfoMessage.FINISHED){
				Intent levelIntent = new Intent(context, LevelActivity.class);
				levelIntent.putExtra(LevelActivity.EXTRA_LEVEL, level);
				levelIntent.putExtra(LevelActivity.EXTRA_MEDAL, medal);
				context.startActivity(levelIntent);
			}

			break;

		case GameOver:
			
//			GameOverInfoMessage gameOver = (GameOverInfoMessage) message;
			
			Intent gameOverIntent = new Intent(context, GameOverActivity.class); 
			gameOverIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			context.startActivity(gameOverIntent);
			break;
			
		case PlayerInfo:
			Log.d(TAG, message.toString());
			Log.d(TAG, "bewirkt nichts");
			break;
			
		default:
			Log.d(TAG, "Kein passender Input");
			break;
		}
	}
}
