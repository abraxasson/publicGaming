package hof.level.objects;

import com.badlogic.gdx.graphics.Texture;

public class Person {
	private Texture body;
	private int healthpoints;
	
	
	public Person(Texture body, int healthpoints) {
		super();
		this.body = body;
		this.healthpoints = healthpoints;
	}
	
	public Texture getBody() {
		return body;
	}
	
	public void setBody(Texture body) {
		this.body = body;
	}
	
	public int getHealthpoints() {
		return healthpoints;
	}
	
	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}
	
	
}
