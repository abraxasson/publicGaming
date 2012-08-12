package hof.player;

import java.net.InetAddress;

import com.badlogic.gdx.graphics.Color;

/**
 * Represents the Player. In this class every important information about the
 * player is stored.
 * 
 */
public class Player implements Comparable<Player> {
	/**
	 * The player's name.
	 */
	private String name;
	/**
	 * The InetAddress which helps to identify his actions.
	 */
	private InetAddress ip;
	/**
	 * The score of the player
	 */
	private int score;
	/**
	 * His representing color
	 */
	private Color color;
	/**
	 * If he is still playing
	 */
	private boolean isAlive;
	/**
	 * If he switched to the pumping activity.
	 */
	private boolean isPumping;
	/**
	 * The time when the last Input was received.
	 */
	private long lastInput;
	/**
	 * The bonus-points the player gathers during a level.
	 */
	private int bonuspoints;
	/**
	 * The minus-points the player gathers during a level.
	 */
	private int minuspoints;

	/**
	 * Main-Constructor <br>
	 * Creates a new Player with the given name, the InetAddress and assigns him
	 * the given color
	 * 
	 * @param name
	 *            - the name for the player
	 * @param ip
	 *            - the InetAddress which identifies his actions
	 * @param color
	 *            - his color
	 */
	public Player(String name, InetAddress ip, Color color) {
		this.name = name;
		this.ip = ip;
		this.score = 0;
		this.color = color;
		bonuspoints = 0;
		minuspoints = 0;
		isAlive = true;
		setPumping(false);
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @return the players name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the InetAddress of the player.
	 * 
	 * @return the InetAddress
	 */
	public InetAddress getIp() {
		return ip;
	}

	/**
	 * Returns the score of the player.
	 * 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets a new Score.
	 * 
	 * @param score
	 *            - the new score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Increases the player score by 10.
	 */
	public void incScore() {
		score += 10;
	}

	/**
	 * Returns the color of the player
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns if the player is still alive.
	 * 
	 * @return isAlive
	 */
	public boolean getAlive() {
		return isAlive;
	}

	/**
	 * Sets if the player is alive.
	 * 
	 * @param isAlive
	 *            - active or inactive
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * Returns the time when the last Input was received.
	 * 
	 * @return the time of the last Input
	 */
	public long getLastInput() {
		return lastInput;
	}

	/**
	 * Sets the last Input to the current Time.
	 */
	public void setLastInput() {
		this.lastInput = System.currentTimeMillis();
	}

	/**
	 * Returns the bonus-points.
	 * 
	 * @return bonus-points
	 */
	public int getBonuspoints() {
		return bonuspoints;
	}

	/**
	 * Increases the bonus-points by the given value
	 * 
	 * @param bonuspoints
	 *            - the value to add
	 */
	public void increaseBonuspoints(int bonuspoints) {
		if (bonuspoints > 0) {
			this.bonuspoints += bonuspoints;
		}
	}

	/**
	 * Resets the bonus-points to 0.
	 */
	public void resetBonuspoints() {
		bonuspoints = 0;
	}

	/**
	 * Returns the minus-points.
	 * 
	 * @return minus-points
	 */
	public int getMinuspoints() {
		return minuspoints;
	}

	/**
	 * Increases the minus-points by the given value
	 * 
	 * @param minuspoints
	 */
	public void increaseMinuspoints(int minuspoints) {
		if (this.minuspoints < 0) {
			this.minuspoints += minuspoints;
		}
	}

	/**
	 * Resets the minus-points to 0.
	 */
	public void resetMinuspoints() {
		minuspoints = 0;
	}

	/**
	 * Returns if the player is pumping
	 * 
	 * @return true if isPumping
	 */
	public boolean isPumping() {
		return isPumping;
	}

	/**
	 * Sets if the player is pumping
	 * 
	 * @param isPumping
	 */
	public void setPumping(boolean isPumping) {
		this.isPumping = isPumping;
	}

	/**
	 * Compares this player to another by their score.
	 * 
	 * @return difference between the score
	 */
	@Override
	public int compareTo(Player p) {
		return p.score - score;
	}

	/**
	 * Overrides toString and returns the basic informations of the player
	 * 
	 * @return basic Information: Name InetAddress Score Color
	 */
	@Override
	public String toString() {
		return name + " " + ip + " " + score + " " + color.toString();
	}
}
