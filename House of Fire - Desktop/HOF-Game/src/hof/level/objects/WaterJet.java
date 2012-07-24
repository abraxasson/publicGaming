package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class WaterJet {

	private ParticleEffect waterJet;
	private ParticleEmitter emmitter;
	private Rectangle streamArea;
	private float size;
	private float x;
	private float y;
	
	public WaterJet() {
		waterJet = Assets.loadWaterParticles();
		emmitter = waterJet.getEmitters().get(0);
		
		emmitter.getLife().getHighMin();
		emmitter.getAngle().getLowMin();
		
		size = emmitter.getSpawnWidth().getHighMin();
		streamArea = new Rectangle();
		streamArea.setX(calcX());
		streamArea.setY(calcY());
		
		streamArea.setWidth(size);
		streamArea.setHeight(size);
	}
	
	public void setPosition(float x, float y) {
		waterJet.setPosition(x, y);
		this.x = x;
		this.y = y;
	}
	
	public void setStrength(int change) {
		float strength = emmitter.getLife().getHighMax();
		//strength += change * Gdx.graphics.getDeltaTime();
		strength += change;
		emmitter.getLife().setHigh(strength);
		emmitter.getLife().setHighMin(strength - change);
	}
	
	public void setAngle(int val ) {
		float angle = emmitter.getAngle().getLowMin();
		//angle += val * Gdx.graphics.getDeltaTime();
		angle += val;
		emmitter.getAngle().setLow(angle);
		emmitter.getAngle().setHigh(angle - 10, angle + 10);
	}
	
	public void changeDiameter(float diameter) {
		size = diameter;
		updateRectangle();
	}
	
	private int calcX() {
		int x = 0;
		float length = emmitter.getLife().getHighMin() * 3/4;
		float angle = emmitter.getAngle().getLowMin();
		if (angle == 90 || angle == 270) {
			x = (int) this.x;
		} else {
			x =(int) (this.x + (Math.cos(Math.toRadians(angle)) * length));
		}
		
		x -= size / 2; 

		return x;
	}
	
	private int calcY() {
		int y = 0;
		float length = emmitter.getLife().getHighMin() * 3/4;
		float angle = emmitter.getAngle().getLowMin();
		if (angle == 180 || angle == 360) {
			y = (int) this.y;
		} else {
			y =(int) (this.y +  (Math.sin(Math.toRadians(angle)) * length));
		}
		
		y -= size / 2;
		
		return y;
	}
	
	private void updateRectangle() {
		streamArea.setX(calcX());
		streamArea.setY(calcY());
		streamArea.setWidth(size);
		streamArea.setHeight(size);
//		System.out.println(streamArea);
	}
	
	public Rectangle getStreamArea() {
		return streamArea;
	}
	
	public void draw (SpriteBatch spriteBatch) {
		updateRectangle();
		waterJet.draw(spriteBatch, Gdx.graphics.getDeltaTime());
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.BLACK);
		//spriteBatch.draw(Assets.pureWhiteTextureRegion.getTexture(), streamArea.x, streamArea.y, streamArea.width, streamArea.height, 0, 0, 8, 8, false, false);
		spriteBatch.setColor(oldColor);
	}
}
