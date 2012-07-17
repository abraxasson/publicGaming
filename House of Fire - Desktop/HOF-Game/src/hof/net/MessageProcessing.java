package hof.net;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import hof.player.PlayerInput;
import hof.player.Player;
import hof.net.userMessages.*;
import hof.net.userMessages.AbstractMessage.Type;

public class MessageProcessing {

	private ArrayList<Player> list;
	private LinkedList<PlayerInput> queue;
	
	private static MessageProcessing instance;

	public static MessageProcessing getInstance() {
		if (instance == null) {
			instance = new MessageProcessing();
		}
		return instance;
	}
	
	private MessageProcessing() {
		list = new ArrayList<Player>();
		queue = new LinkedList<PlayerInput>();
	}
	
	/**
	 * Processeses the received Message.
	 * Checks what type it is and calls the right handling method for this type
	 * 
	 * @param message the AbstractMessage received by the client
	 * @param address the InetAddress of the sender
	 */
	public  void processMessage(AbstractMessage message, InetAddress address) {
		if (address == null) {
			return;
		}
		Type type = message.getType();
		switch (type) {
		case InputInfo:
			InputInfoMessage inputMessage = (InputInfoMessage) message;
			processInputMessage(inputMessage, address);
			break;
		case LogoutInfo:
			LogoutInfoMessage logoutMessage = (LogoutInfoMessage) message;
			processLogoutMessage(logoutMessage, address);
			break;
		case PlayerInfo:
			PlayerInfoMessage playerMessage = (PlayerInfoMessage) message;
			processPlayerMessage(playerMessage, address);			
			break;
		default:
			break;
		}
	}
	
	/**
	 * Receives the InputInfoMessage and notifies the game about this.
	 * 
	 * @param inputMessage
	 * @param address
	 */
	private  void processInputMessage(InputInfoMessage inputMessage, InetAddress address) {
		if (checkPlayer(address)) {
			getPlayer(address).incScore();
			queue.add(new PlayerInput());
		}
	}
	
	private Player getPlayer(InetAddress address) {
		for (Player player: list) {
			if (player.getIp().equals(address)) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Removes the player with the given InetAddress from the list of active Players.
	 * 
	 * @param logoutMessage
	 * @param address
	 */
	private  void processLogoutMessage(LogoutInfoMessage logoutMessage, InetAddress address) {
		Iterator<Player> iter = list.iterator();
		while (iter.hasNext()) {
			Player player = iter.next();
			if (address.equals(player.getIp())) {
				System.out.println("Player: " + player.getName() + " hat sich ausgeloggt");
				iter.remove();				
			}
		}
	}
	
	/**
	 * Checks the PlayerInfoMessage for the name and creates a new Player with this name and the given InetAddress. 
	 * The player is added to the list of players.
	 * 
	 * @param message
	 * @param address
	 */
	private  void processPlayerMessage(PlayerInfoMessage message, InetAddress address) {
		Player player = new Player(message.getName(), address);
		if (!checkPlayer(address)) {
			list.add(player);
			System.out.println("New Player online");
		} else {
			System.out.println("Spieler existiert bereits");
		}
	}
	
	/**
	 * Returns the list of the active Players.
	 * 
	 * @return ArrayList of active players.
	 */
	public ArrayList<Player> getList() {
		return list;		
	}
	
	/**
	 * Returns the next PlayerInput
	 * @return
	 */
	public PlayerInput getInput() {
		return queue.poll();
	}
	
	/**
	 * Checks if LinkedList has any PlayerInput
	 * @return true if PlayerInput is available
	 */
	public boolean hasInput() {
		if (!queue.isEmpty()) {
			queue.removeFirst();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if the given InetAddress is already active.
	 * 
	 * @param address 
	 * @return true if the InetAddress is already active.
	 */
	private  boolean checkPlayer(InetAddress address) {
		for (Player player: list) {
			if (address.equals(player.getIp())) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		MessageProcessing m = MessageProcessing.getInstance();
		try {
			m.processMessage(new PlayerInfoMessage("Florian"), InetAddress.getLocalHost());
			//m.processMessage(new PlayerInfoMessage("Marcel"), null);
			//m.processMessage(new PlayerInfoMessage("Manuel"), InetAddress.getByName("niue"));
			//m.processMessage(new PlayerInfoMessage("Manuel"), InetAddress.getByName("niue"));
			//m.processMessage(new LogoutInfoMessage(), InetAddress.getByName("niue"));
			
			
		} catch (Exception e) {
			
		}		
		
		for (Player player: m.getList()) {
			System.out.println(player);
		}

		
	}
}
