package hof.level.effects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WaterPressure extends AbstractCloud {
	public static final int WATERPRESSUREINC = Settings.waterPressureInc;
	public static long lastUsed;
	
	public WaterPressure(){
		super(Settings.pressureCooldown);
		this.type = AbstractCloud.WATERPRESSURE;
		this.lifeTime = Settings.waterPressureLifeTime;
		lastUsed = System.currentTimeMillis();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		this.lifeTime -= Gdx.graphics.getDeltaTime();
		if(this.lifeTime < 0){
			this.alive = false;
		}
		else{
			BitmapFont font = Assets.menu45Font;
			font.setColor(0, 0, 255, 1);
			TextBounds bounds = font.getBounds("Wasserdruck erhöht!");
			font.draw(spriteBatch, "Wasserdruck erhöht!", Gdx.graphics.getWidth()/2-bounds.width/2, Gdx.graphics.getHeight()/2-bounds.height/2);
		}
	}
	
	public static boolean isReady() {
		if (System.currentTimeMillis() - lastUsed >= cooldown) {
			return true;
		} else {
			return false;
		}
	}
}
