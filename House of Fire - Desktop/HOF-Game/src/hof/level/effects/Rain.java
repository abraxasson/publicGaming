package hof.level.effects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;
import hof.level.objects.Pixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Rain extends AbstractCloud {
	private ParticleEffect shower;
	private Pixel burningSpot;
	private ParticleEmitter emitter;
	private static long lastUsed;
	private boolean onPosition;
	private int count = 0;
	
	public Rain(){
		super(Settings.rainCooldown);
		this.x = Assets.FRAME_WIDTH * 2;
		burningSpot = null;
		this.shower = Assets.loadRainParticles();
		this.type = AbstractCloud.RAIN;
		emitter = shower.getEmitters().get(0);
		this.lifeTime = Settings.rainLifeTime;
		lastUsed = System.currentTimeMillis();
		this.onPosition = false;
	}
	
	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		if(this.startPos <= super.x && this.lifeTime > 0){
			if(count == 0){
				this.lifeTime = Settings.rainLifeTime;
				count++;
			}
			emitter.setPosition(this.x, this.y);
			shower.draw(spriteBatch,Gdx.graphics.getDeltaTime());
			this.onPosition = true;
		}
	}

	public Pixel getBurningSpot() {
		return burningSpot;
	}
	
	public static void setLastUsed(long newLastUsed){
		lastUsed = newLastUsed;
	}

	public void setBurningSpot(Pixel burningSpot) {
		this.burningSpot = burningSpot;
		this.x = burningSpot.getX()-this.width/2;
	}
	
	public static boolean isReady() {
		if (System.currentTimeMillis() - lastUsed >= cooldown) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOnPosition() {
		return onPosition;
	}

}
