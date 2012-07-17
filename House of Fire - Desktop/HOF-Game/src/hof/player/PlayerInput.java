package hof.player;

public class PlayerInput {

	private int number = 0;
	private Player player;
	
	public PlayerInput() {
		
	}
	
	public PlayerInput(Player player) {
		this.player = player;
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
	
}
