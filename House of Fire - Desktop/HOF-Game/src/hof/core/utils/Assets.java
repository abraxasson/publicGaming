package hof.core.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	public static final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int STATUS_BAR_HEIGHT = (int) d.getHeight();
	public static final int STATUS_BAR_WIDTH = (int) (d.getWidth() / 6);
	
	public static final int TIMELINE_WIDTH = (int) (d.getWidth() - STATUS_BAR_WIDTH);
	public static final int TIMELINE_HEIGHT = (int) (d.getHeight() / 15);
	
	public static final int CANVAS_HEIGHT = (int) (d.getHeight() - TIMELINE_HEIGHT);
	public static final int CANVAS_WIDTH = TIMELINE_WIDTH;
	
	public static final int RANKING_HEIGHT = STATUS_BAR_HEIGHT / 2;
	
	public static final int SMSBAR_HEIGHT = STATUS_BAR_HEIGHT / 2;
	
	private static TextureAtlas atlas;
	public static TextureRegion pureWhiteTextureRegion;
	public static Texture firefighter_left;
	public static Texture firefighter_right;
	public static Texture mainMenu;
	
	public static Map<Texture, BufferedImage> houseMap;
	public static Texture houseTexture;
	public static BufferedImage houseImage;
	public static Texture house2Texture;
	public static BufferedImage house2Image;
	
	public static Texture cloudTexture;
	public static Texture lightningTexture;
	
	public static Sound buttonSound;
	
	public static BitmapFont defaultFont;
	public static BitmapFont text30Font;
	public static BitmapFont text45Font;
	public static BitmapFont text50Font;
	public static BitmapFont menu45Font;
	
	public static void load() {
		String textureDir = "textures";
		String textureFile = textureDir + "/pack";
		atlas = new TextureAtlas(Gdx.files.internal(textureFile), Gdx.files.internal(textureDir));
		houseMap = new HashMap<Texture, BufferedImage>();
		loadTextures();
		loadHouses();
		createAnimations();
		loadSounds();
		loadFonts();
	}

	public static ParticleEffect loadWaterParticles() {
		ParticleEffect waterParticleEffect = new ParticleEffect();
		waterParticleEffect.load(Gdx.files.internal("particles/waterjet2.p"), Gdx.files.internal("particles"));
		return waterParticleEffect;
	}
	
	public static ParticleEffect loadRainParticles(){
		ParticleEffect rainParticleEffect = new ParticleEffect();
		rainParticleEffect.load(Gdx.files.internal("particles/rain.p"), Gdx.files.internal("paricles"));
		return rainParticleEffect;
	}
	
	public static ParticleEffect loadFireParticles() {
		ParticleEffect fireParticleEffect = new ParticleEffect();
		fireParticleEffect.load(Gdx.files.internal("particles/fire.p"), Gdx.files.internal("particles"));
		return fireParticleEffect;
	}

	private static void createAnimations() {
		
	}

	private static void loadTextures() {
		pureWhiteTextureRegion = atlas.findRegion("8x8");
		firefighter_left = new Texture(Gdx.files.internal("textures/firefighter_left.png"));
		firefighter_right = new Texture(Gdx.files.internal("textures/firefighter_right.png"));
		mainMenu = new Texture(Gdx.files.internal("textures/mainMenu.png"));
		cloudTexture = new Texture(Gdx.files.internal("textures/cloud.png"));
		lightningTexture = new Texture(Gdx.files.internal("textures/lightning.png"));
	}
	
	private static void loadHouses() {
		try {
			houseTexture = new Texture(Gdx.files.internal("textures/TestHouse.png"));
			house2Texture = new Texture(Gdx.files.internal("textures/TestHouse2.png"));
			houseImage =  ImageIO.read(new File("assets/textures/TestHouse_burningArea.png"));
			house2Image = ImageIO.read(new File("assets/textures/TestHouse2_burningArea.png"));
			houseMap.put(houseTexture, houseImage);
			houseMap.put(house2Texture, house2Image);
		} catch (IOException e) {
			System.out.println("Haus konnte nicht vollständig geladen werden. Fehler!!");
		}		
	}
	
	private static void loadSounds() {
		
	}
	
	private static void loadFonts() {
		defaultFont = new BitmapFont();
		
		menu45Font = new BitmapFont(Gdx.files.internal("fonts/menu45.fnt"), Gdx.files.internal("fonts/menu45.png"), false);
		text30Font = new BitmapFont(Gdx.files.internal("fonts/text30.fnt"), Gdx.files.internal("fonts/text30.png"), false);
		text45Font = new BitmapFont(Gdx.files.internal("fonts/text45.fnt"), Gdx.files.internal("fonts/text45.png"), false);
		text50Font = new BitmapFont(Gdx.files.internal("fonts/text50.fnt"), Gdx.files.internal("fonts/text50.png"), false);
	}
	
	public static void playSound (Sound sound) {
        sound.play(1);
	}
	
	public static void dispose() {
		atlas.dispose();
		firefighter_left.dispose();
		firefighter_right.dispose();
		mainMenu.dispose();

		houseTexture.dispose();
		house2Texture.dispose();
		cloudTexture.dispose();
		lightningTexture.dispose();
		
//		buttonSound.dispose();
		
		defaultFont.dispose();
		text30Font.dispose();
		text45Font.dispose();
		text50Font.dispose();
		menu45Font.dispose();
	}
}
