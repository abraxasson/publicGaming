package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Fire {
	private float healthpoints;
	private ParticleEffect flame;
	private int x;
	private int y;
	private Rectangle fireRectangle = new Rectangle();
	private ParticleEmitter emitter;
	private boolean alive;

	public Fire(Pixel p) {
		super();
		this.flame = Assets.loadFireParticles();
		this.x = p.getX();
		this.y = p.getY();
		this.alive = true;
		flame.setPosition(x, y);
		emitter = flame.getEmitters().get(0);
		this.healthpoints = emitter.getEmission().getHighMax();
		fireRectangle.setX(x - 20);
		fireRectangle.setY(y);
		fireRectangle.setWidth(emitter.getSpawnWidth().getHighMin());
		fireRectangle.setHeight(emitter.getSpawnWidth().getHighMin());
	}

	public float getHealthpoints() {
		return healthpoints;
	}

	public void setHealthpoints(float healthpoints) {
		this.healthpoints = healthpoints;
		emitter.getEmission().setHigh(healthpoints);
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
		//spriteBatch.draw(Assets.pureWhiteTextureRegion, x-20,
		//y,emitter.getLife().getLowMin()/6,emitter.getLife().getLowMin()/6);
	}

	public void update() {
		fireRectangle.setX(x - 20);
		fireRectangle.setY(y);
		fireRectangle.setWidth(emitter.getLife().getLowMin() / 6);
		fireRectangle.setHeight(emitter.getLife().getLowMin() / 6);
		
		if(this.healthpoints <= 0){
			this.alive = false;
		}
	}

	public Rectangle getFireRectangle() {
		return this.fireRectangle;
	}

	public ParticleEffect getFlame() {
		return this.flame;
	}

	public boolean isAlive() {
		return alive;
	}
}
