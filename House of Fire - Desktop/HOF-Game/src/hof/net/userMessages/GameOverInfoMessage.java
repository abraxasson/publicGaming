package hof.net.userMessages;

/**
 * This message shows that the game is over and the players have lost.
 */
public class GameOverInfoMessage extends AbstractMessage{
	
	/**
	 * Creates a new GameOvrInfoMessage.
	 */
	public GameOverInfoMessage() {
		super(AbstractMessage.GameOver);
	}
}
