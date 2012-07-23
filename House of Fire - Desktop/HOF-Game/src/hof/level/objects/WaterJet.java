package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WaterJet {

	private ParticleEffect waterJet;
	private ParticleEmitter emmitter;
	
	public WaterJet() {
		waterJet = Assets.loadWaterParticles();
		emmitter = waterJet.getEmitters().get(0);
	}
	
	public void setPosition(float x, float y) {
		waterJet.setPosition(x, y);
	}
	
	public void setStrength(int change) {
		float strength = emmitter.getLife().getHighMax();
		strength += change * Gdx.graphics.getDeltaTime();
		emmitter.getLife().setHigh(strength);
		emmitter.getLife().setHighMin(strength-change);
	}
	
	public void setAngle(int val ) {
		float angle = emmitter.getAngle().getLowMin();
		angle += val * Gdx.graphics.getDeltaTime();
		emmitter.getAngle().setLow(angle);
		emmitter.getAngle().setHigh(angle - 10, angle + 10);
	}
	
	public void draw (SpriteBatch spriteBatch) {
		waterJet.draw(spriteBatch, Gdx.graphics.getDeltaTime());
	}
}
