package hof.net.tcpNetwork;

import hof.net.userMessages.AbstractMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;



public class TcpClient extends Thread {
	public final static String SERVER_HOSTNAME = "localhost";
	public final static int COMM_PORT = 5050; // socket port for client comms

	private Socket socket;
	private AbstractMessage message;

	/** Default constructor. */
	public TcpClient() {
	}

	public void run() {
		try {
			this.socket = new Socket(SERVER_HOSTNAME, COMM_PORT);
			InputStream iStream = this.socket.getInputStream();
			ObjectInputStream oiStream = new ObjectInputStream(iStream);
			this.message = (AbstractMessage) oiStream.readObject();
		} catch (UnknownHostException uhe) {
			System.out.println("Don't know about host: " + SERVER_HOSTNAME);
			System.exit(1);
		} catch (IOException ioe) {
			System.out.println("Couldn't get I/O for the connection to: "
					+ SERVER_HOSTNAME + ":" + COMM_PORT);
			System.exit(1);
		} catch (ClassNotFoundException cne) {
			System.out.println("Wanted class AbstractMessage, but got class " + cne);
		}
		System.out.println("Received AbstractMessage:");
		System.out.println(this.message.toString());
	}
	
	public void setMessage(AbstractMessage message){
		this.message = message;
		this.start();
	}

	
	
}
