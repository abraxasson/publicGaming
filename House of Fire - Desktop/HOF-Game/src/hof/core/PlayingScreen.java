package hof.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.level.objects.Fire;
import hof.level.objects.Firefighter;
import hof.level.objects.House;
import hof.level.objects.Pixel;
import hof.player.Player;

public class PlayingScreen extends GameScreen<HouseOfFireGame> {

	Firefighter firefighter;
	BitmapFont font;
	House house;
	String txt = "";
	Fire fire;
	Fire fire2;
	
	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		spriteBatch = new SpriteBatch();
		firefighter = new Firefighter(Assets.pureWhiteTextureRegion.getTexture(), Gdx.graphics.getWidth() / 2, 
				0, 40, 80, new Player("Florian", null, Color.PINK));
		house = new House(Assets.houseTexture,1000);
		house.setBurningArea(255, 242, 10, "TestHouseFire3.png");
		font = Assets.textFont;
		fire = new Fire(1000,house.getRandomBurningArea());
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		house.draw(spriteBatch);
		firefighter.draw(spriteBatch);
		fire.draw(spriteBatch);
		//fire2.draw(spriteBatch);
		spriteBatch.end();
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			
			int d = firefighter.getX();
			int x =  d + (int) (300 * Gdx.graphics.getDeltaTime());
			firefighter.setX(x);
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			
			int d = firefighter.getX();
			int x =  d - (int) (300 * Gdx.graphics.getDeltaTime());
			firefighter.setX(x);
		}
		
		keepInBounds();
		
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			game.setScreen(game.mainMenuScreen);
		}
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			System.exit(0);
		}
	}
	
	private void keepInBounds() {
		firefighter.stayInBounds();
	}
}
