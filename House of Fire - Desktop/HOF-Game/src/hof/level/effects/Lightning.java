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
		super(Settings.lightningCooldown);
		this.flash = Assets.lightningTexture;
		this.x = Assets.FRAME_WIDTH * 2;
		this.type = AbstractCloud.LIGHTNING;
		this.lifeTime = Settings.lightningLifeTime;
		hotSpot = null;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		this.lifeTime -= Gdx.graphics.getDeltaTime();
		if(this.lifeTime < 0){
			this.alive = false;
			Assets.thunder.stop();
			lastUsed = System.currentTimeMillis();
		}
		else{
			spriteBatch.draw(flash, hotSpot.getX(), hotSpot.getY(), 150, 900);
		}
	}
	
	public void setHotSpot(Pixel hotSpot) {
		this.hotSpot = hotSpot;
		this.x = hotSpot.getX()+flash.getWidth();
	}
	
	public Pixel getHotSpot(){
		return this.hotSpot;
	}
	
	public static long getLastUsed(){
		return lastUsed;
	}
	
	public static void setLastUsed(long newLastUsed){
		lastUsed = newLastUsed;
	}
	
	public static boolean isReady() {
		if (System.currentTimeMillis() - lastUsed >= cooldown) {
			return true;
		} else {
			return false;
		}
	}

}
