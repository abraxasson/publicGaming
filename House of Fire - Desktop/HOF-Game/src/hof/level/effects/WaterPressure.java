package hof.level.effects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WaterPressure extends AbstractEffect {
	public static final int WATERPRESSUREINC = Settings.waterPressureInc;
	public static long lastUsed;
	
	public WaterPressure(){
		super(SpecialEffectType.WATERPRESSURE, Settings.pressureCooldown);
		this.lifeTime = Settings.waterPressureLifeTime;
		this.active = true;
		lastUsed = System.currentTimeMillis();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		this.lifeTime -= Gdx.graphics.getDeltaTime();
		if(this.lifeTime < 0){
			this.active = false;
		}
		else{
			BitmapFont font = Assets.menu45Font;
			font.setColor(0, 0, 255, 1);
			TextBounds bounds = font.getBounds("Wasserdruck erh�ht!");
			font.draw(spriteBatch, "Wasserdruck erh�ht!", Gdx.graphics.getWidth()/2-bounds.width/2, Gdx.graphics.getHeight()/2-bounds.height/2);
			this.active = true;
		}
	}
	
	public static boolean isReady() {
		if (System.currentTimeMillis() - lastUsed >= cooldown) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void updateLastUsed(){
		lastUsed = System.currentTimeMillis();
	}
	
	public static long getLastUsed(){
		return lastUsed;
	}
}
