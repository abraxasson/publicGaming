package hof.level.objects;

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
	private int healthpoints;
	private ArrayList<Pixel> burningArea = new ArrayList<Pixel>();
	Fire fire;
	Fire fire2;

	public House(Texture image, int healthpoints) {
		super();
		this.image = image;
		this.healthpoints = healthpoints;
		this.setBurningArea(0, 0, 0, "TestHouseFire4.png");
		fire = new Fire(1000,this.getRandomBurningArea());
		fire2 = new Fire(1000,this.getRandomBurningArea());
	}

	public House() {

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
		BufferedImage img;
		Color c = new Color(r,g,b);
		try {
			img = ImageIO.read(new File("assets/textures/"+filename));
			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					int rgb = img.getRGB(x, y);
					Color color = new Color(rgb);
					if (color.toString().equals(c.toString())) {
						double verhaeltnis = Gdx.graphics.getWidth()/img.getWidth();
						int x1 = (int) (x*verhaeltnis);
						verhaeltnis = Gdx.graphics.getHeight()/img.getHeight();
						int y1 = (int) (y*verhaeltnis);
						Pixel pixel = new Pixel(x1, y1, color);
						if (burningArea.add(pixel)) {
							System.out.println("Pixel added-- X:"+x1+" Y:"+y1);
							System.out.println(color.toString());
							//System.out.println(x+" "+y);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(SpriteBatch spriteBatch) {
		spriteBatch.draw(image, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1007, 629, false, false);
		fire.draw(spriteBatch);
		fire2.draw(spriteBatch);
	}

}
