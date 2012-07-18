package hof.net;

import java.net.UnknownHostException;



public class Main {

	public static void main(String[] args) throws UnknownHostException {
		SSDPNetworkClient explorer = new SSDPNetworkClient("Manuel");
		explorer.start();
	}
}
