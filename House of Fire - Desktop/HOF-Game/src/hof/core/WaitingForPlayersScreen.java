package hof.core;

import hof.core.utils.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WaitingForPlayersScreen extends GameScreen<HouseOfFireGame> {

	BitmapFont font;
	SpriteBatch spriteBatch;
	
	public WaitingForPlayersScreen(HouseOfFireGame game) {
		super(game);
		font = new BitmapFont();
		font.setColor(0, 0, 0, 1);
		spriteBatch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			game.setScreen(game.mainMenuScreen);
		}
		
		spriteBatch.begin();
		font.draw(spriteBatch, "Waiting for Players", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		spriteBatch.end();
	}
}
