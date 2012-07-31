package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NonPlayable extends AbstractPerson{
	private int healthpoints;
	private int startPos;
	
	public NonPlayable(Texture body, int x, int y, int healthpoints) {
		this(body, x, y, 20, 20, healthpoints);
		this.startPos = Assets.CANVAS_WIDTH;
	}
	
	public NonPlayable(Texture body, int x, int y, int width, int height, int healthpoints) {
		super(body, x, y, width, height);
		this.healthpoints = healthpoints;
		this.startPos = Assets.CANVAS_WIDTH;
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
			startPos -= 150*Gdx.graphics.getDeltaTime()*((int)(Math.random()*5));
			int zz = (int)(Math.random()*10);
			if(zz>8){
				this.setY(this.getY()+5);
			}
			else if(zz<2){
				this.setY(this.getY()-5);
			}
			if(this.getY()<= 100){
				this.setY(100);
			}
			if(this.getY()>=200){
				this.setY(200);
			}
		}
		else{
			this.startPos = Assets.CANVAS_WIDTH;
		}
	}
	
}
