package hof.level.objects;

import com.badlogic.gdx.graphics.Texture;

public class NonPlayable extends AbstractPerson{
	private int healthpoints;
	
	public NonPlayable(Texture body, int x, int y, int healthpoints) {
		super(body, x, y);
		this.healthpoints = healthpoints;
	}

	public int getHealthpoints() {
		return healthpoints;
	}

	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}
	
}
