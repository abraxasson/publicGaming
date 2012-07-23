package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.level.objects.Firefighter;
import hof.level.objects.House;
import hof.level.objects.StatusBar;
import hof.level.objects.TimeLine;
import hof.net.MessageProcessing;
import hof.net.userMessages.InputInfoMessage;
import hof.player.Player;
import hof.player.PlayerInput;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

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

	FPS fps;
	
	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		spriteBatch = new SpriteBatch();
		timeline = new TimeLine();
		statusBar = new StatusBar();
		firefighters = new ArrayList<>();
		ff = new Firefighter(new Player("Florian",null,Color.PINK));
		house = new House(Assets.houseTexture, 1000, 20);
		
		fps = new FPS();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draws everything
		spriteBatch.begin();
		house.draw(spriteBatch);
		drawFirefighters();
		timeline.draw(spriteBatch, house);
		statusBar.draw(spriteBatch);
		fps.draw(spriteBatch);
		spriteBatch.end();

		// checks if new players are available
		checkPlayers();

		// checks that the players stay inside the screen
		keepInBounds();

		if (processing.hasInput()) {
			moveFireFighter();
		}

		if (!house.getAlive()) {
			game.setScreen(game.gameOverScreen);
		}
		
		if (processing.getPlayerList().isEmpty()) {
			//for testing not yet implemented
//			game.setScreen(game.waitingForPlayersScreen);
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			ff.setBody(Assets.firefighter_right);
			int d = ff.getX();
			int x = d + (int) (300 * Gdx.graphics.getDeltaTime());
			ff.setX(x);
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			ff.setBody(Assets.firefighter_left);
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
			processing.getPlayerList().clear();
		}

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (Gdx.input.isKeyPressed(Keys.V)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();
				
				processing.processMessage(new InputInfoMessage(true), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.B)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();
				
				processing.processMessage(new InputInfoMessage(false), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}

	private void moveFireFighter() {
		PlayerInput input = processing.getInput();
		for (Firefighter fighter: firefighters) {
			if (fighter.getPlayer().getIp().equals(input.getPlayer().getIp())){
				if (input.getMessage().getLeft()) {
					fighter.setBody(Assets.firefighter_left);
					int d = fighter.getX();
					int x = d - (int) (300 * Gdx.graphics.getDeltaTime());
					fighter.setX(x);
				} else  {
					fighter.setBody(Assets.firefighter_right);
					int d = fighter.getX();
					int x = d + (int) (300 * Gdx.graphics.getDeltaTime());
					fighter.setX(x);
				}		
			}
			
			
		}
		
	}

	private void drawFirefighters() {
		if (firefighters.size() > 0) {
			for (Firefighter fighter : firefighters) {
				fighter.draw(spriteBatch);
			}
		}
		ff.draw(spriteBatch);
	}

	private void checkPlayers() {
		if (processing.hasPlayers()) {
			firefighters.add(new Firefighter(processing.getPlayer()));
		}
		
		Iterator<Firefighter> iter = firefighters.iterator();
		while (iter.hasNext()) {
			Firefighter firefighter = iter.next();
			if (!processing.getPlayerList().contains(firefighter.getPlayer())) {
				iter.remove();
			}
		}
	}

	private void keepInBounds() {
		for (Firefighter fighter : firefighters) {
			fighter.stayInBounds();
		}
		ff.stayInBounds();
	}
}
