package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.Settings;
import hof.core.view.StatusBar;
import hof.core.view.TimeLine;
import hof.level.effects.AbstractCloud;
import hof.level.effects.Lightning;
import hof.level.effects.Rain;
import hof.level.effects.WaterPressure;
import hof.level.objects.Fire;
import hof.level.objects.Firefighter;
import hof.level.objects.House;
import hof.level.objects.NonPlayable;
import hof.level.objects.Pixel;
import hof.level.objects.WaterJet;
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
import java.util.Collections;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class PlayingScreen extends GameScreen<HouseOfFireGame> {

	private ArrayList<Firefighter> firefighters;
	private TimeLine timeline;
	private StatusBar statusBar;
	private House currentHouse;
	private ArrayList<NonPlayable> gags;

	private MessageProcessing processing;

	private FPS fps;
	private InetAddress ia;
	private long finishedTime;
	private static long startTime;
	private static long randomTime;

	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();

		firefighters = new ArrayList<>();
		gags = new ArrayList<NonPlayable>();
		currentHouse = game.houseList.get(game.houseIndex);

		timeline = new TimeLine();
		statusBar = new StatusBar();
		fps = new FPS();
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
			player.setScore(player.getScore() + player.getBonuspoints()
					+ player.getMinuspoints());
			player.setBonuspoints(0);
			player.setMinuspoints(0);
		}
		finishedTime = 0;
		startTime = System.currentTimeMillis();
		randomTime = (long)(10000+(Math.random()*20000));
		Lightning.setLastUsed(System.currentTimeMillis());
		Rain.setLastUsed(System.currentTimeMillis());
		WaterPressure.setLastUsed(System.currentTimeMillis());
		Assets.backgroundMusic.play();
		Assets.backgroundMusic.setLooping(true);
		Assets.sirene.play();
		Assets.fire.loop();
	}

	@Override
	public void hide() {
		processing.getSmsQueue().clear();
		this.gags.clear();
		Assets.backgroundMusic.stop();
		Assets.fire.stop();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.08235f, 0f, 0.498f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draws everything
		spriteBatch.begin();
		currentHouse.draw(spriteBatch);

		removeDeadGags();
		drawGag();
		
		if(System.currentTimeMillis() >= startTime + randomTime){
			double random = Math.random();
			if(random > 0.5){
				initGag(new NonPlayable(100, (int) (Gdx.graphics.getHeight()*0.75)/5, 100,
						Assets.runningCatAnimation));
			}
			else{
				initGag(new NonPlayable(100,(int) (Gdx.graphics.getHeight()*0.75),100, Assets.flyingBirdAnimation));
			}
			
			startTime = System.currentTimeMillis();
			randomTime = startTime;
		}
		
		drawFirefighters();

		statusBar.draw(spriteBatch);
		fps.draw(spriteBatch);
		timeline.draw(spriteBatch, currentHouse);

		drawSpecialEffects();
		spriteBatch.draw(Assets.borderTexture, 0, 0, Assets.CANVAS_WIDTH,
				Gdx.graphics.getHeight());
		spriteBatch.end();

		// checks if new players are available
		checkPlayers();
		Collections.sort(processing.getPlayerList());

		// checks that the players stay inside the screen
		
		moveFireFighter();
		keepInBounds();
		checkCollision();

		processing.processMessageQueue();

		updateWaterJet();

		if (!currentHouse.getAlive()) {
			game.setScreen(game.gameOverScreen);
		}

		if (currentHouse.getFireList().size() == 0) {
			Assets.fire.stop();
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

		if (Gdx.input.isKeyPressed(Keys.W)) {
			currentHouse.getFireList().clear();
		}

		if (Gdx.input.isKeyPressed(Keys.L)) {
			currentHouse.destroy();
		}

		checkComputerInput();
		this.processing.removeInactivePlayers();
		processWaterPressure();
	}

	private void drawGag() {
		if (!this.gags.isEmpty()) {
			this.gags.get((int)(Math.random()*gags.size())).drawAnimation(spriteBatch);
		}
	}

	private void processWaterPressure() {
		if (processing.hasWaterPressureMessage()) {
			WaterPressureInfoMessage waterInfoMessage = processing
					.getWaterPressureMessage();
			for (Firefighter firefighter : this.firefighters) {
				if (firefighter.getPlayer().getIp()
						.equals(waterInfoMessage.getIa())) {
					if (waterInfoMessage.getPressure() <= 0) {
						firefighter.getWaterJet().setActive(false);
					} else {
						firefighter.getWaterJet().setActive(true);
					}
					// WaterJet waterJet = firefighter.getWaterJet();
					// waterJet.setSize(waterJet.getSize()*waterInfoMessage.getPressure()/100);
				}
			}
		}
	}

	private void initGag(NonPlayable gag) {
		this.gags.add(gag);
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
					waterJet.setAngle(30);
				}
				break;
			case RIGHT:
				if (waterJet.getAngle() >= 45) {
					waterJet.setAngle(-30);
				}
				break;
			default:
				break;
			}
			switch (waterJet.getStrengthState()) {
			case UP:
				if (waterJet.getStrength() < (Assets.CANVAS_HEIGHT) * 1.3) {
					waterJet.setStrength(200);
				}
				break;
			case DOWN:
				if (waterJet.getStrength() > (Assets.CANVAS_HEIGHT / 5)) {
					waterJet.setStrength(-200);
				}
				break;
			case NORMAL:
			default:
				break;
			}
		}
	}

	public static void setRandomTime(){
		randomTime = (long)(10000+(Math.random()*20000));
		startTime = System.currentTimeMillis();
	}
	
	private void drawSpecialEffects() {
		if (processing.hasSMS()) {
			for (AbstractCloud effect : processing.getSmsQueue()) {
				switch (effect.getType()) {
				case AbstractCloud.LIGHTNING:
					Lightning lightning = (Lightning) effect;
					if (Lightning.isReady()) {
						if (lightning.getActive()) {
							if (lightning.getHotSpot() == null) {
								lightning.setHotSpot(currentHouse
										.getRandomBurningArea());
							}
							if (lightning.getLifeTime() == Settings.lightningLifeTime) {
								currentHouse.getFireList().add(
										new Fire(lightning.getHotSpot()));
								Assets.thunder.play();
							}
							lightning.draw(spriteBatch);
						}
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
						if (rain.getBurningSpot() != null
								&& rain.isOnPosition()) {
							for (Fire fire : currentHouse.getFireList()) {
								if (fire.getX() < rain.getBurningSpot().getX() + 10
										&& fire.getX() > rain.getBurningSpot()
												.getX() - 10) {
									fire.setHealthpoints(fire.getHealthpoints()
											- Settings.rainDamage);
									Rain.setLastUsed(System.currentTimeMillis());
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
					}
					break;
				default:
					break;
				}
			}

			Iterator<AbstractCloud> iter = processing.getSmsQueue().iterator();
			while (iter.hasNext()) {
				AbstractCloud currentEffect = iter.next();
				if (!currentEffect.getActive()) {
					if (currentEffect.getType() == AbstractCloud.WATERPRESSURE) {
						for (Firefighter firefighter : this.firefighters) {
							WaterJet waterJet = firefighter.getWaterJet();
							waterJet.changeDiameter(Settings.waterAimSize);
						}
					}
					iter.remove();
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

	private void removeDeadGags() {
		Iterator<NonPlayable> iter = this.gags.iterator();
		NonPlayable gag;
		while (iter.hasNext()) {
			gag = iter.next();
			if (gag.getHealthpoints() < 0) {
				iter.remove();
			}
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

		for (NonPlayable gag : this.gags) {
			for (Firefighter firefighter : this.firefighters) {
				if (firefighter.getWaterJet().getStreamArea()
						.overlaps(gag.getPosition())) {
					gag.setHealthpoints(gag.getHealthpoints()
							- Settings.waterDamage);
					firefighter.getPlayer()
							.setMinuspoints(
									(int) (firefighter.getPlayer()
											.getMinuspoints() - (gag
											.getHealthpoints()
											* Gdx.graphics.getDeltaTime())*1000));
				}
			}
		}
	}

	private void checkComputerInput() {
		if (Gdx.input.isKeyPressed(Keys.V)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();

				UdpClientThread.getInstance().sendMessage(
						new ButtonInfoMessage(ButtonInfoMessage.NORMAL), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.C)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();

				UdpClientThread.getInstance().sendMessage(
						new ButtonInfoMessage(ButtonInfoMessage.LEFT), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.B)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();

				UdpClientThread.getInstance().sendMessage(
						new ButtonInfoMessage(ButtonInfoMessage.RIGHT), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.Y)) {
			UdpClientThread.getInstance().sendMessage(
					new SMSInfoMessage(SMSInfoMessage.LIGHTNING), ia);
		}

		if (Gdx.input.isKeyPressed(Keys.X)) {
			UdpClientThread.getInstance().sendMessage(
					new SMSInfoMessage(SMSInfoMessage.RAIN), ia);
		}

		if (Gdx.input.isKeyPressed(Keys.Z)) {
			UdpClientThread.getInstance().sendMessage(
					new SMSInfoMessage(SMSInfoMessage.PRESSURE), ia);
		}
	}
}
