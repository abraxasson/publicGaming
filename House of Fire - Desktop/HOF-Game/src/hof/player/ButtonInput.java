package hof.player;

import hof.net.userMessages.ButtonInfoMessage;

public class ButtonInput {

	private Player player;
	private ButtonInfoMessage message;
	
	public ButtonInput(Player player, ButtonInfoMessage message) {
		this.player = player;
		this.message = message;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ButtonInfoMessage getMessage() {
		return message;
	}
	
}
