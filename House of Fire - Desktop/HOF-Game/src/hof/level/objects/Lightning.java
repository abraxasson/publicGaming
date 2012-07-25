package hof.level.objects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Lightning extends AbstractCloud {
	private Texture flash;
	private Pixel hotSpot;
	
	public Lightning(Pixel hotSpot){
		super();
		this.flash = Assets.lightningTexture;
		this.hotSpot = hotSpot;
		this.x = hotSpot.getX()+flash.getWidth();
		this.type = AbstractCloud.LIGHTNING;
		this.lifeTime = Settings.lightningLifeTime;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		spriteBatch.draw(flash,hotSpot.getX(),hotSpot.getY());
	}
	
	public Pixel getHotSpot(){
		return this.hotSpot;
	}

}
