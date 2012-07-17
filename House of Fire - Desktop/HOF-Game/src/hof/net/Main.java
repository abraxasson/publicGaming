package hof.net;

import hof.net.userMessages.PlayerInfoMessage;

public class Main {

	public static void main(String[] args) {
		UdpClientThread udp = UdpClientThread.getInstance();
		udp.sendObject(new PlayerInfoMessage("Florian"));
		udp.setActive(false);
	}
}
