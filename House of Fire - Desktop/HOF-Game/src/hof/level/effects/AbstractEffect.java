package hof.level.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractEffect {
	protected static float cooldown;
	
	private SpecialEffectType type;
	
	protected float lifeTime;
	protected boolean active;
	
	protected AbstractEffect(final SpecialEffectType type, final float cooldown){
		this.type = type;
		this.active = true;
		AbstractEffect.cooldown = cooldown;
	}

	public abstract void draw(SpriteBatch spriteBatch);
	
	protected float getCooldown() {
		return cooldown;
	}
	
	public SpecialEffectType getType(){
		return this.type;
	}
	
	public boolean getActive(){
		return this.active;
	}
	
	public float getLifeTime(){
		return this.lifeTime;
	}
}
