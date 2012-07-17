package hof.level.objects;

import com.badlogic.gdx.graphics.Texture;

public class Fire {
	private int healthpoints;
	private Texture flame;
	private int x;
	private int y;
	
	public Fire(int healthpoints, Texture flame, int x, int y) {
		super();
		this.healthpoints = healthpoints;
		this.flame = flame;
		this.x = x;
		this.y = y;
	}
	
	public int getHealthpoints() {
		return healthpoints;
	}
	
	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}
	
	public Texture getFlame() {
		return flame;
	}
	
	public void setFlame(Texture flame) {
		this.flame = flame;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
}
