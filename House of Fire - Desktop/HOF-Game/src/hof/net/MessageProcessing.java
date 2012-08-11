package hof.net;

import hof.core.utils.ColorList;
import hof.core.utils.Settings;
import hof.core.utils.WordFilter;

import hof.level.effects.AbstractCloud;
import hof.level.effects.Lightning;
import hof.level.effects.Rain;
import hof.level.effects.WaterPressure;
import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.ButtonInfoMessage;
import hof.net.userMessages.LogoutInfoMessage;
import hof.net.userMessages.PlayerInfoMessage;
import hof.net.userMessages.SMSInfoMessage;
import hof.net.userMessages.SensorInfoMessage;
import hof.net.userMessages.ValidationInfoMessage;
import hof.net.userMessages.WaterPressureInfoMessage;
import hof.player.ButtonInput;
import hof.player.Player;
import hof.player.SensorInput;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class is an important part of the network communication of this game.
 * All received AbstractMessages are processed here.  
 * This class is designed as a Singleton so you cannot instantiate it, but get the instance over the given static method.
 *
 */
public class MessageProcessing {

	/**
	 * the only instance of this class is stored here
	 */
	private static MessageProcessing instance;
	
	/**
	 * Stores all received Messages and holds them until they ae processed
	 */
	private LinkedList<AbstractMessage> messageQueue;
	/**
	 * Holds the active players
	 */
	private ArrayList<Player> activePlayers;
	/**
	 * Stores new players who have just joined the game.
	 */
	private LinkedList<Player> playerQueue;
	/**
	 * Holds the received ButtonInput until they are used.
	 */
	private LinkedList<ButtonInput> buttonQueue;
	/**
	 * Holds the received SensorInput until they are used.
	 */
	private LinkedList<SensorInput> sensorQueue; 
	/**
	 * Holds the received SMS-SpecialEffects until they become active.
	 */
	private LinkedList<AbstractCloud> smsQueue;
	/**
	 * Holds the received WaterPressureMessages until they are processed.
	 */
	private LinkedList<WaterPressureInfoMessage> waterPressureQueue;

	
	/**
	 * Is used to store Colors for the players
	 */
	private ColorList colorList;
	/**
	 * Filters bad words.
	 */
	private WordFilter filter;
	
	/**
	 * Client is used to send Messages over the UDP-Network.
	 */
	private UdpClientThread udpClient;

	/**
	 * Used to get the Singleton instance of this class.
	 * If there is not already an instance a new one is created.
	 * @return the instance of this class
	 */
	public static MessageProcessing getInstance() {
		if (instance == null) {
			instance = new MessageProcessing();
		}
		return instance;
	}

	/**
	 * Private constructor.
	 * Creates all the queues and lists for the later use. 
	 */
	private MessageProcessing() {
		messageQueue = new LinkedList<>();
		activePlayers = new ArrayList<Player>();
		playerQueue = new LinkedList<>();
		buttonQueue = new LinkedList<ButtonInput>();
		sensorQueue = new LinkedList<SensorInput>();
		smsQueue = new LinkedList<>();
		colorList = new ColorList();
		filter = new WordFilter();
		waterPressureQueue = new LinkedList<WaterPressureInfoMessage>();
		udpClient = UdpClientThread.getInstance();
	}
	
	/**
	 * Gets the first element of the MessageQueue and processes it.
	 * This method <b>has to be called</b> in the render method to guarantee network communication.
	 * 
	 */
	public void processMessageQueue() {
		if (!messageQueue.isEmpty()) {
			processMessage(messageQueue.poll());
		}
	}

	/**
	 * Processes the received Message. Checks what type it is and calls the
	 * right handling method for this type
	 * 
	 * @param message
	 *            the AbstractMessage received by the client
	 * @param address
	 *            the InetAddress of the sender
	 */
	private void processMessage(AbstractMessage message) {
		int type = message.getType();
		switch (type) {
		case AbstractMessage.PlayerInfo:
			PlayerInfoMessage playerMessage = (PlayerInfoMessage) message;
			processPlayerMessage(playerMessage);
			break;
		case AbstractMessage.ButtonInfo:
			ButtonInfoMessage inputMessage = (ButtonInfoMessage) message;
			processButtonMessage(inputMessage);
			break;
		case AbstractMessage.LogoutInfo:
			LogoutInfoMessage logOutMessage = (LogoutInfoMessage) message;
			processLogoutMessage(logOutMessage);
			break;
		case AbstractMessage.WaterPressure:
			WaterPressureInfoMessage pressureMessage = (WaterPressureInfoMessage) message;
			processWaterPressureMessage(pressureMessage);
			break;
		case AbstractMessage.SensorInfo:
			SensorInfoMessage sensorMessage = (SensorInfoMessage) message;
			processSensorMessage(sensorMessage);
			break;
		case AbstractMessage.SMSInfo:
			SMSInfoMessage smsMessage = (SMSInfoMessage) message;
			processSmsMessage(smsMessage);
			break;
		default:
			System.out.println("Paket unbekannt");
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
	private void processPlayerMessage(PlayerInfoMessage message) {
		Player player;
		InetAddress address = message.getIa();
		if (checkPlayer(address)) {
			player = getPlayer(address);
			udpClient.prepareMessage(new ValidationInfoMessage(player.getColor().r,player.getColor().g,player.getColor().b), address);
		} else {
			player = new Player(filter.checkName(message.getName()), address, colorList.getNextColor());
		}
		
		if (!checkPlayer(address) && activePlayers.size() <= Settings.maxPlayers) {
			activePlayers.add(player);
			playerQueue.add(player);
			player.setLastInput();
			udpClient.prepareMessage(new ValidationInfoMessage(player.getColor().r,player.getColor().g,player.getColor().b), address);
		}
	}

	/**
	 * Receives the InputInfoMessage and notifies the game about this.
	 * 
	 * @param inputMessage
	 * @param address
	 */
	private void processButtonMessage(ButtonInfoMessage inputMessage) {
		InetAddress address = inputMessage.getIa();
		if (checkPlayer(address)) {
			Player player = getPlayer(address);
			player.setAlive(true);
			player.setLastInput();
			buttonQueue.add(new ButtonInput(player, inputMessage.getState()));
		}
	}

	/**
	 * Processes the received SensorMessages.
	 * It checks if there is an existing player, adds him to the list and sets him alive.
	 * @param sensorMessage to process
	 * @param address the address from where the message came
	 */
	private void processSensorMessage(SensorInfoMessage sensorMessage){
		InetAddress address = sensorMessage.getIa();
		if (checkPlayer(address)) {
			Player player = getPlayer(address);
			player.setAlive(true);
			player.setLastInput();
			sensorQueue.add(new SensorInput(player, sensorMessage));
		}
	}
	
	/**
	 * Processes an incoming WaterPressureMessage. <br>
	 * If the player who sent the Message is active, this message is added to the Queue. <br>
	 * If the water level reaches 0 the player's pumping flag is set to true, else the flag is set to false. <br|
	 * As long he is "pumping" the time until he is kicked out when he does not send any data is increased.
	 * 
	 * @param pressureMessage
	 */
	private void processWaterPressureMessage(WaterPressureInfoMessage pressureMessage) {
		InetAddress address = pressureMessage.getIa();
		if (checkPlayer(address)) {
			Player player = getPlayer(address);
			player.setAlive(true);
			player.setLastInput();
			if (pressureMessage.getPressure() <= 0) {
				player.setPumping(true);
			} else {
				player.setPumping(false);
			}
			waterPressureQueue.add(pressureMessage);
		}
	}
	
	/**
	 * Removes the player with the given InetAddress from the list of active
	 * Players.
	 * 
	 * @param logoutMessage
	 * @param address
	 */
	private void processLogoutMessage(LogoutInfoMessage logOutMessage) {
		Iterator<Player> iter = activePlayers.iterator();
		while (iter.hasNext()) {
			Player player = iter.next();
			if (logOutMessage.getIa().equals(player.getIp())) {
				player.setAlive(false);
				this.colorList.reuseColor(player.getColor());
				iter.remove();
			}
		}
	}
	
	/**
	 * Processes the received SmsMessages.
	 * @param smsMessage to process
	 * @param address - the address from where the message came
	 */
	private void processSmsMessage(SMSInfoMessage smsMessage) {		
		AbstractCloud effect = null;
		switch (smsMessage.getEffectType()) {
		case SMSInfoMessage.LIGHTNING: 
			if (Lightning.isReady()) effect = new Lightning();
			break;
		case SMSInfoMessage.RAIN:
			if (Rain.isReady()) effect = new Rain();
			break;
		case SMSInfoMessage.PRESSURE: 
			if (WaterPressure.isReady()) effect = new WaterPressure();
			break;
		default:
			double rand = Math.random();
			if (rand < 0.2) {
				if (Lightning.isReady()) effect = new Lightning();
			} else if (rand < 0.6) {
				if (Rain.isReady()) effect = new Rain();
			} else {
				if (WaterPressure.isReady()) effect = new WaterPressure();
			}
			break;
		}
		if (effect != null) {
			this.smsQueue.add(effect);
		}
	}
	
	/**
	 * Returns the player which has the given InetAddress
	 * @param address of the player
	 * @return the player
	 */
	private Player getPlayer(InetAddress address) {
		for (Player player : activePlayers) {
			if (player.getIp().equals(address)) {
				return player;
			}
		}
		return null;
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
	 * Removes all players from the list of active Players.
	 * In addition to this all colors become available again.
	 */
	public void clearPlayerList() {
		activePlayers.clear();
		colorList.reuseColors();
	}

	/**
	 * Returns and removes the next Player to add to the game
	 * 
	 * @return the first new Player
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
	 * Returns and removes the first ButtonInput
	 * 
	 * @return the first ButtonInput in the Queue
	 */
	public ButtonInput getButtonInput() {
		return buttonQueue.poll();
	}
	
	/**
	 * Check if the WaterPressurequeue has any WaterPressureInfoMessages in it
	 * @return false if queue is empty, else true
	 */
	public boolean hasWaterPressureMessage(){
		return !this.waterPressureQueue.isEmpty();
	}
	
	/**
	 * Returns and removes the first WaterPressureMessage from the Queue
	 * @return the first WatterPressureMessage
	 */
	public WaterPressureInfoMessage getWaterPressureMessage(){
		return this.waterPressureQueue.poll();
	}
	
	/**
	 * Returns and removes the first SensorInput
	 * 
	 * @return the first SensorInput in the Queue
	 */
	public SensorInput getSensorInput(){
		return this.sensorQueue.poll();
	}
	
	/**
	 * Return the Queue of the received text messages
	 * @return LinkedList with AbstractCloud
	 */
	public LinkedList<AbstractCloud> getSmsQueue() {
		return smsQueue;
	}
	
	/**
	 * Checks if LinkedList has any SMS
	 * 
	 * @return true if PlayerInput is available
	 */
	public boolean hasSMS() {
		return !this.smsQueue.isEmpty();
	}

	/**
	 * Checks if LinkedList has any ButtonInput
	 * 
	 * @return true if ButtonrInput is available
	 */
	public boolean hasButtonInput() {
		return !this.buttonQueue.isEmpty();
	}
	
	/**
	 * Checks if LinkedList has any SensorInput
	 * 
	 * @return true if SensorInput is available
	 */
	public boolean hasSensorInput(){
		return !this.sensorQueue.isEmpty();
	}
	
	/** 
	* 	Clears the smsQueue
	 */
	public void emptySmsInput() {
		smsQueue.clear();
	}
	
	/**
	 * Clears the sensorQueue
	 */
	public void emptySensorInput() {
		sensorQueue.clear();
	}
	
	/**
	 * Clears the buttonQueue
	 */
	public void emptyButtonInput() {
		buttonQueue.clear();
	}
	
	/**
	 * Clears the sensorQueue and the buttonQueue
	 */
	public void emptyInputQueues() {
		sensorQueue.clear();
		buttonQueue.clear();
		smsQueue.clear();
		waterPressureQueue.clear();
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
	
	/**
	 * Adds a new AbstractMessage to the Queue.
	 * 
	 * @param message -  the message to add
	 */
	public void addMessage(AbstractMessage message) {
		messageQueue.add(message);
	}
	
	/**
	 * Removes inactive players from the game
	 */
	public void removeInactivePlayers(){
		for(Player player : this.activePlayers){
			if(System.currentTimeMillis() - player.getLastInput() > Settings.playerTimeout && !player.isPumping()){
				player.setAlive(false);
			}
			if(System.currentTimeMillis() - player.getLastInput() > Settings.playerTimeout * 6){
				player.setAlive(false);
			}
		}
		
		Iterator<Player> iter = this.activePlayers.iterator();
		while(iter.hasNext()){
			Player player = iter.next();
			if(!player.getAlive()){
				this.colorList.reuseColor(player.getColor());
				iter.remove();
			}
		}
	}
}
