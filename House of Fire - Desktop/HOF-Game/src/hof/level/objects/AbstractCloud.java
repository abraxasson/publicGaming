package hof.level.objects;

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
	
	protected AbstractCloud(){
		this.texture = Assets.cloudTexture;
		this.x = (int)(Math.random()*(Assets.CANVAS_WIDTH-texture.getWidth()));
		this.y = (int)(Assets.CANVAS_HEIGHT*0.9);
		this.alive = true;
	}

	public void draw(SpriteBatch spriteBatch){
		this.lifeTime -= Gdx.graphics.getDeltaTime();
		if(this.lifeTime < 0){
			this.alive = false;
		}
		else{
			spriteBatch.draw(texture, x, y);
		}
	}
	
	protected int getX(){
		return this.x;
	}
	
	protected void setX(int x){
		this.x = x;
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
