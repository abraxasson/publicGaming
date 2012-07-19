package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.level.objects.Firefighter;
import hof.level.objects.House;
import hof.level.objects.TimeLine;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayingScreen extends GameScreen<HouseOfFireGame> {

	Firefighter firefighter;
	TimeLine timeline;
	House house;

	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		spriteBatch = new SpriteBatch();
		timeline = new TimeLine();
		firefighter = new Firefighter(
				Assets.pureWhiteTextureRegion.getTexture(),
				Gdx.graphics.getWidth() / 2, 0, 40, 80, new Player("Florian",
						null, Color.PINK));
		house = new House(Assets.houseTexture, 1000,20);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		house.draw(spriteBatch);
		firefighter.draw(spriteBatch);
		timeline.draw(spriteBatch);
		spriteBatch.end();

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {

			int d = firefighter.getX();
			int x = d + (int) (300 * Gdx.graphics.getDeltaTime());
			firefighter.setX(x);
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {

			int d = firefighter.getX();
			int x = d - (int) (300 * Gdx.graphics.getDeltaTime());
			firefighter.setX(x);
		}

		keepInBounds();
		
		if (Gdx.input.isKeyPressed(Keys.UP) ||  Gdx.input.isKeyPressed(Keys.W)) {
			firefighter.getWaterJet().setStrength(200);
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN) ||  Gdx.input.isKeyPressed(Keys.S)) {
			firefighter.getWaterJet().setStrength(-200);
		}
		
		if ( Gdx.input.isKeyPressed(Keys.A)) {
			firefighter.getWaterJet().setAngle(40);
		}
		
		if ( Gdx.input.isKeyPressed(Keys.D)) {
			firefighter.getWaterJet().setAngle(-40);
		}
		
		

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
