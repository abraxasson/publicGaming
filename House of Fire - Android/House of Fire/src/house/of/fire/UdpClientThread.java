package house.of.fire;

import hof.net.userMessages.AbstractMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import android.util.Log;

public class UdpClientThread extends Thread {

	private static final String TAG = UdpClientThread.class.getSimpleName();
	
	private InetAddress ia;
	private static int port = 4711;
	private DatagramSocket toSocket;
	private boolean isActive = true;
	private LinkedList<DatagramPacket> list;


	public UdpClientThread() {
		list = new LinkedList<DatagramPacket>();

		try {
			ia = InetAddress.getByName("192.168.1.106");
			toSocket = new DatagramSocket();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Log.d(TAG, "Thread gestartet -" + isActive);

		while (isActive || !list.isEmpty()) {
			try {
				synchronized (list) {
					while (!list.isEmpty()) {
						toSocket.send(list.removeFirst());
						//Log.d(TAG, "Paket wurde gesendet");
					}

					if (isActive){
						//Log.d(TAG, "Thread schläft!");
						list.wait();
						//Log.d(TAG, "Thread wacht auf! - List size: " + list.size());
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				this.isActive = false;
			} catch (InterruptedException e) {
				Log.d(TAG, "Thread interrupted");
				//e.printStackTrace();
			}
		}

		if (toSocket != null){
			this.toSocket.close();
		}
		Log.d(TAG, "Thread finished");
	}

	public void setActive(boolean active) {
		this.isActive = active;

		try {
			//Log.d(TAG, "Thread set to: " + (active ? "active" : "inactive"));
			synchronized (list) {
				list.notifyAll();
			}
		} catch (java.lang.IllegalMonitorStateException e) {
			Log.d(TAG, e.getMessage());
		}
	}

	public void sendObject(AbstractMessage e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			synchronized (list) {
				DataOutputStream stream = new DataOutputStream(baos);
				e.serialize(stream);
				byte[] data = baos.toByteArray();
				DatagramPacket packet = new DatagramPacket(data, data.length, ia, port);
				list.add(packet);
				
				stream.close();
				//Log.d(TAG, "packet geladen: " + e.toString());
				
				list.notifyAll();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void setIa(InetAddress ia) {
		this.ia = ia;
	}

}
