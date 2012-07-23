package hof.net;

import hof.core.utils.ColorList;
import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.AbstractMessage.Type;
import hof.net.userMessages.InputInfoMessage;
import hof.net.userMessages.PlayerInfoMessage;
import hof.net.userMessages.ValidationInfoMessage;
import hof.player.Player;
import hof.player.PlayerInput;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MessageProcessing {

	private ArrayList<Player> activePlayers;
	private ArrayList<Player> newPlayers;
	private LinkedList<Player> playerQueue;
	private LinkedList<PlayerInput> inputQueue;
	private static MessageProcessing instance;
	private ColorList colorList;
	
	private UdpClientThread udpClient;

	public static MessageProcessing getInstance() {
		if (instance == null) {
			instance = new MessageProcessing();
		}
		return instance;
	}

	private MessageProcessing() {
		activePlayers = new ArrayList<Player>();
		newPlayers = new ArrayList<Player>();
		playerQueue = new LinkedList<>();
		inputQueue = new LinkedList<PlayerInput>();
		colorList = new ColorList();
		
		udpClient = UdpClientThread.getInstance();
	}

	/**
	 * Processeses the received Message. Checks what type it is and calls the
	 * right handling method for this type
	 * 
	 * @param message
	 *            the AbstractMessage received by the client
	 * @param address
	 *            the InetAddress of the sender
	 */
	public void processMessage(AbstractMessage message, InetAddress address) {
		if (address == null) {
			//return;
		}
		Type type = message.getType();
		switch (type) {
		case InputInfo:
			InputInfoMessage inputMessage = (InputInfoMessage) message;
			processInputMessage(inputMessage, address);
			break;
		case LogoutInfo:
			processLogoutMessage(address);
			break;
		case PlayerInfo:
			PlayerInfoMessage playerMessage = (PlayerInfoMessage) message;
			processPlayerMessage(playerMessage, address);
			break;
		case ValidationInfo:
			processValidationMessage(address);
			break;
		case Retry:
			System.out.println(message.toString());
			break;
		case WaterPressure:
			System.out.println(message.toString());
			break;
		default:
			break;
		}
	}
	
	/**
	 * Checks the PlayerInfoMessage for the name and creates a new Player with
	 * this name and the given InetAddress. The player is added to the list of
	 * players.
	 * 
	 * @param message
	 * @param address
	 */
	private void processPlayerMessage(PlayerInfoMessage message, InetAddress address) {
		Player player = new Player(message.getName(), address, colorList.getNextColor());
		if (!checkPlayer(address)) {
			newPlayers.add(player);
			udpClient.setIA(address);
			
			udpClient.sendObject(new ValidationInfoMessage(player.getColor().r,player.getColor().g,player.getColor().b));
			System.out.println("New Player online");
		} else {
			System.out.println("Spieler existiert bereits");
		}
	}

	private void processValidationMessage(InetAddress address) {
		System.out.println("Validation des Handys erhalten");
		Player player = getPlayer(address, newPlayers);
		activePlayers.add(player);
		playerQueue.add(player);
		newPlayers.remove(player);		
	}

	/**
	 * Receives the InputInfoMessage and notifies the game about this.
	 * 
	 * @param inputMessage
	 * @param address
	 */
	private void processInputMessage(InputInfoMessage inputMessage,	InetAddress address) {
		if (checkPlayer(address)) {
			Player player = getPlayer(address, activePlayers);
			player.incScore();
			player.setAlive(true);
			inputQueue.add(new PlayerInput(player, inputMessage));
		}
	}

	private Player getPlayer(InetAddress address, List<Player> list) {
		for (Player player : list) {
			if (player.getIp().equals(address)) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Removes the player with the given InetAddress from the list of active
	 * Players.
	 * 
	 * @param logoutMessage
	 * @param address
	 */
	private void processLogoutMessage(InetAddress address) {
		Iterator<Player> iter = activePlayers.iterator();
		Iterator<Player> iter2 = newPlayers.iterator();
		while (iter.hasNext()) {
			Player player = iter.next();
			if (address.equals(player.getIp())) {
				System.out.println("Player: " + player.getName()
						+ " hat sich ausgeloggt");
				iter.remove();
			}
		}
		while (iter2.hasNext()) {
			Player player = iter2.next();
			if (address.equals(player.getIp())) {
				System.out.println("Player: "+player.getName()+" wurde aus newPlayers entfernt!");
				iter2.remove();
			}
		}
		
		
	}

	/**
	 * Returns the list of the active Players.
	 * 
	 * @return ArrayList of active players.
	 */
	public ArrayList<Player> getPlayerList() {
		return activePlayers;
	}

	/**
	 * Returns the next Player to add to the game
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return playerQueue.poll();
	}
	
	/**
	 * Checks if LinkedList has any Players
	 * 
	 * @return true if Players are available
	 */
	public boolean hasPlayers() {
		return !this.playerQueue.isEmpty();
	}
	
	/**
	 * Returns the next PlayerInput
	 * 
	 * @return
	 */
	public PlayerInput getInput() {
		return inputQueue.poll();
	}

	/**
	 * Checks if LinkedList has any PlayerInput
	 * 
	 * @return true if PlayerInput is available
	 */
	public boolean hasInput() {
		return !this.inputQueue.isEmpty();
	}

	/**
	 * Checks if the given InetAddress is already active.
	 * 
	 * @param address
	 * @return true if the InetAddress is already active.
	 */
	private boolean checkPlayer(InetAddress address) {
		boolean check = false;
		for (Player player : activePlayers) {
			if (address.equals(player.getIp())) {
				check = true;
			}
		}
		for (Player player : newPlayers) {
			if (address.equals(player.getIp())) {
				check = true;
			}
		}
		return check;
	}

	public static void main(String[] args) {
		MessageProcessing m = MessageProcessing.getInstance();
		try {
			m.processMessage(new PlayerInfoMessage("Florian"),
					InetAddress.getLocalHost());
			// m.processMessage(new PlayerInfoMessage("Marcel"), null);
			// m.processMessage(new PlayerInfoMessage("Manuel"),
			// InetAddress.getByName("niue"));
			// m.processMessage(new PlayerInfoMessage("Manuel"),
			// InetAddress.getByName("niue"));
			// m.processMessage(new LogoutInfoMessage(),
			// InetAddress.getByName("niue"));

		} catch (Exception e) {

		}

		for (Player player : m.getPlayerList()) {
			System.out.println(player);
		}

	}
}
