package hof.core.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

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
	
	private static TextureAtlas atlas;
	public static Texture houseTexture;
	public static TextureRegion pureWhiteTextureRegion;
	
	public static Sound buttonSound;
	
	public static BitmapFont defaultFont;
	public static BitmapFont textFont;
	
	public static void load() {
		String textureDir = "textures";
		String textureFile = textureDir + "/pack";
		atlas = new TextureAtlas(Gdx.files.internal(textureFile), Gdx.files.internal(textureDir));
		loadTextures();
		createAnimations();
		loadSounds();
		loadFonts();

	}
	
	public static ParticleEffect loadWaterParticles() {
		ParticleEffect waterParticleEffect = new ParticleEffect();
		waterParticleEffect.load(Gdx.files.internal("particles/waterjet.p"), Gdx.files.internal("particles"));
		return waterParticleEffect;
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
		houseTexture = new Texture(Gdx.files.internal("textures/TestHouseFire4.png"));
	}

	private static void loadSounds() {
		
	}
	
	private static void loadFonts() {
		defaultFont = new BitmapFont();
		
		textFont = new BitmapFont(Gdx.files.internal("fonts/text.fnt"), Gdx.files.internal("fonts/text.png"), false);
	}
	
	public static void playSound (Sound sound) {
        sound.play(1);
	}
}
