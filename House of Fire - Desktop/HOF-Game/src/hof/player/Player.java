package hof.player;
import java.net.InetAddress;

import com.badlogic.gdx.graphics.Color;

/**
 * Represents the Player.
 * In this class every important information about the player is stored.
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
	 * Test-Constructor 
	 * Just calls the second Constructor with its parameters and the Color White.
	 * 
	 * @param name
	 * @param ip
	 */
	@Deprecated
	public Player(String name, InetAddress ip) {
		this(name, ip, Color.WHITE);
	}
	
	/**
	 * Main-Constructor <br>
	 * Creates a new Player with the given name, the InetAddress and assigns him the given color
	 * @param name - the name for the player
	 * @param ip - the InetAddress which identifies his actions
	 * @param color - his color
	 */
	public Player(String name, InetAddress ip, Color color) {
		this.name = name;
		this.ip = ip;
		this.score = 0;
		this.color = color;
		isAlive = true;
		setPumping(false);
	}
	
	/**
	 * Returns the name of the player.
	 * @return the players name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the InetAddress of the player.
	 * @return the InetAddress
	 */
	public InetAddress getIp() {
		return ip;
	}
	
	/**
	 * Returns the score of the player.
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Sets a new Score.
	 * 
	 * @param score - the new score
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
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns if the player is still alive.
	 * @return isAlive
	 */
	public boolean getAlive() {
		return isAlive;
	}

	/**
	 * Sets if the player is alive.
	 * @param isAlive - active or inactive
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * Returns the time when the last Input was received.
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
	 * @return bonus-points
	 */
	public int getBonuspoints() {
		return bonuspoints;
	}

	/**
	 * Sets the bonus-points to the given value
	 * @param bonuspoints - the new value
	 */
	public void setBonuspoints(int bonuspoints) {
		this.bonuspoints = bonuspoints;
	}

	/**
	 * Returns if the player is pumping
	 * @return true if isPumping
	 */
	public boolean isPumping() {
		return isPumping;
	}

	/**
	 * Sets if the player is pumping
	 * @param isPumping
	 */
	public void setPumping(boolean isPumping) {
		this.isPumping = isPumping;
	}

	/**
	 * Returns the minus-points.
	 * @return minus-points
	 */
	public int getMinuspoints() {
		return minuspoints;
	}

	/**
	 * Sets the new minus-points
	 * @param minuspoints
	 */
	public void setMinuspoints(int minuspoints) {
		this.minuspoints = minuspoints;
		if(this.minuspoints > 0){
			this.minuspoints = 0;
		}
	}
	
	/**
	 * Compares this player to another by their score.
	 * @return difference between the score
	 */
	@Override
	public int compareTo(Player p) {
		return p.score - score;
	}
	
	/**
	 * Overrides toString and returns the basic informations of the player
	 * @return basic Information: Name InetAddress Score Color
	 */
	@Override
	public String toString() {
		return name + " " + ip + " " + score + " " + color.toString();
	}
}
