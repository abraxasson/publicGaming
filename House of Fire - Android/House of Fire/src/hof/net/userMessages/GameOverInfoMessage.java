package hof.net.userMessages;


public class GameOverInfoMessage extends AbstractMessage{
	private static final long serialVersionUID = 37295872L;
	
	public GameOverInfoMessage() {
		super(AbstractMessage.GameOver);
	}
}
