package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Fire {
	private int healthpoints;
	public ParticleEffect flame;
	private int x;
	private int y;
	private Rectangle fireRectangle = new Rectangle();
	
	public Fire(int healthpoints, Pixel p) {
		super();
		this.healthpoints = healthpoints;
		this.flame = Assets.loadFireParticles();
		this.x = p.getX();
		this.y = p.getY();
		flame.setPosition(x, y);
		fireRectangle.setX(x-20);
		fireRectangle.setY(y);
		fireRectangle.setWidth(60);
		fireRectangle.setHeight(100);
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
		flame.setPosition(x, y);
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
		flame.setPosition(x, y);
	}
	
	public void draw(SpriteBatch spriteBatch) {
		flame.draw(spriteBatch, Gdx.graphics.getDeltaTime());
	}
	
}
