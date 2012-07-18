package hof.level.objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.*;
import com.badlogic.gdx.graphics.Texture;

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

	public void setBurningArea(Color c, String filename) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File("assets/textures/"+filename));
			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					int rgb = img.getRGB(x, y);
					Color color = new Color(rgb);
					// System.out.println(color.toString()+" "+c.toString()+"  X:"+x+" Y:"+y);
					if (color.toString().equals(c.toString())) {
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

}
