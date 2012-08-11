package hof.player;


/**
 * This class stores the received data from the pressed button and holds the corresponding player object. <p>
 * The information can have up to three different states.<br>
 * {@link hof.net.userMessages.ButtonInfoMessage#LEFT Left} or {@link hof.net.userMessages.ButtonInfoMessage#RIGHT right},
 * if the left or right button is pressed or {@link hof.net.userMessages.ButtonInfoMessage#NORMAL normal} if no button is pressed.
 * 
 */
public class ButtonInput {

	/**
	 * The player who has made the input.
	 */
	private Player player;
	/**
	 * The current button state.
	 */
	private int state;
	
	/**
	 * Constructs a new ButtonInput.
	 * @param player - player who made the input
	 * @param state - the current state
	 */
	public ButtonInput(Player player, int state) {
		this.player = player;
		this.state = state;
	}

	/**
	 * Returns the player who made the input.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the current state of the button.
	 * @return the state ({@link hof.net.userMessages.ButtonInfoMessage#LEFT Left},
	 *  {@link hof.net.userMessages.ButtonInfoMessage#RIGHT Right} or {@link hof.net.userMessages.ButtonInfoMessage#NORMAL Normal})
	 */
	public int getButtonState() {
		return state;
	}
	
}
