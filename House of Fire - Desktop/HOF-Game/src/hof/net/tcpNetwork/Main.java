package hof.net.tcpNetwork;
import hof.net.userMessages.PlayerInfoMessage;

import java.net.UnknownHostException;



public class Main {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		TcpServer server = new TcpServer(new PlayerInfoMessage("Manuel"));
		server.start();
		TcpClient client = new TcpClient();
		client.start();
		
		Thread.sleep(5000);
		server.setActive(false);
		
	}

}
