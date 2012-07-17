package hof.level.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;

public class House {
	private Texture image;
	private int healthpoints;
	private Polygon burningArea;
	
	public House(Texture image, int healthpoints, Polygon burningArea) {
		super();
		this.image = image;
		this.healthpoints = healthpoints;
		this.burningArea = burningArea;
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public int getHealthpoints() {
		return healthpoints;
	}

	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}

	public Polygon getBurningArea() {
		return burningArea;
	}

	public void setBurningArea(Polygon burningArea) {
		this.burningArea = burningArea;
	}
	
}
