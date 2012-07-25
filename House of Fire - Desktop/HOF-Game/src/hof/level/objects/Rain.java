package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Rain extends AbstractCloud {
	private ParticleEffect shower;
	private Pixel burningSpot;
	
	public Rain(Pixel burningSpot){
		super();
		this.setBurningSpot(burningSpot);
		this.x = burningSpot.getX();
		this.shower = Assets.loadWaterParticles();
	}
	
	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		shower.draw(spriteBatch,Gdx.graphics.getDeltaTime());
	}

	public Pixel getBurningSpot() {
		return burningSpot;
	}

	public void setBurningSpot(Pixel burningSpot) {
		this.burningSpot = burningSpot;
	}
}
