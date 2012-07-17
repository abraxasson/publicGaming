package hof.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	private static TextureAtlas atlas;
	
	public static TextureRegion pureWhiteTextureRegion;
	
	public static Sound buttonSound;
	
	
	public static BitmapFont defaultFont;
	
	public static void load() {
		String textureDir = "textures";
		String textureFile = textureDir + "/pack";
		atlas = new TextureAtlas(Gdx.files.internal(textureFile), Gdx.files.internal(textureDir));
		loadTextures();
		createAnimations();
		loadSounds();
		loadFonts();
	}
	
	private static void createAnimations() {
		
	}

	private static void loadTextures() {
		pureWhiteTextureRegion = atlas.findRegion("8x8");
	}

	private static void loadSounds() {
		
	}
	
	private static void loadFonts() {
		defaultFont = new BitmapFont();
	}
	
	public static void playSound (Sound sound) {
        sound.play(1);
	}
}
