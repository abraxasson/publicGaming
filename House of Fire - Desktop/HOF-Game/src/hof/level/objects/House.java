package hof.level.objects;

import hof.core.utils.Assets;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class House {
	private Texture image;
	private float healthpoints;
	private float maxHealth;
	private ArrayList<Pixel> burningArea = new ArrayList<Pixel>();
	private ArrayList<Fire> fireList = new ArrayList<Fire>();
	
	private boolean isAlive;

	public House(Texture image, int healthpoints,int fire) {
		super();
		this.image = image;
		this.healthpoints = healthpoints;
		isAlive = true;
		maxHealth = healthpoints;
		this.setBurningArea(Assets.houseMap.get(image));
		for(int i=0;i<fire;i++){
			Pixel spawnPos = this.getRandomBurningArea();
			this.fireList.add(new Fire(spawnPos));
		}
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public float getHealthpoints() {
		return healthpoints;
	}

	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}
	
	public ArrayList<Pixel> getBurningArea(){
		return this.burningArea;
	}

	public Pixel getRandomBurningArea(){
		if(!this.burningArea.isEmpty()){
			int length = this.burningArea.size();
			int index = (int)(Math.random()*length);
			Pixel r = (Pixel) burningArea.toArray()[index];
			return r;
		}
		return null;
	}
	
	
	public void setBurningArea(BufferedImage img) {
		Color c = Color.BLACK;
		double verhaeltnisX = ((double)Assets.CANVAS_WIDTH)/((double)img.getWidth());
		double verhaeltnisY = ((double)Assets.CANVAS_HEIGHT)/((double)img.getHeight());
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb);
				if (color.toString().equals(c.toString())) {
					int x1 = (int) (x*verhaeltnisX);
					int y1 = (int) ((img.getHeight()-y)*verhaeltnisY);
					Pixel pixel = new Pixel(x1, y1, color);
					if (burningArea.add(pixel)) {
						//System.out.println("Pixel added-- X:"+x1+" Y:"+y1);
						//System.out.println(color.toString());
					}
				}
			}
		}
	}
	
	public void checkFire(){
		Iterator<Fire> iter = this.fireList.iterator();
		while(iter.hasNext()){
			if(!iter.next().isAlive()){
				iter.remove();
			}
		}
	}
	
	public void draw(SpriteBatch spriteBatch) {
		if(this.healthpoints > 0){
			spriteBatch.draw(image, 0,0, Assets.CANVAS_WIDTH, Assets.CANVAS_HEIGHT, 0, 0, 1007, 629, false, false);
			checkFire();
			for(Fire fire : fireList){
				fire.draw(spriteBatch);
				this.healthpoints -= 1*Gdx.graphics.getDeltaTime();
			}
		}
		else{
			isAlive = false;
			System.out.println("Game Over!");
		}
	}
	
	public boolean getAlive() {
		return isAlive;
	}

	public float getMaxHealth() {
		return maxHealth;
	}
	
	public ArrayList<Fire> getFireList(){
		return this.fireList;
	}

}
