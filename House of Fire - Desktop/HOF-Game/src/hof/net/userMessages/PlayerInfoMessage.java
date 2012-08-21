package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This message is send when a new player joins the game.
 *
 */
public class PlayerInfoMessage extends AbstractMessage {

	/**
	 * The name of the player.
	 */
	private String name;

	/**
	 * Creates a new PlayerInfoMessage with the given name.
	 * @param name the name of the new player
	 */
	public PlayerInfoMessage(String name) {
		super(AbstractMessage.PlayerInfo);
		this.name = name;
	}

	/**
	 * Returns the name of the new player.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the super.toString() plus the name.
	 * @return the super.toString() plus the name
	 */
	@Override
	public String toString() {
		return super.toString() + " " + name;
	}
	
	/**
	 * Writes the name into the given stream.
	 * @param stream the stream to write into
	 */
	@Override
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeUTF(name);
	}
}
