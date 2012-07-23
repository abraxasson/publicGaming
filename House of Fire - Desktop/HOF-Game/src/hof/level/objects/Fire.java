package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Fire {
	private int healthpoints;
	public ParticleEffect flame;
	private int x;
	private int y;
	private Rectangle fireRectangle = new Rectangle();
	private ParticleEmitter emitter;
	
	public Fire(int healthpoints, Pixel p) {
		super();
		this.healthpoints = healthpoints;
		this.flame = Assets.loadFireParticles();
		this.x = p.getX();
		this.y = p.getY();
		flame.setPosition(x, y);
		emitter = flame.getEmitters().get(0);
		fireRectangle.setX(x-20);
		fireRectangle.setY(y);
		fireRectangle.setWidth(50);
		fireRectangle.setHeight(50);
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
		update();
		flame.draw(spriteBatch, Gdx.graphics.getDeltaTime());
		//spriteBatch.draw(Assets.pureWhiteTextureRegion, x-20, y,emitter.getLife().getLowMin()/6,emitter.getLife().getLowMin()/6);
	}
	
	public void update(){
		fireRectangle.setX(x-20);
		fireRectangle.setY(y);
		fireRectangle.setWidth(emitter.getLife().getLowMin()/6);
		fireRectangle.setHeight(emitter.getLife().getLowMin()/6);
	}
	
	public Rectangle getFireRectangle(){
		return this.fireRectangle;
	}
	
}
