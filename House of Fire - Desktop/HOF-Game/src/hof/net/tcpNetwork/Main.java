package hof.net.tcpNetwork;
import java.net.InetAddress;
import java.net.UnknownHostException;



public class Main {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		TcpServer server = new TcpServer();
		TcpClient client = new TcpClient();
		server.start();
		client.start();
		Thread.sleep(5000);
		server.setActive(false);
		
	}

}
