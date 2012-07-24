package house.of.fire;

import hof.net.userMessages.AbstractMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class UdpClientThread extends Thread {
	private InetAddress ia;
	private static int port = 4711;
	private DatagramPacket packet;
	private DatagramSocket toSocket;
	private boolean isActive = true;
	private LinkedList<DatagramPacket> list;

	public UdpClientThread() {
		list = new LinkedList<DatagramPacket>();

		try {
			ia = InetAddress.getByName("192.168.1.105");
			toSocket = new DatagramSocket();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		System.out.println("Thread gestartet -" + isActive);

		while (isActive || !list.isEmpty()) {
			try {
				while (!list.isEmpty()) {
					toSocket.send(list.removeFirst());
					System.out.println("Paket wurde gesendet");
				}
				System.out.println("Thread schläft!");
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

	public synchronized void setActive(boolean active) {
		this.isActive = active;
		try {
			notify();
		} catch (java.lang.IllegalMonitorStateException e) {
			System.out.println(e.getMessage());
		}
	}

	public synchronized void sendObject(AbstractMessage e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream stream = new ObjectOutputStream(baos);
			stream.writeObject(e);
			byte[] data = baos.toByteArray();
			packet = new DatagramPacket(data, data.length, ia, port);
			list.add(packet);
			stream.close();
			System.out.println("packet geladen");
			notify();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void setIa(InetAddress ia) {
		this.ia = ia;
	}

}
