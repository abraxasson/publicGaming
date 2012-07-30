package hof.level.effects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractCloud {
	public static final int LIGHTNING = 1;
	public static final int RAIN = 2;
	public static final int WATERPRESSURE = 4;
	protected int type;
	protected Texture texture;
	protected int x;
	protected int y;
	protected float lifeTime;
	protected boolean alive;
	protected int width;
	protected int height;
	
	protected AbstractCloud(){
		this.texture = Assets.cloudTexture;
		this.alive = true;
		this.width = 300;
		this.height = 150;
		this.x = (int)(Math.random()*(Assets.CANVAS_WIDTH-texture.getWidth())-width/2);
		this.y = (int)(Assets.CANVAS_HEIGHT*0.9);
	}

	public void draw(SpriteBatch spriteBatch){
		this.lifeTime -= Gdx.graphics.getDeltaTime();
		if(this.lifeTime < 0){
			this.alive = false;
		}
		else{
			//spriteBatch.draw(texture, x, y);
			spriteBatch.draw(texture, x, y, width, height);
		}
	}
	
	public int getType(){
		return this.type;
	}
	
	public boolean getActive(){
		return this.alive;
	}
	
	public float getLifeTime(){
		return this.lifeTime;
	}
}
