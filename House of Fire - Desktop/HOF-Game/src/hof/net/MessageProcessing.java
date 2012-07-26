package hof.net;

import hof.core.utils.ColorList;
import hof.core.utils.Settings;
import hof.core.utils.WordFilter;
import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.AbstractMessage.Type;
import hof.net.userMessages.ButtonInfoMessage;
import hof.net.userMessages.PlayerInfoMessage;
import hof.net.userMessages.SensorInfoMessage;
import hof.net.userMessages.ValidationInfoMessage;
import hof.player.ButtonInput;
import hof.player.Player;
import hof.player.SensorInput;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MessageProcessing {

	private ArrayList<Player> activePlayers;
	private LinkedList<Player> playerQueue;
	private LinkedList<ButtonInput> inputQueue;
	private LinkedList<SensorInput> sensorQueue; 
	private static MessageProcessing instance;
	private ColorList colorList;
	private WordFilter filter;
	
	private UdpClientThread udpClient;

	public static MessageProcessing getInstance() {
		if (instance == null) {
			instance = new MessageProcessing();
		}
		return instance;
	}

	private MessageProcessing() {
		activePlayers = new ArrayList<Player>();
		playerQueue = new LinkedList<>();
		inputQueue = new LinkedList<ButtonInput>();
		sensorQueue = new LinkedList<SensorInput>();
		colorList = new ColorList();
		filter = new WordFilter();
		
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
		case PlayerInfo:
			PlayerInfoMessage playerMessage = (PlayerInfoMessage) message;
			processPlayerMessage(playerMessage, address);
			break;
		case ButtonInfo:
			ButtonInfoMessage inputMessage = (ButtonInfoMessage) message;
			processButtonMessage(inputMessage, address);
			break;
		case LogoutInfo:
			processLogoutMessage(address);
			break;
		case Retry:
			System.out.println(message.toString());
			break;
		case WaterPressure:
			System.out.println(message.toString());
			break;
		case SensorInfo:
			SensorInfoMessage sensorMessage = (SensorInfoMessage) message;
			processSensorMessage(sensorMessage, address);
			System.out.println(message.toString());
			break;
		default:
			System.out.println("Input nicht kompatibel!");
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
		Player player;
		if (checkPlayer(address)) {
			player = getPlayer(address);
			udpClient.setIA(address);			
			udpClient.sendObject(new ValidationInfoMessage(player.getColor().r,player.getColor().g,player.getColor().b));
		} else {
			player = new Player(filter.checkName(message.getName()), address, colorList.getNextColor());
		}
		
		if (!checkPlayer(address) && activePlayers.size() <= Settings.maxPlayers) {
			activePlayers.add(player);
			playerQueue.add(player);
			player.setLastInput(System.currentTimeMillis());
			udpClient.setIA(address);			
			udpClient.sendObject(new ValidationInfoMessage(player.getColor().r,player.getColor().g,player.getColor().b));
			System.out.println("New Player online");
		} else {
			System.out.println("Spieler existiert bereits");
		}
	}

	/**
	 * Receives the InputInfoMessage and notifies the game about this.
	 * 
	 * @param inputMessage
	 * @param address
	 */
	private void processButtonMessage(ButtonInfoMessage inputMessage,	InetAddress address) {
		if (checkPlayer(address)) {
			Player player = getPlayer(address);
			player.setAlive(true);
			player.setLastInput(System.currentTimeMillis());
			inputQueue.add(new ButtonInput(player, inputMessage));
		}
	}

	private void processSensorMessage(SensorInfoMessage sensorMessage, InetAddress address){
		if (checkPlayer(address)) {
			Player player = getPlayer(address);
			player.incScore();
			player.setAlive(true);
			player.setLastInput(System.currentTimeMillis());
			sensorQueue.add(new SensorInput(player, sensorMessage));
		}
	}
	
	private Player getPlayer(InetAddress address) {
		for (Player player : activePlayers) {
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
		while (iter.hasNext()) {
			Player player = iter.next();
			if (address.equals(player.getIp())) {
				System.out.println("Player: " + player.getName()
						+ " hat sich ausgeloggt");
				player.setAlive(false);
				iter.remove();
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
	public ButtonInput getInput() {
		return inputQueue.poll();
	}
	
	public SensorInput getSensorInput(){
		return this.sensorQueue.poll();
	}

	/**
	 * Checks if LinkedList has any PlayerInput
	 * 
	 * @return true if PlayerInput is available
	 */
	public boolean hasInput() {
		return !this.inputQueue.isEmpty();
	}
	
	public boolean hasSensorInput(){
		return !this.sensorQueue.isEmpty();
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
		return check;
	}
	
	public void removeInactivePlayers(){
		Iterator<Player> iter = this.activePlayers.iterator();
		for(Player player : this.activePlayers){
			if(System.currentTimeMillis() - player.getLastInput() > Settings.playerTimeout){
				player.setAlive(false);
			}
		}
		while(iter.hasNext()){
			Player player = iter.next();
			if(!player.getAlive()){
				iter.remove();
			}
		}
	}
}
