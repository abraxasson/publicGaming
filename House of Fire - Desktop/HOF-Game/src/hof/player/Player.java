package hof.player;
import java.net.InetAddress;

import com.badlogic.gdx.graphics.Color;

public class Player implements Comparable<Player> {
	private String name;
	private InetAddress ip;
	private int score;
	private Color color;
	private boolean isAlive;
	private boolean isPumping;
	private long lastInput;
	private int bonuspoints;
	private int minuspoints;
	
	public Player(String name, InetAddress ip) {
		this(name, ip, Color.WHITE);
	}
	
	public Player(String name, InetAddress ip, Color color) {
		this.name = name;
		this.ip = ip;
		this.score = 0;
		this.color = color;
		isAlive = true;
		setPumping(false);
	}
	
	public String getName() {
		return name;
	}
	public InetAddress getIp() {
		return ip;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return name + " " + ip + " " + score;
	}

	public void incScore() {
		score += 10;		
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean getAlive() {
		return isAlive;
	}

	public void setAlive(boolean alive) {
		isAlive = alive;
		
	}

	public long getLastInput() {
		return lastInput;
	}

	public void setLastInput(long lastInput) {
		this.lastInput = lastInput;
	}

	public int getBonuspoints() {
		return bonuspoints;
	}

	public void setBonuspoints(int bonuspoints) {
		this.bonuspoints = bonuspoints;
	}

	public boolean isPumping() {
		return isPumping;
	}

	public void setPumping(boolean isPumping) {
		this.isPumping = isPumping;
	}

	@Override
	public int compareTo(Player p) {
		return p.score - score;
	}

	public int getMinuspoints() {
		return minuspoints;
	}

	public void setMinuspoints(int minuspoints) {
		this.minuspoints = minuspoints;
		if(this.minuspoints > 0){
			this.minuspoints = 0;
		}
	}
}
