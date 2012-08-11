package hof.player;

import hof.net.userMessages.SensorInfoMessage;

/**
 * This class stores the received sensor input and the corresponding player object.
 * <p>
 * The sensor input is transmitted in three float values.
 */
public class SensorInput {
	/**
	 * The player who made an input.
	 */
	private Player player;
	private float x;
	private float y;
	private float z;
	
	/**
	 * @param player - the player who made an input
	 * @param message - the received Message with the input
	 */
	public SensorInput(Player player, SensorInfoMessage message) {
		this.player = player;
		x = message.getX();
		y = message.getY();
		z = message.getZ();
	}

	/**
	 * Returns the player who made an input.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the x - Coordinate of the sensor
	 * @return the x Coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns the y - Coordinate of the sensor
	 * @return the y Coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * Returns the z - Coordinate of the sensor
	 * @return the z Coordinate
	 */
	public float getZ() {
		return z;
	}
}
