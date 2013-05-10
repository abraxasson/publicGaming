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

/**
 * This class manages all used assets in one class.
 * They are loaded here and stored in public static fields, so they can be used everywhere.
 * In addition to this, there are also some important constants for creating the Screens.
 *
 */
public class Assets{

	
	/**
	 * The width of the current screen.
	 */
	public static final int FRAME_WIDTH = Gdx.graphics.getWidth();
	/**
	 * The height of the current screen.
	 */
	public static final int FRAME_HEIGHT = Gdx.graphics.getHeight();
	/**
	 * The width of the status bar dynamically calculated by use of the Frame width.
	 */
	public static final int STATUS_BAR_WIDTH = FRAME_WIDTH / 6;
	/**
	 * The offset from the upper end of the screen for the timeline.
	 */
	public static final int TIMELINE_OFFSET = 10;
	/**
	 * The offset from the width of the timeline.
	 */
	public static final int TIMELINE_WIDTH_OFFSET = TIMELINE_OFFSET * 2;
	/**
	 * The width for the drawn timeline.
	 */
	public static final int TIMELINE_WIDTH = (int) (FRAME_WIDTH - STATUS_BAR_WIDTH) - (TIMELINE_WIDTH_OFFSET);
	/**
	 * The height for the drawn timeline.
	 */
	public static final int TIMELINE_HEIGHT = (int) (FRAME_HEIGHT / 15);
	/**
	 * The height of the actual level.
	 */
	public static final int CANVAS_HEIGHT = FRAME_HEIGHT;
	/**
	 * The width of the actual level.
	 */
	public static final int CANVAS_WIDTH = FRAME_WIDTH - STATUS_BAR_WIDTH;
	/**
	 * The height of the ranking.
	 */
	public static final int RANKING_HEIGHT = FRAME_HEIGHT / 2;
	/**
	 * The height of the SMS-bar.
	 */
	public static final int SMSBAR_HEIGHT = FRAME_HEIGHT / 2;
	
	/**
	 * This map stores the textures of the Levels and the Images of the Burning Area.
	 */
	public static Map<Texture, BufferedImage> houseMap;
	/**
	 * In this list all textures are stored in the right order.
	 */
	public static ArrayList<Texture> houseList;
	
	/**
	 * 8x8 sized white Texture
	 */
	public static Texture pureWhiteTexture;
	/**
	 * Just a border.
	 */
	public static Texture borderTexture;
	/**
	 * The texture for the timeline and the SMS-Effects.
	 */
	public static Texture timeLineTexture;
	/**
	 * The red Fire-fighter.
	 */
	public static Texture firefighter_red;
	/**
	 * The blue Fire-fighter.
	 */
	public static Texture firefighter_blue;
	/**
	 * The orange Fire-fighter.
	 */
	public static Texture firefighter_orange;
	/**
	 * The green Fire-fighter.
	 */
	public static Texture firefighter_green;
	/**
	 * The magenta Fire-fighter.
	 */
	public static Texture firefighter_magenta;
	/**
	 * The cyan Fire-fighter.
	 */
	public static Texture firefighter_cyan;
	
	/**
	 * The title texture for the title view in the WaitingForPlayerScreen.
	 */
	public static Texture waitingForPlayerTitle;
	/**
	 * The help texture for the help view in the WaitingForPlayerScreen.
	 */
	public static Texture waitingForPlayerHelp;
	/**
	 * The title texture for the main view in the WaitingForPlayerScreen.
	 */
	public static Texture waitingForPlayerMain;
	/**
	 * The title texture for the highscore view in the WaitingForPlayerScreen.
	 */
	public static Texture waitingForPlayerHighscore;
	/**
	 * The texture for the title in the main menu.
	 */
	public static Texture mainMenu;
	/**
	 * The texture for the LevelFinishedScreen.
	 */
	public static Texture levelFinishedScreen;
	/**
	 * The background texture of the GameOverScreen.
	 */
	public static Texture gameOverScreen;
	/**
	 * The texture for the GameFinishedScreen.
	 */
	public static Texture gameFinishedScreen;
	/**
	 * Texture of a big trophy.
	 */
	public static Texture trophyTexture;
	/**
	 * A Texture of the text "Game Finished".
	 */
	public static Texture gameFinishedText;
	/**
	 * The texture of the rain clod.
	 */
	public static Texture cloudTexture;
	/**
	 * The texture of the lightning.
	 */
	public static Texture lightningTexture;
	/**
	 * The texture for the StatusBar.
	 */
	public static Texture statusBar;
	/**
	 * The icon of the application 
	 */
	public static Texture icon_32x32;
	/**
	 * An animated running man for the timeline.
	 */
	public static Animation runningAnimation;
	/**
	 * An animated running cat. (Gag)
	 */
	public static Animation runningCatAnimation;
	/**
	 * Animated fireworks. 
	 */
	public static Animation fireWorksAnimation;
	/**
	 * An animated flying bird. (Gag)
	 */
	public static Animation flyingBirdAnimation;
	
	/**
	 * A typical loser sound.
	 */
	public static Sound loser;
	/**
	 * Sound of applause.
	 */
	public static Sound applause;
	/**
	 * Another applause.
	 */
	public static Sound applause2;
	/**
	 * The sound of rain played when the SMS-Effect rain is activated.
	 */
	public static Sound rain;
	/**
	 * A siren played when the level starts.
	 */
	public static Sound sirene;
	/**
	 * A fanfare.
	 */
	public static Sound fanfare;
	/**
	 * Sound of thunder played when the SMS-Effect lightning is activated.
	 */
	public static Sound thunder;
	/**
	 * The sound of fireworks.
	 */
	public static Sound firework;
	/**
	 * Another sound of fireworks.
	 */
	public static Sound firework2;
	/**
	 * The sound of burning fire during the game.
	 */
	public static Sound fire;
	/**
	 * The laugh at the GameOverScreen.
	 */
	public static Sound evilLaugh;
	/**
	 * The help scream for the WaitingForPlayerScreen.
	 */
	public static Sound help;
	
	/**
	 * The background-music played during the game.
	 */
	public static Music backgroundMusic;
	/**
	 * The background-music played in the menu.
	 */
	public static Music backgroundMusicMenu;
	
	/**
	 * Is used to determine where the smoke appears on the GameOverScreen.
	 */
	public static BufferedImage smokingArea;
	
	
	/**
	 * Arial 30 Font.
	 */
	public static BitmapFont text30Font;
	/**
	 * Cooper Std. 45
	 */
	public static BitmapFont menu45Font;
	/**
	 * Font for highscore. (50 pt)
	 */
	public static BitmapFont highscore50Font;
	/**
	 * Font for different use (30 pt).
	 */
	public static BitmapFont standardFont30;
	/**
	 * Font for different use (40 pt).
	 */
	public static BitmapFont standardFont40;
	/**
	 * Font for different use (50 pt).
	 */
	public static BitmapFont standardFont50;
	/**
	 * Font for the status bar (30 pt).
	 */
	public static BitmapFont statusBarFont30;
	/**
	 * Font for the status bar (35 pt).
	 */
	public static BitmapFont statusBarFont35;
	/**
	 * Font for the status bar (40 pt).
	 */
	public static BitmapFont statusBarFont40;
	/**
	 * Hobo-Font 30 pt for the LevelFinishedScreen.
	 */
	public static BitmapFont hobo30;
	
	/**
	 * This method has to be called when the game starts, otherwise the assets will not be loaded.
	 * It creates the map and the list for the levels and calls the other load-methods.
	 */
	public static void load() {
		houseMap = new HashMap<Texture, BufferedImage>();
		houseList = new ArrayList<>();
		loadHouses();
		loadTextures();
		createAnimations();
		loadFonts();
		loadSounds();
	}

	/**
	 * Loads and returns the particles for the smoke effect.
	 * @return smoke particles
	 */
	public static ParticleEffect loadSmokeParticles(){
		ParticleEffect smokeParticleEffect = new ParticleEffect();
		smokeParticleEffect.load(Gdx.files.internal("particles/smoke.p"),Gdx.files.internal("particles"));
		return smokeParticleEffect;
	}
	
	/**
	 * Loads and returns the particles for the water-jet.
	 * @return water-jet particles
	 */
	public static ParticleEffect loadWaterParticles() {
		ParticleEffect waterParticleEffect = new ParticleEffect();
		waterParticleEffect.load(Gdx.files.internal("particles/waterjet2.p"), Gdx.files.internal("particles"));
		return waterParticleEffect;
	}
	
	/**
	 * Loads and returns the particles for the rain effect.
	 * @return rain particles
	 */
	public static ParticleEffect loadRainParticles(){
		ParticleEffect rainParticleEffect = new ParticleEffect();
		rainParticleEffect.load(Gdx.files.internal("particles/rain.p"), Gdx.files.internal("particles"));
		return rainParticleEffect;
	}
	
	/**
	 * Loads and returns the particles for the fire.
	 * @return fire particles
	 */
	public static ParticleEffect loadFireParticles() {
		ParticleEffect fireParticleEffect = new ParticleEffect();
		fireParticleEffect.load(Gdx.files.internal("particles/fire.p"), Gdx.files.internal("particles"));
		return fireParticleEffect;
	}
	
	/**
	 * Loads and returns the particles for the fireworks effect.
	 * @return fireworks particles
	 */
	public static ParticleEffect loadFireWorksParticles() {
		ParticleEffect fireWorkParticleEffect = new ParticleEffect();
		fireWorkParticleEffect.load(Gdx.files.internal("particles/firework.p"), Gdx.files.internal("particles"));
		return fireWorkParticleEffect;
	}

	/**
	 * Loads and creates the animations used in the game.
	 */
	private static void createAnimations() {
		int colums;
		int rows;
		Texture sheet;
		TextureRegion [][] tmp;
		TextureRegion [] frames;
		int index;
		
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
		runningAnimation = new Animation(0.05f, frames);
		
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

	/**
	 * Loads the textures from the assets folder.
	 */
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
		
		icon_32x32 = new Texture(Gdx.files.internal("textures/icon_32x32.png"));
		
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
	
	/**
	 * Loads the houses dynamically.
	 * When a new folder containing a house and a burning-area is added the level is automatically added to the game.
	 * The burning-area has to be the second file in the list of the folder.
	 */
	private static void loadHouses() {
		try {
			String housePath = "textures/house-texture";
			String imagePath = "assets/" + housePath;
			File directory = new File(imagePath);
			File [] dirs = null;
			if (directory.isDirectory()) {
				dirs = directory.listFiles();
				
			} 
			else{
				System.out.println(directory.getAbsolutePath());
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
	
	/**
	 * Loads the fonts from the assets folder.
	 */
	private static void loadFonts() {
		menu45Font = new BitmapFont(Gdx.files.internal("fonts/menu45.fnt"), Gdx.files.internal("fonts/menu45.png"), false);
		text30Font = new BitmapFont(Gdx.files.internal("fonts/text30.fnt"), Gdx.files.internal("fonts/text30.png"), false);
		highscore50Font = new BitmapFont(Gdx.files.internal("fonts/highscore50.fnt"),Gdx.files.internal("fonts/highscore50.png"),false);
		standardFont30 = new BitmapFont(Gdx.files.internal("fonts/standardFont30.fnt"),Gdx.files.internal("fonts/standardFont30.png"),false);
		standardFont40 = new BitmapFont(Gdx.files.internal("fonts/standardFont40.fnt"),Gdx.files.internal("fonts/standardFont40.png"),false);
		standardFont50 = new BitmapFont(Gdx.files.internal("fonts/standardFont50.fnt"),Gdx.files.internal("fonts/standardFont50.png"),false);
		statusBarFont30 = new BitmapFont(Gdx.files.internal("fonts/statusBarFont30.fnt"),Gdx.files.internal("fonts/statusBarFont30.png"),false);
		statusBarFont35 = new BitmapFont(Gdx.files.internal("fonts/statusBarFont35.fnt"),Gdx.files.internal("fonts/statusBarFont35.png"),false);
		statusBarFont40 = new BitmapFont(Gdx.files.internal("fonts/statusBarFont40.fnt"),Gdx.files.internal("fonts/statusBarFont40.png"),false);
		hobo30 = new BitmapFont(Gdx.files.internal("fonts/hobo30.fnt"),Gdx.files.internal("fonts/hobo30.png"),false);
	}
	
	/**
	 * Loads all the sounds and music.
	 */
	private static void loadSounds(){
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
		help = Gdx.audio.newSound(Gdx.files.internal("sounds/help.mp3"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
		backgroundMusicMenu = Gdx.audio.newMusic(Gdx.files.internal("sounds/background2.mp3"));
	}
	
	/**
	 * Disposes all Objects, which can be disposed.
	 */
	public static void dispose() {
		firefighter_red.dispose();
		firefighter_blue.dispose();
		firefighter_orange.dispose();
		firefighter_green.dispose();
		firefighter_magenta.dispose();
		firefighter_cyan.dispose();
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
		help.dispose();
		backgroundMusic.dispose();
		backgroundMusicMenu.dispose();
		
		text30Font.dispose();
		menu45Font.dispose();
		highscore50Font.dispose();
		standardFont30.dispose();
		standardFont40.dispose();
		standardFont50.dispose();
		statusBarFont30.dispose();
		statusBarFont35.dispose();
		statusBarFont40.dispose();
		hobo30.dispose();
	}
}
