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

public class UdpClientThread extends Thread {
	private static int port = 4711;
	private DatagramPacket packet;
	private DatagramSocket toSocket;
	private boolean isActive = true;
	private LinkedList<DatagramPacket> list;
	private static UdpClientThread instance;

	private UdpClientThread() {
		list = new LinkedList<DatagramPacket>();

		try {
			toSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Socket nicht gefunden");
		}
	}

	public static UdpClientThread getInstance() {
		if (instance == null || instance.getState() == Thread.State.TERMINATED) {
			instance = new UdpClientThread();
			instance.start();
		}
		return instance;
	}

	public void run() {
//		System.out.println("Thread gestartet -" + isActive);

		while (isActive || !list.isEmpty()) {
			try {
				while (!list.isEmpty()) {
					toSocket.send(list.removeFirst());
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

	public synchronized void setActive(boolean active) {
		this.isActive = active;
		notify();
	}

	public synchronized void sendMessage(AbstractMessage e, InetAddress ineta) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			DataOutputStream stream = new DataOutputStream(baos);
			e.serialize(stream);
			byte[] data = baos.toByteArray();
			packet = new DatagramPacket(data, data.length, ineta, port);
			list.add(packet);
			notify();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}