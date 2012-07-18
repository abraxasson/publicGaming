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

	public House(Texture image, int healthpoints) {
		super();
		this.image = image;
		this.healthpoints = healthpoints;
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
		try {
			img = ImageIO.read(new File("assets/textures/"+filename));
			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					int rgb = img.getRGB(x, y);
					Color color = new Color(rgb);
					Color c = new Color(r,g,b);
					if (color.toString().equals(c.toString())) {
						Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
						double verhaeltnis = d.getWidth()/img.getWidth();
						x = (int) (x*verhaeltnis);
						verhaeltnis = d.getHeight()/img.getHeight();
						y = (int) (y*verhaeltnis);
						Pixel pixel = new Pixel(x, y, color);
						if (burningArea.add(pixel)) {
							System.out.println("Pixel added");
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
	}

}
