package hof.level.effects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;
import hof.level.objects.Pixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Lightning extends AbstractCloud {
	private Texture flash;
	private Pixel hotSpot;
	private static long lastUsed;
	
	public Lightning(){
		super(SpecialEffectType.LIGHTNING, Settings.lightningCooldown);
		this.flash = Assets.lightningTexture;
		this.x = Assets.FRAME_WIDTH * 2;
		this.lifeTime = Settings.lightningLifeTime;
		hotSpot = null;
	}
	
	@Override
	public void draw(final SpriteBatch spriteBatch){
		//TODO why is there no cloud
		this.lifeTime -= Gdx.graphics.getDeltaTime();
		if(this.lifeTime < 0){
			this.active = false;
			//TODO sound stopped here and started in PlayingScreen
			Assets.thunder.stop();
			lastUsed = System.currentTimeMillis();
		}
		else{
			spriteBatch.draw(flash, hotSpot.getX(), hotSpot.getY(), 150, 900);
		}
	}
	
	public void setHotSpot(final Pixel hotSpot) {
		this.hotSpot = hotSpot;
		this.x = hotSpot.getX() + flash.getWidth();
	}
	
	public Pixel getHotSpot(){
		return this.hotSpot;
	}
	
	public static long getLastUsed(){
		return lastUsed;
	}
	
	public static void updateLastUsed(){
		lastUsed = System.currentTimeMillis();
	}
	
	public static boolean isReady() {
		return System.currentTimeMillis() - lastUsed >= cooldown;
	}

}
