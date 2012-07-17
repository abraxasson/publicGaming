package hof.core;

import hof.core.utils.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class MainMenuScreen extends GameScreen<HouseOfFireGame> {

	public MainMenuScreen(HouseOfFireGame game) {
		super(game);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			game.setScreen(game.waitingForPlayersScreen);
		}
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			System.exit(0);
		}

	}
}
 