package hof.net.tcpNetwork;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class works in conjunction with TcpClient.java and TcpPayload.java
 * 
 * This server test class opens a socket on localhost and waits for a client to
 * connect. When a client connects, this server serializes an instance of
 * TcpPayload and sends it to the client.
 */

public class TcpServer extends Thread {
	public final static int COMM_PORT = 5050; // socket port for client comms

	private ServerSocket serverSocket;
	private InetSocketAddress inboundAddr;
	private TcpPayload payload;
	private boolean isActive;

	/** Default constructor. */
	public TcpServer() {
		this.payload = new TcpPayload();
		initServerSocket();
		this.isActive = true;
	}

	public void run(){
		try {
			while (isActive) {
				// listen for and accept a client connection to serverSocket
				Socket sock = this.serverSocket.accept();
				OutputStream oStream = sock.getOutputStream();
				ObjectOutputStream ooStream = new ObjectOutputStream(oStream);
				ooStream.writeObject(this.payload); // send serilized payload
				ooStream.close();
				Thread.sleep(1000);
			}
		} catch (SecurityException se) {
			System.err.println("Unable to get host address due to security.");
			System.err.println(se.toString());
			System.exit(1);
		} catch (IOException ioe) {
			System.err.println("Unable to read data from an open socket.");
			System.err.println(ioe.toString());
			System.exit(1);
		} catch (InterruptedException ie) {
		} // Thread sleep interrupted
		finally {
			try {
				this.serverSocket.close();
			} catch (IOException ioe) {
				System.err.println("Unable to close an open socket.");
				System.err.println(ioe.toString());
				System.exit(1);
			}
		}
	}
	
	/** Initialize a server socket for communicating with the client. */
	private void initServerSocket() {
		this.inboundAddr = new InetSocketAddress(COMM_PORT);
		try {
			this.serverSocket = new java.net.ServerSocket(COMM_PORT);
			assert this.serverSocket.isBound();
			if (this.serverSocket.isBound()) {
				System.out.println("SERVER inbound data port "
						+ this.serverSocket.getLocalPort()
						+ " is ready and waiting for client to connect...");
			}
		} catch (SocketException se) {
			System.err.println("Unable to create socket.");
			System.err.println(se.toString());
			System.exit(1);
		} catch (IOException ioe) {
			System.err.println("Unable to read data from an open socket.");
			System.err.println(ioe.toString());
			System.exit(1);
		}
	}
	
	public void setActive(boolean active){
		this.isActive = active;
		System.out.println("Server wurde beendet!");
	}

}
