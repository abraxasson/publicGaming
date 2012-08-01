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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
	
	public static Texture pureWhiteTexture;
	public static Texture borderTexture;
	public static Texture timeLineTexture;
	
	public static Texture firefighter_left;
	public static Texture firefighter_right;
	public static Texture cat;
	
	public static Texture mainMenu;
	public static Texture levelFinishedScreen;
	public static Texture gameOverScreen;
	public static Texture gameFinishedScreen;
	public static Animation runningAnimation;
	public static Animation runningAnimation2;
	public static Animation runningAnimation3;
	public static Animation runningCatAnimation;
	public static Animation fireWorksAnimation;
	
	public static Map<Texture, BufferedImage> houseMap;
	public static ArrayList<Texture> houseList;
	
	public static Texture cloudTexture;
	public static Texture lightningTexture;
	
	public static Sound buttonSound;
	public static Sound loser;
	
	public static BufferedImage smokingArea;
	
	public static BitmapFont defaultFont;
	public static BitmapFont text30Font;
	public static BitmapFont text45Font;
	public static BitmapFont text50Font;
	public static BitmapFont menu45Font;
	public static BitmapFont highscore30Font;
	public static BitmapFont highscore40Font;
	public static BitmapFont highscore50Font;
	
	public static void load() {
		houseMap = new HashMap<Texture, BufferedImage>();
		houseList = new ArrayList<>();
		loadTextures();
		loadHouses();
		createAnimations();
		loadSounds();
		loadFonts();
	}

	public static ParticleEffect loadSmokeParticles(){
		ParticleEffect smokeParticleEffect = new ParticleEffect();
		smokeParticleEffect.load(Gdx.files.internal("particles/smoke.p"),Gdx.files.internal("particles"));
		return smokeParticleEffect;
	}
	
	public static ParticleEffect loadWaterParticles() {
		ParticleEffect waterParticleEffect = new ParticleEffect();
		waterParticleEffect.load(Gdx.files.internal("particles/waterjet2.p"), Gdx.files.internal("particles"));
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
	
	public static ParticleEffect loadFireWorksParticles() {
		ParticleEffect fireWorkParticleEffect = new ParticleEffect();
		fireWorkParticleEffect.load(Gdx.files.internal("particles/firework.p"), Gdx.files.internal("particles"));
		return fireWorkParticleEffect;
	}

	private static void createAnimations() {
		int colums;
		int rows;
		Texture sheet;
		TextureRegion [][] tmp;
		TextureRegion [] frames;
		int index;
		
		colums = 7;
		rows = 1;
		sheet = new Texture (Gdx.files.internal("textures/animations/Strich1.png"));
		tmp = TextureRegion.split(sheet, 
				sheet.getWidth() / colums, sheet.getHeight() / rows);
		frames = new TextureRegion[rows * colums];
		index = 0;
		for (int i = 0; i < rows; i++  ) {
			for (int j = 0; j < colums; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		runningAnimation = new Animation(0.05f, frames);
		
		colums = 14;
		rows = 1;
		sheet = new Texture (Gdx.files.internal("textures/animations/runningMan.png"));
		tmp = TextureRegion.split(sheet, 
				sheet.getWidth() / colums, sheet.getHeight() / rows);
		frames = new TextureRegion[rows * colums];
		index = 0;
		for (int i = 0; i < rows; i++  ) {
			for (int j = 0; j < colums; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		runningAnimation2 = new Animation(0.05f, frames);
		
		colums = 11;
		rows = 1;
		sheet = new Texture (Gdx.files.internal("textures/animations/runningMan2.png"));
		tmp = TextureRegion.split(sheet, 
				sheet.getWidth() / colums, sheet.getHeight() / rows);
		frames = new TextureRegion[rows * colums];
		index = 0;
		for (int i = 0; i < rows; i++  ) {
			for (int j = 0; j < colums; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		runningAnimation3 = new Animation(0.05f, frames);
		
		colums = 5;
		rows = 4;
		sheet = new Texture (Gdx.files.internal("textures/animations/runningCat.png"));
		tmp = TextureRegion.split(sheet, 
				sheet.getWidth() / colums, sheet.getHeight() / rows);
		frames = new TextureRegion[rows * colums];
		index = 0;
		for (int i = 0; i < rows; i++  ) {
			for (int j = 0; j < colums; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		runningCatAnimation = new Animation(0.08f, frames);
		
		colums = 5;
		rows = 5;
		sheet = new Texture (Gdx.files.internal("textures/animations/feuerwerk1.png"));
		tmp = TextureRegion.split(sheet, 
				sheet.getWidth() / colums, sheet.getHeight() / rows);
		frames = new TextureRegion[colums * rows];
		index = 0;
		for (int i = 0; i < rows; i++  ) {
			for (int j = 0; j < colums; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		fireWorksAnimation = new Animation(0.025f, frames);
	}

	private static void loadTextures() {
		pureWhiteTexture = new Texture(Gdx.files.internal("textures/8x8.png"));
		borderTexture = new Texture(Gdx.files.internal("textures/border.png"));
		timeLineTexture = new Texture(Gdx.files.internal("textures/timeline.png"));;
		
		firefighter_left = new Texture(Gdx.files.internal("textures/firefighter_left.png"));
		firefighter_right = new Texture(Gdx.files.internal("textures/firefighter_right.png"));
		cat = new Texture(Gdx.files.internal("textures/cat.png"));
		mainMenu = new Texture(Gdx.files.internal("textures/mainMenu.png"));
		cloudTexture = new Texture(Gdx.files.internal("textures/cloud.png"));
		lightningTexture = new Texture(Gdx.files.internal("textures/lightning.png"));
		levelFinishedScreen = new Texture(Gdx.files.internal("textures/LevelFinished.png"));
		gameOverScreen = new Texture(Gdx.files.internal("textures/GameOver.png"));
		try {
			smokingArea = ImageIO.read(new File("assets/textures/GameOver_SmokingArea.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gameFinishedScreen = new Texture(Gdx.files.internal("textures/GameFinished.png"));
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
					File [] file = dir.listFiles();
					String path = file[0].getPath().substring(file[0].getPath().indexOf('\\') + 1);
					Texture house = new Texture(Gdx.files.internal(path));
					houseList.add(house);
					houseMap.put(house, ImageIO.read(new File(file[1].getPath())));
				}
				
			}
		} catch (IOException e) {
			System.out.println("Haus konnte nicht vollständig geladen werden. Fehler!!");
			Gdx.app.exit();
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
		highscore30Font = new BitmapFont(Gdx.files.internal("fonts/Highscore30.fnt"),Gdx.files.internal("fonts/Highscore30.png"),false);
		highscore40Font = new BitmapFont(Gdx.files.internal("fonts/Highscore40.fnt"),Gdx.files.internal("fonts/Highscore40.png"),false);
		highscore50Font = new BitmapFont(Gdx.files.internal("fonts/highscore50.fnt"),Gdx.files.internal("fonts/highscore50.png"),false);;
	}
	
	public static void playSound (Sound sound) {
        sound.play(1);
	}
	
	public static void dispose() {
		firefighter_left.dispose();
		firefighter_right.dispose();
		cat.dispose();
		mainMenu.dispose();
		levelFinishedScreen.dispose();
		gameOverScreen.dispose();
		gameFinishedScreen.dispose();
		
		pureWhiteTexture.dispose();
		borderTexture.dispose();
		timeLineTexture.dispose();
		cloudTexture.dispose();
		lightningTexture.dispose();
		
		for (Texture texture: houseList) {
			texture.dispose();
		}
		
//		buttonSound.dispose();
		
		defaultFont.dispose();
		text30Font.dispose();
		text45Font.dispose();
		text50Font.dispose();
		menu45Font.dispose();
		highscore30Font.dispose();
		highscore40Font.dispose();
		highscore50Font.dispose();
		
	}
}
