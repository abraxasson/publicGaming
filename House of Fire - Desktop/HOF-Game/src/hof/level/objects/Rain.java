package hof.level.objects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Rain extends AbstractCloud {
	private ParticleEffect shower;
	private Pixel burningSpot;
	private ParticleEmitter emitter;
	
	public Rain(Pixel burningSpot){
		super();
		this.x = burningSpot.getX()-this.width/2;
		this.setBurningSpot(burningSpot);
		this.shower = Assets.loadRainParticles();
		this.type = AbstractCloud.RAIN;
		emitter = shower.getEmitters().get(0);
		this.lifeTime = Settings.rainLifeTime;
	}
	
	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		emitter.setPosition(this.x, this.y);
		shower.draw(spriteBatch,Gdx.graphics.getDeltaTime());
	}

	public Pixel getBurningSpot() {
		return burningSpot;
	}

	public void setBurningSpot(Pixel burningSpot) {
		this.burningSpot = burningSpot;
	}

}
