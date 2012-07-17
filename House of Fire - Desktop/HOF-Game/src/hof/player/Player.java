package hof.player;
import java.net.InetAddress;

public class Player {
	private String name;
	private InetAddress ip;
	private int score;
	
	public Player(String name, InetAddress ip) {
		this.name = name;
		this.ip = ip;
		this.score = 0;
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
}
