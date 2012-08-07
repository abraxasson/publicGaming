package hof.level.objects;

import hof.core.PlayingScreen;
import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class NonPlayable extends AbstractPerson{
	private float healthpoints;
	private int startPos;
	private Animation animation;
	private TextureRegion running;
	private Rectangle position;
	private float stateTime;
	
	//FIXME Gag as own class
	
	public NonPlayable(int x, int y, int healthpoints, Animation animation) {
		this(Assets.pureWhiteTexture, x, y, 20, 20, healthpoints);
		this.startPos = Assets.CANVAS_WIDTH;
		this.animation = animation;
		this.setPosition(new Rectangle());
		this.startPos = - 300;
	}
	
	public NonPlayable(Texture body, int x, int y, int width, int height, int healthpoints) {
		super(body, x, y, width, height);
		this.healthpoints = healthpoints;
		this.startPos = 0 - width;
		this.setPosition(new Rectangle());
	}

	public float getHealthpoints() {
		return healthpoints;
	}

	public void setHealthpoints(float f) {
		this.healthpoints = f;
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
		if(this.healthpoints > 0){
			if(this.startPos < Assets.CANVAS_WIDTH){
				stateTime += Gdx.graphics.getDeltaTime(); 
				this.running = animation.getKeyFrame(stateTime,true);
				updatePosition();
				spriteBatch.draw(this.running, startPos, this.getY());
				startPos += 150*Gdx.graphics.getDeltaTime();
			}
			else{
				this.healthpoints = -100;
				PlayingScreen.setRandomTime();
			}
		}
		
	}

	private void updatePosition(){
		this.position.setWidth(this.running.getRegionWidth());
		this.position.setHeight(this.running.getRegionHeight()/2);
		this.position.setX(this.startPos);
		this.position.setY(this.getY()+this.position.getHeight());
	}
	
	public Rectangle getPosition() {
		return position;
	}

	private void setPosition(Rectangle position) {
		this.position = position;
	}
	
}
