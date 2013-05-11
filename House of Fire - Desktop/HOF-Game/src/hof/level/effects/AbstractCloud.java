package hof.level.effects;

import hof.core.utils.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractCloud extends AbstractEffect {
	protected Texture texture;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int texturePosition;
	
	protected AbstractCloud(final SpecialEffectType type, final float cooldown) {
		super(type, cooldown);
		this.texture = Assets.cloudTexture;
		this.x = (int)(Math.random()*(Assets.CANVAS_WIDTH-texture.getWidth())-width/2);
		this.y = (int)(Assets.CANVAS_HEIGHT*0.9);
		this.width = 300;
		this.height = 150;
		this.texturePosition = Assets.CANVAS_WIDTH;
	}
	
	@Override
	public void draw(final SpriteBatch spriteBatch){
		if(this.active && this.texturePosition > this.x){
			spriteBatch.draw(texture, texturePosition, y, width, height);
			texturePosition -= 200 * Gdx.graphics.getDeltaTime();
		}
		else{
			if(this.lifeTime <= 0){
				Assets.rain.stop();
				spriteBatch.draw(texture, texturePosition, y, width, height);
				texturePosition -= 200*Gdx.graphics.getDeltaTime();
				if(texturePosition + width <= 0){
					this.active = false;
				}
			}
			else{
				spriteBatch.draw(texture, x, y, width, height);
				this.lifeTime -= Gdx.graphics.getDeltaTime();
			}
		}
	}
}
