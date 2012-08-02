package hof.core.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets{

	
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
	
	
	public static Map<Texture, BufferedImage> houseMap;
	public static ArrayList<Texture> houseList;
	
	
	public static Texture pureWhiteTexture;
	public static Texture borderTexture;
	public static Texture timeLineTexture;
	public static Texture firefighter_red;
	public static Texture firefighter_blue;
	public static Texture firefighter_orange;
	public static Texture firefighter_green;
	public static Texture firefighter_magenta;
	public static Texture firefighter_cyan;
	public static Texture cat;
	public static Texture waitingForPlayerTitle;
	public static Texture waitingForPlayerHelp;
	public static Texture waitingForPlayerMain;
	public static Texture waitingForPlayerHighscore;
	public static Texture mainMenu;
	public static Texture levelFinishedScreen;
	public static Texture gameOverScreen;
	public static Texture gameFinishedScreen;
	public static Texture trophyTexture;
	public static Texture gameFinishedText;
	public static Texture cloudTexture;
	public static Texture lightningTexture;
	public static Texture statusBar;
	
	
	public static Animation runningAnimation;
	public static Animation runningAnimation2;
	public static Animation runningAnimation3;
	public static Animation runningCatAnimation;
	public static Animation fireWorksAnimation;
	public static Animation flyingBirdAnimation;
	
	
	public static Sound loser;
	public static Sound applause;
	public static Sound applause2;
	public static Sound rain;
	public static Sound sirene;
	public static Sound fanfare;
	public static Sound thunder;
	public static Sound firework;
	public static Sound firework2;
	public static Sound fire;
	public static Sound evilLaugh;
	
	
	public static Music backgroundMusic;
	public static Music backgroundMusicMenu;
	
	
	public static BufferedImage smokingArea;
	
	
	public static BitmapFont defaultFont;
	public static BitmapFont text30Font;
	public static BitmapFont text45Font;
	public static BitmapFont text50Font;
	public static BitmapFont menu45Font;
	public static BitmapFont highscore30Font;
	public static BitmapFont highscore40Font;
	public static BitmapFont highscore50Font;
	public static BitmapFont standardFont30;
	public static BitmapFont standardFont40;
	public static BitmapFont standardFont50;
	public static BitmapFont statusBarFont30;
	public static BitmapFont statusBarFont35;
	public static BitmapFont statusBarFont40;
	public static BitmapFont hobo25;
	public static BitmapFont hobo30;
	
	
	public static void load() {
		houseMap = new HashMap<Texture, BufferedImage>();
		houseList = new ArrayList<>();
		loadTextures();
		loadHouses();
		createAnimations();
		loadFonts();
		loadSounds();
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
		runningCatAnimation = new Animation(0.05f, frames);
		
		colums = 4;
		rows = 1;
		sheet = new Texture (Gdx.files.internal("textures/animations/flyingBird.png"));
		tmp = TextureRegion.split(sheet, 
				sheet.getWidth() / colums, sheet.getHeight() / rows);
		frames = new TextureRegion[rows * colums];
		index = 0;
		for (int i = 0; i < rows; i++  ) {
			for (int j = 0; j < colums; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		flyingBirdAnimation = new Animation(0.1f, frames);
		
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
		fireWorksAnimation = new Animation(1/25f, frames);
	}

	private static void loadTextures() {
		pureWhiteTexture = new Texture(Gdx.files.internal("textures/8x8.png"));
		borderTexture = new Texture(Gdx.files.internal("textures/border2.png"));
		timeLineTexture = new Texture(Gdx.files.internal("textures/timeline.png"));;
		
		firefighter_blue = new Texture(Gdx.files.internal("textures/firefighters/firefighter_blue.png"));
		firefighter_red = new Texture(Gdx.files.internal("textures/firefighters/firefighter_red.png"));
		firefighter_orange = new Texture(Gdx.files.internal("textures/firefighters/firefighter_orange.png"));
		firefighter_green = new Texture(Gdx.files.internal("textures/firefighters/firefighter_green.png"));
		firefighter_cyan = new Texture(Gdx.files.internal("textures/firefighters/firefighter_cyan.png"));
		firefighter_magenta = new Texture(Gdx.files.internal("textures/firefighters/firefighter_magenta.png"));
		
		cat = new Texture(Gdx.files.internal("textures/cat.png"));
		
		
		mainMenu = new Texture(Gdx.files.internal("textures/mainMenu.png"));
		cloudTexture = new Texture(Gdx.files.internal("textures/cloud.png"));
		lightningTexture = new Texture(Gdx.files.internal("textures/lightning.png"));
		levelFinishedScreen = new Texture(Gdx.files.internal("textures/LevelFinished.png"));
		gameOverScreen = new Texture(Gdx.files.internal("textures/GameOver.png"));
		statusBar = new Texture(Gdx.files.internal("textures/statusBar.png"));
		try {
			smokingArea = ImageIO.read(new File("assets/textures/GameOver_SmokingArea.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gameFinishedScreen = new Texture(Gdx.files.internal("textures/GameFinished.png"));
		trophyTexture = new Texture(Gdx.files.internal("textures/trophy.png"));
		gameFinishedText = new Texture(Gdx.files.internal("textures/gameFinishedText.png"));
		
		waitingForPlayerTitle = new Texture(Gdx.files.internal("textures/waitingForPlayers/view1.png"));
		waitingForPlayerHelp = new Texture(Gdx.files.internal("textures/waitingForPlayers/view2.png"));
		waitingForPlayerMain = new Texture(Gdx.files.internal("textures/waitingForPlayers/view4.png"));
		waitingForPlayerHighscore = new Texture(Gdx.files.internal("textures/waitingForPlayers/view5.png"));
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
	
	private static void loadFonts() {
		defaultFont = new BitmapFont();
		
		menu45Font = new BitmapFont(Gdx.files.internal("fonts/menu45.fnt"), Gdx.files.internal("fonts/menu45.png"), false);
		text30Font = new BitmapFont(Gdx.files.internal("fonts/text30.fnt"), Gdx.files.internal("fonts/text30.png"), false);
		text45Font = new BitmapFont(Gdx.files.internal("fonts/text45.fnt"), Gdx.files.internal("fonts/text45.png"), false);
		text50Font = new BitmapFont(Gdx.files.internal("fonts/text50.fnt"), Gdx.files.internal("fonts/text50.png"), false);
		highscore30Font = new BitmapFont(Gdx.files.internal("fonts/Highscore30.fnt"),Gdx.files.internal("fonts/Highscore30.png"),false);
		highscore40Font = new BitmapFont(Gdx.files.internal("fonts/Highscore40.fnt"),Gdx.files.internal("fonts/Highscore40.png"),false);
		highscore50Font = new BitmapFont(Gdx.files.internal("fonts/highscore50.fnt"),Gdx.files.internal("fonts/highscore50.png"),false);
		standardFont30 = new BitmapFont(Gdx.files.internal("fonts/standardFont30.fnt"),Gdx.files.internal("fonts/standardFont30.png"),false);
		standardFont40 = new BitmapFont(Gdx.files.internal("fonts/standardFont40.fnt"),Gdx.files.internal("fonts/standardFont40.png"),false);
		standardFont50 = new BitmapFont(Gdx.files.internal("fonts/standardFont50.fnt"),Gdx.files.internal("fonts/standardFont50.png"),false);
		statusBarFont30 = new BitmapFont(Gdx.files.internal("fonts/statusBarFont30.fnt"),Gdx.files.internal("fonts/statusBarFont30.png"),false);
		statusBarFont35 = new BitmapFont(Gdx.files.internal("fonts/statusBarFont35.fnt"),Gdx.files.internal("fonts/statusBarFont35.png"),false);
		statusBarFont40 = new BitmapFont(Gdx.files.internal("fonts/statusBarFont40.fnt"),Gdx.files.internal("fonts/statusBarFont40.png"),false);
		hobo25 = new BitmapFont(Gdx.files.internal("fonts/hobo25.fnt"),Gdx.files.internal("fonts/hobo25.png"),false);
		hobo30 = new BitmapFont(Gdx.files.internal("fonts/hobo30.fnt"),Gdx.files.internal("fonts/hobo30.png"),false);
	}
	
	public static void loadSounds(){
		loser = Gdx.audio.newSound(Gdx.files.internal("sounds/loser.ogg")); 
		applause = Gdx.audio.newSound(Gdx.files.internal("sounds/applause.mp3"));
		applause2 = Gdx.audio.newSound(Gdx.files.internal("sounds/applause2.ogg"));
		sirene = Gdx.audio.newSound(Gdx.files.internal("sounds/emergency.mp3"));
		fanfare = Gdx.audio.newSound(Gdx.files.internal("sounds/fanfare2.ogg"));
		rain = Gdx.audio.newSound(Gdx.files.internal("sounds/rain.mp3"));
		thunder = Gdx.audio.newSound(Gdx.files.internal("sounds/thunder.ogg"));
		firework = Gdx.audio.newSound(Gdx.files.internal("sounds/firework.ogg"));
		firework2 = Gdx.audio.newSound(Gdx.files.internal("sounds/firework2.mp3"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/forest_fire.ogg"));
		evilLaugh = Gdx.audio.newSound(Gdx.files.internal("sounds/laugh.ogg"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
		backgroundMusicMenu = Gdx.audio.newMusic(Gdx.files.internal("sounds/background2.mp3"));
	}
	
	public static void dispose() {
		firefighter_red.dispose();
		firefighter_blue.dispose();
		firefighter_orange.dispose();
		firefighter_green.dispose();
		firefighter_magenta.dispose();
		firefighter_cyan.dispose();
		cat.dispose();
		mainMenu.dispose();
		levelFinishedScreen.dispose();
		gameOverScreen.dispose();
		gameFinishedScreen.dispose();
		waitingForPlayerMain.dispose();
		waitingForPlayerHelp.dispose();
		waitingForPlayerTitle.dispose();
		waitingForPlayerHighscore.dispose();
		
		pureWhiteTexture.dispose();
		borderTexture.dispose();
		timeLineTexture.dispose();
		cloudTexture.dispose();
		lightningTexture.dispose();
		trophyTexture.dispose();
		statusBar.dispose();
		gameFinishedText.dispose();
		trophyTexture.dispose();
		
		for (Texture texture: houseList) {
			texture.dispose();
		}
		
		
//		buttonSound.dispose();
		loser.dispose();
		applause.dispose();
		applause2.dispose();
		sirene.dispose();
		rain.dispose();
		fanfare.dispose();
		thunder.dispose();
		firework.dispose();
		firework2.dispose();
		fire.dispose();
		evilLaugh.dispose();
		backgroundMusic.dispose();
		backgroundMusicMenu.dispose();
		
		defaultFont.dispose();
		text30Font.dispose();
		text45Font.dispose();
		text50Font.dispose();
		menu45Font.dispose();
		highscore30Font.dispose();
		highscore40Font.dispose();
		highscore50Font.dispose();
		standardFont30.dispose();
		standardFont40.dispose();
		standardFont50.dispose();
		statusBarFont30.dispose();
		statusBarFont35.dispose();
		statusBarFont40.dispose();
		hobo25.dispose();
		hobo30.dispose();
	}
}
