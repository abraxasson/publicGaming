package hof.core.view;

import hof.core.utils.Assets;
import hof.level.objects.House;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TimeLine {

	private int height;
	private int width;
	private int maxWidth;
	private float percentage;
	private Texture texture;
	private int offset;
	private int borderOff;
	private TextureRegion running;
	private float stateTime;
	
	public TimeLine() {
		height = Assets.TIMELINE_HEIGHT;
		percentage = 0.5f;
		width = 0;
		offset = Assets.TIMELINE_OFFSET;
		maxWidth = Assets.TIMELINE_WIDTH;
		texture = Assets.timeLineTexture;
		borderOff = 2;
		stateTime = 0;
	}
	
	public void draw(SpriteBatch spriteBatch, House house) {
		percentage = 1-(house.getHealthpoints()/house.getMaxHealth());
		width = (int) (maxWidth * percentage);

		stateTime += Gdx.graphics.getDeltaTime();
		running = Assets.runningAnimation2.getKeyFrame(stateTime, true);
		
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.BLACK);
		spriteBatch.draw(texture, offset - borderOff,Gdx.graphics.getHeight() - height - offset - borderOff, maxWidth + (borderOff*2), height +  (borderOff*2));
		spriteBatch.setColor(Color.GRAY);
		spriteBatch.draw(texture, offset,Gdx.graphics.getHeight() - height - offset, maxWidth, height);
		spriteBatch.setColor(Color.BLACK);
		spriteBatch.draw(texture, offset - borderOff,Gdx.graphics.getHeight() - height - offset - borderOff, width + (borderOff*2), height +  (borderOff*2));
		spriteBatch.draw(running, width + 20, Gdx.graphics.getHeight() - height - offset);
		spriteBatch.setColor(Color.RED);
		spriteBatch.draw(texture, offset,Gdx.graphics.getHeight() - height - offset, width , height);
		spriteBatch.setColor(oldColor);
	}
}
