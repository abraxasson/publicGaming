package hof.level.objects;

import hof.core.utils.Assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class House {
	private Texture image;
	private float healthpoints;
	private float maxHealth;
	private ArrayList<Pixel> burningArea = new ArrayList<Pixel>();
	private ArrayList<Fire> fireList = new ArrayList<Fire>();

	public House(Texture image, int healthpoints,int fire) {
		super();
		this.image = image;
		this.healthpoints = healthpoints;
		maxHealth = healthpoints;
		this.setBurningArea(177, 177, 176, "TestHouseFire4.png");
		for(int i=0;i<fire;i++){
			Pixel spawnPos = this.getRandomBurningArea();
			this.fireList.add(new Fire(1000,spawnPos));
		}
	}

	public House() {

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
	
	public void setBurningArea(int r, int g, int b, String filename) {
		try {
			BufferedImage img;
			img = ImageIO.read(new File("assets/textures/"+filename));
			Color c = new Color(r,g,b);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void draw(SpriteBatch spriteBatch) {
		if(this.healthpoints > 0){
			spriteBatch.draw(image, 0,0, Assets.CANVAS_WIDTH, Assets.CANVAS_HEIGHT, 0, 0, 1007, 629, false, false);
		}
		else{
			System.out.println("Game Over!");
		}
		for(Fire fire : fireList){
			fire.draw(spriteBatch);
			this.healthpoints -= 1*Gdx.graphics.getDeltaTime();
		}
		
	}

	public float getMaxHealth() {
		return maxHealth;
	}

}
