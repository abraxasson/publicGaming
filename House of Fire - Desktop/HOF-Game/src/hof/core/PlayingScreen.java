package hof.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.level.objects.Firefighter;
import hof.player.Player;

public class PlayingScreen extends GameScreen<HouseOfFireGame> {

	Firefighter firefighter;
	BitmapFont font;
	
	String txt = "";
	
	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		spriteBatch = new SpriteBatch();
		firefighter = new Firefighter(Assets.pureWhiteTextureRegion.getTexture(), Gdx.graphics.getWidth() / 2, 
				0, 40, 80, new Player("Florian", null, Color.PINK));

		font = Assets.textFont;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		firefighter.draw(spriteBatch);
		font.draw(spriteBatch, txt, 600, 1000);
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
