package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.Settings;
import hof.level.effects.*;
import hof.level.objects.*;
import hof.level.objects.WaterJet.State;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.ButtonInfoMessage;
import hof.net.userMessages.SMSInfoMessage;
import hof.net.userMessages.WaterPressureInfoMessage;
import hof.player.ButtonInput;
import hof.player.Player;
import hof.player.SensorInput;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class PlayingScreen extends GameScreen<HouseOfFireGame> {

	private ArrayList<Firefighter> firefighters;
	private TimeLine timeline;
	private StatusBar statusBar;
	private House currentHouse;

	private MessageProcessing processing;

	private FPS fps;
	InetAddress ia;
	private long finishedTime;

	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();

		firefighters = new ArrayList<>();
		currentHouse = game.houseList.get(game.houseIndex);

		timeline = new TimeLine();
		statusBar = new StatusBar();
		fps = new FPS();
		finishedTime = 0;

		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		currentHouse = game.houseList.get(game.houseIndex);
		currentHouse.resetHouse();
		for (Player player : processing.getPlayerList()) {
			player.setScore(player.getScore() + player.getBonuspoints());
			player.setBonuspoints(0);
		}
	}

	@Override
	public void hide() {
		processing.getSmsQueue().clear();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.08235f, 0f, 0.498f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draws everything
		spriteBatch.begin();
		currentHouse.draw(spriteBatch);
		drawFirefighters();

		statusBar.draw(spriteBatch);
		fps.draw(spriteBatch);
		timeline.draw(spriteBatch, currentHouse);

		drawSpecialEffects();
		spriteBatch.end();

		// checks if new players are available
		checkPlayers();

		// checks that the players stay inside the screen
		keepInBounds();

		moveFireFighter();

		checkCollision();

		processing.processMessageQueue();

		updateWaterJet();

		if (!currentHouse.getAlive()) {
			if (finishedTime == 0) {
				finishedTime = System.currentTimeMillis();
			} else if (System.currentTimeMillis() - finishedTime > 3000l) {
				
			}
			game.setScreen(game.gameOverScreen);
		}

		if (currentHouse.getFireList().size() == 0) {
			for (Player player : this.processing.getPlayerList()) {
				player.setBonuspoints((int) (currentHouse.getHealthpoints()) * 10);
			}
			
			if (finishedTime == 0) {
				finishedTime = System.currentTimeMillis();
			} else if (System.currentTimeMillis() - finishedTime > 3000l) {
				game.setScreen(game.levelFinishedScreen);
			}
		}

		if (processing.getPlayerList().isEmpty()) {
			game.setScreen(game.waitingForPlayersScreen);
			game.houseIndex = 0;
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
		processWaterPressure();
	}

	private void processWaterPressure(){
		if(processing.hasWaterPressureMessage()){
			WaterPressureInfoMessage waterInfoMessage = processing.getWaterPressureMessage();
			for(Firefighter firefighter : this.firefighters){
				if(firefighter.getPlayer().getIp().equals(waterInfoMessage.getIa())){
					if (waterInfoMessage.getPressure() <= 0) {
						firefighter.getWaterJet().setActive(false);
					} else {
						firefighter.getWaterJet().setActive(true);
					}
//					WaterJet waterJet = firefighter.getWaterJet();					
//					waterJet.setSize(waterJet.getSize()*waterInfoMessage.getPressure()/100);
				}
			}
		}
	}
	
	private void updateWaterJet() {
		if (processing.hasSensorInput()) {
			checkWaterJetState();
		}
		moveWaterJet();
	}
	
	private void checkWaterJetState() {
		SensorInput input = processing.getSensorInput();
		for (Firefighter fighter : firefighters) {
			if (fighter.getPlayer().getIp().equals(input.getPlayer().getIp())) {

				WaterJet waterJet = fighter.getWaterJet();
				if (input.getMessage().getY() > 1.5) {
					waterJet.setDirectionState(State.RIGHT);
				} else if (input.getMessage().getY() < 1.5
						&& input.getMessage().getY() > -1.5) {
					waterJet.setDirectionState(State.NORMAL);
				} else if (input.getMessage().getY() < -1.5) {
					waterJet.setDirectionState(State.LEFT);
				}

				if (input.getMessage().getX() < 1.5) {
					waterJet.setStrengthState(State.DOWN);
				} else if (input.getMessage().getX() > 1.5
						&& input.getMessage().getX() < 8.5) {
					waterJet.setStrengthState(State.NORMAL);
				} else if (input.getMessage().getX() > 8.5) {
					waterJet.setStrengthState(State.UP);
				}

			}
		}
	}

	private void moveWaterJet() {
		for (Firefighter fighter : firefighters) {
			WaterJet waterJet = fighter.getWaterJet();
			switch (waterJet.getDirectionState()) {
			case NORMAL:
				break;
			case LEFT:
				if (waterJet.getAngle() <= 135) {
					waterJet.setAngle(40);
				}
				break;
			case RIGHT:
				if (waterJet.getAngle() >= 45) {
					waterJet.setAngle(-40);
				}
				break;
			default:
				break;
			}
			switch (waterJet.getStrengthState()) {
			case UP:
				if (waterJet.getStrength() < (Assets.CANVAS_HEIGHT) * 1.3) {
					waterJet.setStrength(72);
				}
				break;
			case DOWN:
				if (waterJet.getStrength() > (Assets.CANVAS_HEIGHT / 5)) {
					waterJet.setStrength(-72);
				}
				break;
			case NORMAL:
			default:
				break;
			}
		}
	}

	private void drawSpecialEffects() {
		if (processing.hasSMS()) {
			for (AbstractCloud effect : processing.getSmsQueue()) {
				switch (effect.getType()) {
				case AbstractCloud.LIGHTNING:
					Lightning lightning = (Lightning) effect;
					if (lightning.getActive()) {
						if (lightning.getHotSpot() == null) {
							lightning.setHotSpot(currentHouse
									.getRandomBurningArea());
						}
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
						if (rain.getBurningSpot() == null) {
							if (!currentHouse.getFireList().isEmpty()) {
								Fire fire = currentHouse.getFireList().get(
										(int) (Math.random() * currentHouse
												.getFireList().size()));
								Pixel pixel = new Pixel(fire.getX(),
										fire.getY());
								rain.setBurningSpot(pixel);
							}
						}
						if (rain.getLifeTime() == Settings.rainLifeTime
								&& rain.getBurningSpot() != null) {
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
					if (waterPressure.getActive()) {
						if (waterPressure.getLifeTime() == Settings.waterPressureLifeTime) {
							for (Firefighter firefighter : this.firefighters) {
								WaterJet waterJet = firefighter.getWaterJet();
								waterJet.changeDiameter(waterJet.getSize()
										+ WaterPressure.WATERPRESSUREINC);
							}
						}
						waterPressure.draw(spriteBatch);
					} else {
						for (Firefighter firefighter : this.firefighters) {
							WaterJet waterJet = firefighter.getWaterJet();
							waterJet.changeDiameter(Settings.waterAimSize);
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
	}

	private void checkCollision() {
		for (Fire fire : currentHouse.getFireList()) {
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

				UdpClientThread.getInstance().sendObject(
						new ButtonInfoMessage(ButtonInfoMessage.NORMAL), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.C)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();

				UdpClientThread.getInstance().sendObject(
						new ButtonInfoMessage(ButtonInfoMessage.LEFT), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.B)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();

				UdpClientThread.getInstance().sendObject(
						new ButtonInfoMessage(ButtonInfoMessage.RIGHT), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.Y)) {
			UdpClientThread.getInstance().sendObject(
					new SMSInfoMessage(SMSInfoMessage.LIGHTNING), ia);
		}

		if (Gdx.input.isKeyPressed(Keys.X)) {
			UdpClientThread.getInstance().sendObject(
					new SMSInfoMessage(SMSInfoMessage.RAIN), ia);
			// processing.processMessage(new
			// SMSInfoMessage(SMSInfoMessage.RAIN), ia);
		}

		if (Gdx.input.isKeyPressed(Keys.Z)) {
			Iterator<AbstractCloud> iter = processing.getSmsQueue().iterator();
			while (iter.hasNext()) {
				AbstractCloud message = (AbstractCloud) iter.next();
				if (message.getType() == AbstractCloud.WATERPRESSURE) {
					iter.remove();
				}
			}

			UdpClientThread.getInstance().sendObject(
					new SMSInfoMessage(SMSInfoMessage.PRESSURE), ia);
		}

	}
}
