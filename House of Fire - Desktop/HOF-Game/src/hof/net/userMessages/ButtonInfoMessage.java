package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This message is used to express if an button is pressed or not. It also shows which button is pressed.
 * <br> For this the three constants NORMAL, LEFT and RIGHT are used.
 */
public class ButtonInfoMessage extends AbstractMessage {
	/**
	 * Expresses that no button is pressed.
	 */
	public static final int NORMAL = 1;
	/**
	 * Expresses that the left button is pressed.
	 */
	public static final int LEFT = 2;
	/**
	 * Expresses that the right button is pressed.
	 */
	public static final int RIGHT = 4;
	private int state;
	
	/**
	 * Creates a new ButtonInfoMessage with the given state.
	 * @param state the state of the button
	 */
	public ButtonInfoMessage(int state) {
		super(AbstractMessage.ButtonInfo);
		this.state = state;
	}
	
	/**
	 * Returns the state of the button.
	 * @return the state
	 */
	public int getState(){
		return state;
	}
	
	/**
	 * Calls super.toString() and adds the state of the button.
	 */
	@Override
	public String toString() {
		return super.toString() + " -Button pressed! " + state;
	}

	/**
	 * Calls the super method and also writes the state into the stream.
	 */
	@Override
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeInt(getState());
	}
	
}
