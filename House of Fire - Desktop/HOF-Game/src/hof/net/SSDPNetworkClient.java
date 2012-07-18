package hof.net;

import hof.net.userMessages.PlayerInfoMessage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class SSDPNetworkClient extends Thread {
	/**
	 * UPNP/SSDP client to demonstrate the usage of UDP multicast sockets.
	 * 
	 * @throws IOException
	 */
	private ArrayList<InetAddress> addresses = new ArrayList<InetAddress>();
	private UdpClientThread sender;
	private String name;
	private SSDPNetworkClient client;

	public SSDPNetworkClient(String name) {
		this.name = name;
	}

	private void multicast() throws IOException {
		try {
			InetAddress multicastAddress = InetAddress
					.getByName("239.255.255.250");
			// multicast address for SSDP
			final int port = 1900; // standard port for SSDP
			MulticastSocket socket = new MulticastSocket(port);
			socket.setReuseAddress(true);
			socket.setSoTimeout(15000);
			socket.joinGroup(multicastAddress);

			byte[] txbuf = DISCOVER_MESSAGE_ROOTDEVICE.getBytes("UTF-8");
			DatagramPacket hi = new DatagramPacket(txbuf, txbuf.length,
					multicastAddress, port);
			socket.send(hi);
			System.out.println("SSDP discover sent");
			System.out.println();

			do {
				byte[] rxbuf = new byte[8192];
				DatagramPacket packet = new DatagramPacket(rxbuf, rxbuf.length);
				socket.receive(packet);
				dumpPacket(packet);
			} while (true); // should leave loop by SocketTimeoutException
		} catch (SocketTimeoutException e) {
			System.out.println("Timeout");
			for (InetAddress a : this.addresses) {
				System.out.println(a.toString());
				sender = UdpClientThread.getInstance(a);
				System.out.println("Paket gesendet an "+a.toString());
				sender.sendObject(new PlayerInfoMessage(name));
				sender.sendObject(new PlayerInfoMessage(name));
				sender.sendObject(new PlayerInfoMessage(name));
			}
		}
	}

	private void dumpPacket(DatagramPacket packet) throws IOException {
		InetAddress addr = packet.getAddress();
		System.out.println("Response from: " + addr);
		if (!addresses.contains(addr) && addr.toString() != "127.0.0.1") {
			addresses.add(addr);
			System.out.println(addr + " wurde hinzugefügt!");
		}
		ByteArrayInputStream in = new ByteArrayInputStream(packet.getData(), 0,
				packet.getLength());
		copyStream(in, System.out);
	}

	private void copyStream(InputStream in, OutputStream out)
			throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		int c = bin.read();
		while (c != -1) {
			out.write((char) c);
			c = bin.read();
		}
		bout.flush();
	}

	private final static String DISCOVER_MESSAGE_ROOTDEVICE = "M-SEARCH * HTTP/1.1\r\n"
			+ "ST: upnp:rootdevice\r\n"
			+ "MX: 3\r\n"
			+ "MAN: ssdp:discover \r\n" + "HOST: 239.255.255.250:1900\r\n\r\n";

	public void run() {
		client = new SSDPNetworkClient(this.name);
		try {
			client.multicast();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (InetAddress a : client.getIp()) {
			String ip = a.toString();
			ip = ip.substring(1, ip.length());
			System.out.println(ip);
		}
	}

	private ArrayList<InetAddress> getIp() {
		return this.addresses;
	}
}
