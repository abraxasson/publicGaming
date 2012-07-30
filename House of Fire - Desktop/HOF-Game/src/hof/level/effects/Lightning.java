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
	
	public Lightning(){
		super();
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

}
