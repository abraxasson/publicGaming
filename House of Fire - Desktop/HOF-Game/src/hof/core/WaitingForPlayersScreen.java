package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.HallOfFame;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.UdpServerThread;
import hof.net.userMessages.PlayerInfoMessage;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class WaitingForPlayersScreen extends GameScreen<HouseOfFireGame> {

	@SuppressWarnings("unused")
	private UdpServerThread udpServer;
	private MessageProcessing processing;

	private HallOfFame fame;

	private boolean isWaiting;

	private float stateTime;

	private Status status;

	public WaitingForPlayersScreen(HouseOfFireGame game) {
		super(game);
		udpServer = UdpServerThread.getInstance();
		processing = MessageProcessing.getInstance();

		fame = HallOfFame.getInstance();
	}

	@Override
	public void show() {
		isWaiting = true;
		stateTime = 0;
		status = Status.Title;
	}

	@Override
	public void render(float delta) {
		if (isWaiting) {
			stateTime += delta;
			checkStatus();
		} else {
			game.setScreen(game.playingScreen);
		}
		
		processing.processMessageQueue();
		if (!processing.getPlayerList().isEmpty()) {
			isWaiting = false;
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			game.setScreen(game.mainMenuScreen);
		}

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		
		spriteBatch.begin();
		switch(status) {
		case Title:
			spriteBatch.draw(
					Assets.waitingForPlayerTitle,
					Assets.FRAME_WIDTH / 2
							- Assets.waitingForPlayerTitle.getWidth() / 2,
					Assets.FRAME_HEIGHT / 2
							- Assets.waitingForPlayerTitle.getHeight() / 2);
			break;
			
		case Help:
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			break;
			
		case Main:
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			break;
		
		case House:
			Gdx.gl.glClearColor(1, 1, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			break;
			
		case Highscore:
			fame.draw(spriteBatch, Assets.text50Font, Color.WHITE);
			break;
		}
		spriteBatch.end();

		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();
				UdpClientThread.getInstance().sendMessage(
						new PlayerInfoMessage("Florian"), ia);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkStatus() {
		if (stateTime > 2) {
			status = Status.Help;
		} 
		if (stateTime > 4) {
			status = Status.House;
		} 
		if (stateTime > 6) {
			status = Status.Main;
		}  
		if (stateTime > 8) {
			status = Status.Highscore;
		}
		if (stateTime > 10) {
			status = Status.Title;
			stateTime = 0;
		}

	}

	private enum Status {
		Title, Help, House, Main, Highscore
	}
}
