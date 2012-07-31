package hof.level.effects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractCloud {
	public static final int LIGHTNING = 1;
	public static final int RAIN = 2;
	public static final int WATERPRESSURE = 4;
	protected static float cooldown;
	
	protected int type;
	protected Texture texture;
	protected int x;
	protected int y;
	protected float lifeTime;
	protected boolean alive;
	protected int width;
	protected int height;
	protected int startPos;
	
	protected AbstractCloud(float cooldown){
		this.texture = Assets.cloudTexture;
		this.alive = true;
		this.width = 300;
		this.height = 150;
		this.x = (int)(Math.random()*(Assets.CANVAS_WIDTH-texture.getWidth())-width/2);
		this.y = (int)(Assets.CANVAS_HEIGHT*0.9);
		this.startPos = Assets.CANVAS_WIDTH;
		AbstractCloud.cooldown = cooldown;
	}

	public void draw(SpriteBatch spriteBatch){
		if(this.alive && this.startPos> this.x){
			spriteBatch.draw(texture, startPos, y, width, height);
			startPos -= 200*Gdx.graphics.getDeltaTime();
		}
		else{
			if(this.lifeTime < 0){
				spriteBatch.draw(texture, startPos, y, width, height);
				startPos -= 200*Gdx.graphics.getDeltaTime();
				if(startPos+width <= 0){
					this.alive = false;
				}
			}
			else{
				spriteBatch.draw(texture, x, y, width, height);
				this.lifeTime -= Gdx.graphics.getDeltaTime();
			}
		}
	}
	
	public static float getCooldown() {
		return cooldown;
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
