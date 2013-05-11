package hof.level.effects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;
import hof.level.objects.Pixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Rain extends AbstractCloud {
	private ParticleEffect rainShower;
	private Pixel burningSpot;
	private ParticleEmitter rainEmitter;
	private static long lastUsed;
	private boolean onPosition;
	private int count = 0;
	
	public Rain(){
		super(SpecialEffectType.RAIN, Settings.rainCooldown);
		this.x = Assets.FRAME_WIDTH * 2;
		burningSpot = null;
		this.rainShower = Assets.loadRainParticles();
		rainEmitter = rainShower.getEmitters().get(0);
		this.lifeTime = Settings.rainLifeTime;
		this.onPosition = false;
	}
	
	@Override
	public void draw(final SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		if(this.texturePosition <= super.x && this.lifeTime > 0){
			if(count == 0){
				this.lifeTime = Settings.rainLifeTime;
				//TODO organize the sound / started here stopped in AbstractCloud
				Assets.rain.play();
				count++;
			}
			rainEmitter.setPosition(this.x, this.y);
			rainShower.draw(spriteBatch,Gdx.graphics.getDeltaTime());
			this.onPosition = true;
		}
	}

	public Pixel getBurningSpot() {
		return burningSpot;
	}
	
	public static void updateLastUsed(){
		lastUsed = System.currentTimeMillis();
	}

	public void setBurningSpot(final Pixel burningSpot) {
		this.burningSpot = burningSpot;
		this.x = burningSpot.getX()-this.width/2;
	}
	
	public static boolean isReady() {
		return System.currentTimeMillis() - lastUsed >= cooldown;
	}

	public boolean isOnPosition() {
		return onPosition;
	}
	
	public static long getLastUsed(){
		return lastUsed;
	}
}