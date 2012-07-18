package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Fire {
	private int healthpoints;
	private ParticleEffect flame;
	private int x;
	private int y;
	
	public Fire(int healthpoints, Texture flame, int x, int y) {
		super();
		this.healthpoints = healthpoints;
		this.flame = Assets.loadFireParticles();
		this.x = x;
		this.y = y;
	}
	
	public int getHealthpoints() {
		return healthpoints;
	}
	
	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
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
