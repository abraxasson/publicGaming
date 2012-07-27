package hof.core.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets {

	public static final int FRAME_WIDTH = Gdx.graphics.getWidth();
	public static final int FRAME_HEIGHT = Gdx.graphics.getHeight();
	public static final int STATUS_BAR_HEIGHT = FRAME_HEIGHT;
	public static final int STATUS_BAR_WIDTH = FRAME_WIDTH / 6;
	
	public static final int TIMELINE_OFFSET = 10;
	public static final int TIMELINE_WIDTH_OFFSET = TIMELINE_OFFSET * 2;
	public static final int TIMELINE_WIDTH = (int) (FRAME_WIDTH - STATUS_BAR_WIDTH) - (TIMELINE_WIDTH_OFFSET);
	public static final int TIMELINE_HEIGHT = (int) (FRAME_HEIGHT / 15);
	
	public static final int CANVAS_HEIGHT = (int) (FRAME_HEIGHT - TIMELINE_HEIGHT);
	public static final int CANVAS_WIDTH = FRAME_WIDTH - STATUS_BAR_WIDTH;
	
	public static final int RANKING_HEIGHT = STATUS_BAR_HEIGHT / 2;
	
	public static final int SMSBAR_HEIGHT = STATUS_BAR_HEIGHT / 2;
	
	public static Texture pureWhiteTextureRegion;
	public static Texture borderTexture;
	
	public static Texture firefighter_left;
	public static Texture firefighter_right;
	public static Texture mainMenu;
	public static Texture levelFinishedScreen;
	
	public static Map<Texture, BufferedImage> houseMap;
	public static ArrayList<Texture> houseList;
	
	public static Texture cloudTexture;
	public static Texture lightningTexture;
	
	public static Sound buttonSound;
	public static Sound loser;
	
	public static BitmapFont defaultFont;
	public static BitmapFont text30Font;
	public static BitmapFont text45Font;
	public static BitmapFont text50Font;
	public static BitmapFont menu45Font;
	
	public static void load() {
		houseMap = new HashMap<Texture, BufferedImage>();
		houseList = new ArrayList<>();
		loadTextures();
		loadHouses();
		createAnimations();
		loadSounds();
		loadFonts();
	}

	public static ParticleEffect loadWaterParticles() {
		ParticleEffect waterParticleEffect = new ParticleEffect();
		waterParticleEffect.load(Gdx.files.internal("particles/waterjet-new.p"), Gdx.files.internal("particles"));
		return waterParticleEffect;
	}
	
	public static ParticleEffect loadRainParticles(){
		ParticleEffect rainParticleEffect = new ParticleEffect();
		rainParticleEffect.load(Gdx.files.internal("particles/rain.p"), Gdx.files.internal("particles"));
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
		pureWhiteTextureRegion = new Texture(Gdx.files.internal("textures/8x8.png"));
		borderTexture = new Texture(Gdx.files.internal("textures/border.png"));
		
		firefighter_left = new Texture(Gdx.files.internal("textures/firefighter_left.png"));
		firefighter_right = new Texture(Gdx.files.internal("textures/firefighter_right.png"));
		mainMenu = new Texture(Gdx.files.internal("textures/mainMenu.png"));
		cloudTexture = new Texture(Gdx.files.internal("textures/cloud.png"));
		lightningTexture = new Texture(Gdx.files.internal("textures/lightning.png"));
		levelFinishedScreen = new Texture(Gdx.files.internal("textures/LevelFinished.png"));
	}
	
	private static void loadHouses() {
		try {
			String housePath = "textures/house-texture";
			String imagePath = "assets/" + housePath;
			File directory = new File(imagePath);
			File [] dirs = null;
			if (directory.isDirectory()) {
				dirs = directory.listFiles();
				
			} 
			for (File dir : dirs) {
				if (dir.isDirectory()) {
					System.out.println(dir.getName());
					File [] file = dir.listFiles();
					String path = file[0].getPath().substring(file[0].getPath().indexOf('\\') + 1);
					Texture house = new Texture(Gdx.files.internal(path));
					houseList.add(house);
					houseMap.put(house, ImageIO.read(new File(file[1].getPath())));
				}
				
			}
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
		firefighter_left.dispose();
		firefighter_right.dispose();
		mainMenu.dispose();

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
