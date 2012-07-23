package hof.player;

import hof.net.userMessages.SensorInfoMessage;

public class SensorInput {
	private Player player;
	private SensorInfoMessage message;
	
	public SensorInput(Player player, SensorInfoMessage message) {
		this.player = player;
		this.message = message;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public SensorInfoMessage getMessage() {
		return message;
	}
}
