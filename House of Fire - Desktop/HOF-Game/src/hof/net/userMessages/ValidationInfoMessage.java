package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This message is send after a {@link hof.net.userMessages.PlayerInfoMessage PlayerInfoMessage} was received. <br>
 * It validates the log in and sends back the color of the player.
 *
 */
public class ValidationInfoMessage extends AbstractMessage {


	/**
	 * The red component of the color.
	 */
	private float r;
	/**
	 * The green component of the color.
	 */
	private float g;
	/**
	 * The blue component of the color.
	 */
	private float b;
	
	/**
	 * Creates a new ValidationInfoMessagewith the given components.
	 * @param r the red component of the color
	 * @param g the green component of the color
	 * @param b the blue component of the color
	 */
	public ValidationInfoMessage(float r, float g, float b) {
		super(AbstractMessage.ValidationInfo);
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Returns the red component of the color.
	 * @return the red component of the color
	 */
	public float getR() {
		return r;
	}

	/**
	 * Returns the green component of the color.
	 * @return the green component of the color
	 */
	public float getG() {
		return g;
	}

	/**
	 * Returns the blue component of the color.
	 * @return the blue component of the color
	 */
	public float getB() {
		return b;
	}
	
	/**
	 * Writes the three color components into the given stream.
	 * @param stream the stream to write into
	 */
	@Override
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeFloat(r);
		stream.writeFloat(g);
		stream.writeFloat(b);
	}
}
