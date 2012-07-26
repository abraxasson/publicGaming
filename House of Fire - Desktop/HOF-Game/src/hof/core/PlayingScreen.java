package hof.core;

import hof.core.utils.GameScreen;
import hof.core.utils.Settings;
import hof.level.objects.AbstractCloud;
import hof.level.objects.Fire;
import hof.level.objects.Firefighter;
import hof.level.objects.House;
import hof.level.objects.Lightning;
import hof.level.objects.Pixel;
import hof.level.objects.Rain;
import hof.level.objects.StatusBar;
import hof.level.objects.TimeLine;
import hof.level.objects.WaterJet;
import hof.level.objects.WaterPressure;
import hof.net.MessageProcessing;
import hof.net.SmsProcessing;
import hof.net.userMessages.ButtonInfoMessage;
import hof.player.ButtonInput;
import hof.player.Player;
import hof.player.SensorInput;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class PlayingScreen extends GameScreen<HouseOfFireGame> {

	private ArrayList<Firefighter> firefighters;
	private Firefighter ff;
	private TimeLine timeline;
	private StatusBar statusBar;
	private House currentHouse;

	private SmsProcessing smsProcessing;
	private MessageProcessing processing;

	private FPS fps;

	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		smsProcessing = SmsProcessing.getInstance();

		firefighters = new ArrayList<>();
		ff = new Firefighter(new Player("Florian", null, Color.PINK),
				ButtonInfoMessage.NORMAL);
		currentHouse = game.houseList.get(game.houseIndex);

		timeline = new TimeLine();
		statusBar = new StatusBar();
		fps = new FPS();
	}

	@Override
	public void show() {
		currentHouse = game.houseList.get(game.houseIndex);
		currentHouse.resetHouse();
	}
	
	@Override
	public void hide() {
		smsProcessing.getList().clear();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draws everything
		spriteBatch.begin();
		timeline.draw(spriteBatch, currentHouse);
		statusBar.draw(spriteBatch);
		fps.draw(spriteBatch);
		currentHouse.draw(spriteBatch);
		drawFirefighters();
		drawSpecialEffects();
		spriteBatch.end();

		// checks if new players are available
		checkPlayers();

		// checks that the players stay inside the screen
		keepInBounds();

		moveFireFighter();

		checkCollision();

		if (processing.hasSensorInput()) {
			SensorInput input = processing.getSensorInput();
			for (Firefighter fighter : firefighters) {
				if (fighter.getPlayer().getIp()
						.equals(input.getPlayer().getIp())) {
					if (input.getMessage().getY() > 0) {
						fighter.getWaterJet().setAngle(-3);
					} else if (input.getMessage().getY() < 0) {
						fighter.getWaterJet().setAngle(3);
					}
					/*
					 * if(input.getMessage().getY()<-2){
					 * fighter.getWaterJet().setStrength(4); } else
					 * if(input.getMessage().getY()>2){
					 * fighter.getWaterJet().setStrength(-4); }
					 */

				}
			}
		}

		if (!currentHouse.getAlive()) {
			game.setScreen(game.gameOverScreen);
		}

		if (currentHouse.getFireList().size() == 0) {
			game.setScreen(game.levelFinishedScreen);
		}

		if (processing.getPlayerList().isEmpty()) {
			game.setScreen(game.waitingForPlayersScreen);
			game.houseIndex = 0;
		}

		if (Gdx.input.isKeyPressed(Keys.Y)) {
			this.smsProcessing.addEffect(new Lightning(currentHouse
					.getRandomBurningArea()));
		}

		if (Gdx.input.isKeyPressed(Keys.X)) {
			if (!currentHouse.getFireList().isEmpty()) {
				Fire fire = currentHouse.getFireList().get(
						(int) (Math.random() * currentHouse.getFireList()
								.size()));
				Pixel pixel = new Pixel(fire.getX(), fire.getY());
				this.smsProcessing.addEffect(new Rain(pixel));
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.Z)){
			Iterator<AbstractCloud> iter = this.smsProcessing.getList().iterator();
			while(iter.hasNext()){
				AbstractCloud message = (AbstractCloud) iter.next();
				if(message.getType() == AbstractCloud.WATERPRESSURE){
					iter.remove();
				}
			}
			this.smsProcessing.addEffect(new WaterPressure());
		}

		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			game.setScreen(game.mainMenuScreen);
			processing.getPlayerList().clear();
		}

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		checkComputerInput();
		this.processing.removeInactivePlayers();
	}

	private void drawSpecialEffects() {
		if (!smsProcessing.getList().isEmpty()) {
			for (AbstractCloud effect : smsProcessing.getList()) {
				switch (effect.getType()) {
				case AbstractCloud.LIGHTNING:
					Lightning lightning = (Lightning) effect;
					if (lightning.getActive()) {
						if (lightning.getLifeTime() == Settings.lightningLifeTime) {
							currentHouse.getFireList().add(
									new Fire(lightning.getHotSpot()));
						}
						lightning.draw(spriteBatch);
					}
					break;
				case AbstractCloud.RAIN:
					Rain rain = (Rain) effect;
					if (rain.getActive()) {
						if (rain.getLifeTime() == Settings.rainLifeTime) {
							for (Fire fire : currentHouse.getFireList()) {
								if (fire.getX() < rain.getBurningSpot().getX() + 10
										&& fire.getX() > rain.getBurningSpot()
												.getX() - 10) {
									fire.setHealthpoints(fire.getHealthpoints()
											- Settings.rainDamage);
								}
							}
						}
						rain.draw(spriteBatch);
					}
					break;
				case AbstractCloud.WATERPRESSURE:
					WaterPressure waterPressure = (WaterPressure) effect;
					if(waterPressure.getActive()){
						if(waterPressure.getLifeTime() == Settings.waterPressureLifeTime){
							for(Firefighter firefighter : this.firefighters){
								WaterJet waterJet = firefighter.getWaterJet();
								waterJet.setSize(waterJet.getSize()+WaterPressure.WATERPRESSUREINC);
							}
						}
						waterPressure.draw(spriteBatch);
					}
					else{
						for(Firefighter firefighter : this.firefighters){
							WaterJet waterJet = firefighter.getWaterJet();
							waterJet.setSize(Settings.waterAimSize);
						}
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private void moveFireFighter() {
		ButtonInput input = null;
		if (processing.hasInput()) {
			input = processing.getInput();
		}

		for (Firefighter fighter : firefighters) {
			if (input != null
					&& fighter.getPlayer().getIp()
							.equals(input.getPlayer().getIp())) {
				fighter.setState(input.getMessage().getState());
			}
			int d;
			int x;
			switch (fighter.getState()) {
			case ButtonInfoMessage.NORMAL:
				break;
			case ButtonInfoMessage.LEFT:
				d = fighter.getX();
				x = d - (int) (300 * Gdx.graphics.getDeltaTime());
				fighter.setX(x);
				fighter.setState(ButtonInfoMessage.LEFT);
				break;
			case ButtonInfoMessage.RIGHT:
				d = fighter.getX();
				x = d + (int) (300 * Gdx.graphics.getDeltaTime());
				fighter.setX(x);
				fighter.setState(ButtonInfoMessage.RIGHT);
				break;
			default:
				break;
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
			firefighters.add(new Firefighter(processing.getPlayer(),
					ButtonInfoMessage.NORMAL));
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

	private void checkCollision() {
		for (Fire fire : currentHouse.getFireList()) {
			if (ff.getWaterJet().getStreamArea()
					.overlaps(fire.getFireRectangle())) {
				fire.setHealthpoints(fire.getHealthpoints()
						- Settings.waterDamage);
			}
			for (Firefighter firefighter : firefighters) {
				if (firefighter.getWaterJet().getStreamArea()
						.overlaps(fire.getFireRectangle())) {
					fire.setHealthpoints(fire.getHealthpoints()
							- Settings.waterDamage);
					firefighter.getPlayer().incScore();
				}
			}
		}
	}

	private void checkComputerInput() {
		if (Gdx.input.isKeyPressed(Keys.V)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();

				processing.processMessage(new ButtonInfoMessage(
						ButtonInfoMessage.NORMAL), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.C)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();

				processing.processMessage(new ButtonInfoMessage(
						ButtonInfoMessage.LEFT), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.B)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();

				processing.processMessage(new ButtonInfoMessage(
						ButtonInfoMessage.RIGHT), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			int d = ff.getX();
			int x = d + (int) (300 * Gdx.graphics.getDeltaTime());
			ff.setX(x);
			ff.setState(ButtonInfoMessage.RIGHT);
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			int d = ff.getX();
			int x = d - (int) (300 * Gdx.graphics.getDeltaTime());
			ff.setX(x);
			ff.setState(ButtonInfoMessage.LEFT);
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
	}
}
