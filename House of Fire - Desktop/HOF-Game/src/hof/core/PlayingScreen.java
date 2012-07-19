package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.level.objects.Firefighter;
import hof.level.objects.House;
import hof.level.objects.StatusBar;
import hof.level.objects.TimeLine;
import hof.net.MessageProcessing;
import hof.net.userMessages.AbstractMessage;
import hof.net.userMessages.InputInfoMessage;
import hof.net.userMessages.PlayerInfoMessage;
import hof.player.Player;
import hof.player.PlayerInput;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayingScreen extends GameScreen<HouseOfFireGame> {

	ArrayList<Firefighter> firefighters;
	Firefighter ff;
	TimeLine timeline;
	StatusBar statusBar;
	House house;
	MessageProcessing processing;

	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		spriteBatch = new SpriteBatch();
		timeline = new TimeLine();
		statusBar = new StatusBar();
		firefighters = new ArrayList<>();
		ff = new Firefighter(Assets.pureWhiteTextureRegion.getTexture(),
				Gdx.graphics.getWidth() / 2, 0, 40, 80, new Player("Florian",
						null, Color.PINK));
		house = new House(Assets.houseTexture, 1000, 20);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draws everything
		spriteBatch.begin();
		house.draw(spriteBatch);
		for (Firefighter fighter : firefighters) {
			// fighter.draw(spriteBatch);
		}
		ff.draw(spriteBatch);
		timeline.draw(spriteBatch, house);
		statusBar.draw(spriteBatch);
		spriteBatch.end();

		// checks if new players are available
		checkPlayers();

		// checks that the players stay inside the screen
		keepInBounds();

		if (processing.hasInput()) {
			InputInfoMessage input = processing.getInput().getMessage();
			if (input.getLeft()) {
				int d = ff.getX();
				int x = d - (int) (300 * Gdx.graphics.getDeltaTime());
				ff.setX(x);
			} else if (!input.getLeft()) {
				int d = ff.getX();
				int x = d + (int) (300 * Gdx.graphics.getDeltaTime());
				ff.setX(x);
			}
		}

		if (!house.getAlive()) {
			game.setScreen(game.gameOverScreen);
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			int d = ff.getX();
			int x = d + (int) (300 * Gdx.graphics.getDeltaTime());
			ff.setX(x);
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {

			int d = ff.getX();
			int x = d - (int) (300 * Gdx.graphics.getDeltaTime());
			ff.setX(x);
		}

		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
			ff.getWaterJet().setStrength(200);
		}

		if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
			ff.getWaterJet().setStrength(-200);
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			ff.getWaterJet().setAngle(40);
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			ff.getWaterJet().setAngle(-40);
		}

		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			game.setScreen(game.mainMenuScreen);
		}

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			System.exit(0);
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();
				processing.processMessage(new PlayerInfoMessage("Florian"), ia);
				processing.processMessage(new PlayerInfoMessage("Manuel"), ia);
				processing.processMessage(new InputInfoMessage(true), ia);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void checkPlayers() {
		firefighters.add(new Firefighter(processing.getPlayer()));
	}

	private void keepInBounds() {
		for (Firefighter fighter : firefighters) {
			fighter.stayInBounds();
		}
		ff.stayInBounds();
	}
}
