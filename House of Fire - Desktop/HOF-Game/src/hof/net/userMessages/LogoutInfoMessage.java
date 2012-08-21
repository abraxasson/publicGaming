package hof.net.userMessages;

/**
 * This messages is send when a player logs himself out of the game.
 *
 */
public class LogoutInfoMessage extends AbstractMessage {


	/**
	 * Creates new LogoutInfoMessage.
	 */
	public LogoutInfoMessage() {
		super(AbstractMessage.LogoutInfo);
	}

	/**
	 * Returns the InetAddres of the player who logs out.
	 * @return the InetAddress in a String
	 */
	@Override
	public String toString() {
		return " " + getIa() +" -Logout!";
	}
}
