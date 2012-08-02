package hof.level.objects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;

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
	private ArrayList<Pixel> burningArea;
	private ArrayList<Pixel> fullscreenBurningArea; 
	private ArrayList<Fire> fireList;
	private int fireCount;
	
	private boolean isAlive;

	public House(Texture image, int fire) {
		super();
		this.image = image;
		this.healthpoints = Settings.houseHealthpoints;
		fireCount = fire;
		isAlive = true;
		maxHealth = healthpoints;
		
		burningArea = new ArrayList<Pixel>();
		fullscreenBurningArea = new ArrayList<Pixel>();
		fireList = new ArrayList<Fire>();
		
		resetHouse();
	}
	
	public void resetHouse() {
		this.fireList.clear();
		setBurningArea(Assets.houseMap.get(image));
		for(int i = 0 ; i < fireCount; i++){
			Pixel spawnPos = this.getRandomBurningArea();
			this.fireList.add(new Fire(spawnPos));
		}
		healthpoints = maxHealth;
		isAlive = true;
	}
	
	public void prepareFullscreen() {
		this.fireList.clear();
		setRandomBurningArea(Assets.houseMap.get(image));
		for(int i = 0 ; i < fireCount; i++){
			Pixel spawnPos = this.getRandomFullscreenBurningArea();
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
	
	public Pixel getRandomFullscreenBurningArea(){
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
					burningArea.add(pixel);
				}
			}
		}
	}
	
	public void setRandomBurningArea(BufferedImage img) {
		Color c = Color.BLACK;
		double verhaeltnisX = ((double)Assets.FRAME_WIDTH)/((double)img.getWidth());
		double verhaeltnisY = ((double)Assets.FRAME_HEIGHT)/((double)img.getHeight());
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb);
				if (color.toString().equals(c.toString())) {
					int x1 = (int) (x*verhaeltnisX);
					int y1 = (int) ((img.getHeight()-y)*verhaeltnisY);
					Pixel pixel = new Pixel(x1, y1, color);
					fullscreenBurningArea.add(pixel);
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
			spriteBatch.draw(image, 0,0, Assets.CANVAS_WIDTH, Assets.FRAME_HEIGHT);
			checkFire();
			for(Fire fire : fireList){
				fire.draw(spriteBatch);
				this.healthpoints -= Settings.fireDamage  * Gdx.graphics.getDeltaTime();
			}
		}
		else{
			isAlive = false;
		}
	}
	
	public void drawFullscreen(SpriteBatch spriteBatch) {
		spriteBatch.draw(image, 0,0, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT);
		for(Fire fire : fireList){
			fire.drawFullscreen(spriteBatch);
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
	
	public void destroy(){
		this.healthpoints = 0;
	}

}
