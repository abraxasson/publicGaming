package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NonPlayable extends AbstractPerson{
	private int healthpoints;
	private int startPos;
	private Animation animation;
	private TextureRegion running;
	private float stateTime;
	
	public NonPlayable(int x, int y, int healthpoints, Animation animation) {
		this(Assets.pureWhiteTexture, x, y, 20, 20, healthpoints);
		this.startPos = Assets.CANVAS_WIDTH;
		this.animation = animation;
	}
	
	public NonPlayable(Texture body, int x, int y, int width, int height, int healthpoints) {
		super(body, x, y, width, height);
		this.healthpoints = healthpoints;
		this.startPos = 0 - width;
	}

	public int getHealthpoints() {
		return healthpoints;
	}

	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}
	
	public void draw(SpriteBatch spriteBatch){
		if(this.startPos + this.getWidth() > 0){
			spriteBatch.draw(this.getBody(), startPos, this.getY(), this.getWidth(), this.getHeight());
			startPos -= 150*Gdx.graphics.getDeltaTime();
		}
		else{
			this.startPos = Assets.CANVAS_WIDTH;
		}
	}
	
	public void drawAnimation(SpriteBatch spriteBatch){
		if(this.startPos < Assets.CANVAS_WIDTH){
			stateTime += Gdx.graphics.getDeltaTime();
			this.running = animation.getKeyFrame(stateTime,true);
			spriteBatch.draw(this.running, startPos, this.getY());
			startPos += 150*Gdx.graphics.getDeltaTime();
		}
		else{
			this.startPos = 0 - this.getWidth();
		}
	}
	
}
