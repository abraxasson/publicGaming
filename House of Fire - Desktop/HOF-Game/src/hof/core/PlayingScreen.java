package hof.core;

import hof.core.utils.GameScreen;
import hof.core.utils.Settings;
import hof.level.objects.Fire;
import hof.level.objects.Firefighter;
import hof.level.objects.House;
import hof.level.objects.StatusBar;
import hof.level.objects.TimeLine;
import hof.net.MessageProcessing;
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

	ArrayList<Firefighter> firefighters;
	Firefighter ff;
	TimeLine timeline;
	StatusBar statusBar;
	House currentHouse;
	
	MessageProcessing processing;

	FPS fps;

	public PlayingScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		
		
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
		currentHouse.setHealthpoints(1000);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draws everything
		spriteBatch.begin();
		currentHouse.draw(spriteBatch);
		drawFirefighters();
		timeline.draw(spriteBatch, currentHouse);
		statusBar.draw(spriteBatch);
		fps.draw(spriteBatch);
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
					if(input.getMessage().getX()<0){
						fighter.getWaterJet().setAngle(3);
					}
					else if((int)input.getMessage().getX()==0){
						System.out.println("Gleichgewicht!");
					}
					else if(input.getMessage().getX()>0){
						fighter.getWaterJet().setAngle(-3);
					}
					
					if(input.getMessage().getY()<1){
						fighter.getWaterJet().setStrength(3);
					}
					else if((int)input.getMessage().getY()==0){
						System.out.println("Gleichgewicht!");
					}
					else if(input.getMessage().getY()>1){
						fighter.getWaterJet().setStrength(-3);
					}
					
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
		}

		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			game.setScreen(game.mainMenuScreen);
			processing.getPlayerList().clear();
		}

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		checkComputerInput();
	}

	private void moveFireFighter() {
		ButtonInput input = null;
		if(processing.hasInput()){
			input = processing.getInput();
		}
		
		for (Firefighter fighter : firefighters) {
			if (input != null && fighter.getPlayer().getIp().equals(input.getPlayer().getIp())) {
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
	
	private void checkCollision(){
		for(Fire fire : currentHouse.getFireList()){
			if(ff.getWaterJet().getStreamArea().overlaps(fire.getFireRectangle())){
				fire.setHealthpoints(fire.getHealthpoints() - Settings.waterDamage);
			}	
			for(Firefighter firefighter : firefighters){
				if(firefighter.getWaterJet().getStreamArea().overlaps(fire.getFireRectangle())){
					fire.setHealthpoints(fire.getHealthpoints() - Settings.waterDamage);
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
