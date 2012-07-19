package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StatusBar {

	private Texture texture;
	private int x;
	private int y;
	
	public StatusBar()  {
		texture = Assets.pureWhiteTextureRegion.getTexture();
		x = Assets.TIMELINE_WIDTH;
		y= 0;
	}
	
	public void draw (SpriteBatch spriteBatch) {
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(texture, x, y, Assets.STATUS_BAR_WIDTH, Assets.STATUS_BAR_HEIGHT, 0, 0, 8, 8, false, false);
		spriteBatch.setColor(oldColor);
	}
}
