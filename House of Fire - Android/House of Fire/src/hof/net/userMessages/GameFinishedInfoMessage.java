package hof.net.userMessages;

public class GameFinishedInfoMessage extends AbstractMessage{
	private static final long serialVersionUID = 37295872L;
	private boolean won;
	
	public GameFinishedInfoMessage(boolean won) {
		super(Type.GameFinished);
		this.won = won;
	}

	public boolean hasWon() {
		return won;
	}
	
}
