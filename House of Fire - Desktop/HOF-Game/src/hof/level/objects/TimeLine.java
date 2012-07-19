package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TimeLine {

	private int height;
	private int width;
	private int maxWidth;
	private float percentage;
	private Texture texture;
	
	public TimeLine() {
		height = Assets.TIMELINE_HEIGHT;
		percentage = 0.5f;
		width = 0;
		maxWidth = Assets.TIMELINE_WIDTH;
		texture = Assets.pureWhiteTextureRegion.getTexture();
	}
	
	private void update() {
		width = (int) (maxWidth * percentage);
	}
	
	public void draw(SpriteBatch spriteBatch) {
		update();
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.GRAY);
		spriteBatch.draw(texture, 0,Gdx.graphics.getHeight() - height, maxWidth, height, 0, 0, 8, 8, false, false);
		spriteBatch.setColor(Color.RED);
		spriteBatch.draw(texture, 0,Gdx.graphics.getHeight() - height, width, height, 0, 0, 8, 8, false, false);
		spriteBatch.setColor(oldColor);
	}
}
