package hof.player;
import java.net.InetAddress;

import com.badlogic.gdx.graphics.Color;

public class Player {
	private String name;
	private InetAddress ip;
	private int score;
	private Color color;
	
	public Player(String name, InetAddress ip) {
		this.name = name;
		this.ip = ip;
		this.score = 0;
	}
	
	public Player(String name, InetAddress ip, Color color) {
		this.name = name;
		this.ip = ip;
		this.score = 0;
		this.color = color;
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
}
