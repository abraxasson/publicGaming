package hof.player;

import hof.net.userMessages.InputInfoMessage;

public class PlayerInput {

	private int number = 0;
	private Player player;
	private InputInfoMessage message;
	
	public PlayerInput() {
		
	}
	
	public PlayerInput(Player player, InputInfoMessage message) {
		this.player = player;
		this.message = message;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public InputInfoMessage getMessage() {
		return message;
	}
	
}
