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
	private float houseTime;
	private int index;
	private Status status;

	public WaitingForPlayersScreen(HouseOfFireGame game) {
		super(game);
		udpServer = UdpServerThread.getInstance();
		processing = MessageProcessing.getInstance();

		fame = HallOfFame.getInstance();
		index = 0;
	}

	@Override
	public void show() {
		isWaiting = true;
		stateTime = 0;
		houseTime = 0;
		status = Status.Title;
	}

	@Override
	public void render(float delta) {
		if (isWaiting) {
			stateTime += delta;

			processing.processMessageQueue();
			if (!processing.getPlayerList().isEmpty()) {
				isWaiting = false;
			}
			checkStatus();

			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
				game.setScreen(game.mainMenuScreen);
			}

			Color oldColor = spriteBatch.getColor();
			spriteBatch.begin();
			switch (status) {
			case Title:
				spriteBatch.draw(
						Assets.waitingForPlayerTitle,
						Assets.FRAME_WIDTH / 2
								- Assets.waitingForPlayerTitle.getWidth() / 2,
						Assets.FRAME_HEIGHT / 2
								- Assets.waitingForPlayerTitle.getHeight() / 2);

				break;

			case Help:
				drawHelpView();
				break;
			case Main:
				drawBackgroundHouse(delta);
				spriteBatch.setColor(oldColor);
				spriteBatch.draw(Assets.waitingForPlayerMain, 0, 0,
						Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT);
				break;
			case House:
				drawBackgroundHouse(delta);
				break;

			case Highscore:
				int xPos = (Assets.FRAME_WIDTH - Assets.waitingForPlayerHighscore
						.getWidth()) / 2;
				int yPos = Assets.FRAME_HEIGHT - Assets.waitingForPlayerHighscore.getHeight();
//				spriteBatch.draw(Assets.waitingForPlayerHighscore, xPos, yPos);
//				fame.draw(spriteBatch, Assets.menu45Font, Color.WHITE);
				fame.draw(spriteBatch, Assets.FRAME_WIDTH/2, Assets.FRAME_HEIGHT - 10, 0, Assets.menu45Font, Color.WHITE);
				break;
			}
			spriteBatch.end();
			spriteBatch.setColor(oldColor);

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

			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
		} else {
			game.setScreen(game.playingScreen);
		}
	}

	private void drawBackgroundHouse(float delta) {
		houseTime += delta;
		if (houseTime >= 2) {
			index++;
			houseTime = 0;
		}
		if (index >= game.houseList.size()) {
			index = 0;
		}

		spriteBatch.setColor(Color.LIGHT_GRAY);
		// game.houseList.get(index).resetHouse();
		game.houseList.get(index).drawFullscreen(spriteBatch);
	}

	private void drawHelpView() {
		float width = Assets.FRAME_WIDTH;
		float height = Assets.FRAME_HEIGHT;
		spriteBatch.draw(Assets.waitingForPlayerHelp, width / 10, height / 3);
		if (stateTime > 2.5) {
			spriteBatch.draw(Assets.waitingForPlayerHelp, width / 2,
					height * 0.9f);
		}
		if (stateTime > 3) {
			spriteBatch.draw(Assets.waitingForPlayerHelp, width * 0.75f,
					height / 6f);
		}
		if (stateTime > 3.5) {
			spriteBatch.draw(Assets.waitingForPlayerHelp, width / 5,
					height * 0.7f);
		}
		if (stateTime > 4) {
			spriteBatch.draw(Assets.waitingForPlayerHelp, width / 3,
					height / 4f);
		}
		if (stateTime > 4.5) {
			spriteBatch.draw(Assets.waitingForPlayerHelp, width * 0.75f,
					height / 2);
		}
		if (stateTime > 5) {
			spriteBatch
					.draw(Assets.waitingForPlayerHelp, width / 2, height / 2);
		}
	}

	private void checkStatus() {
		if (stateTime > 2) {
			status = Status.Help;
		}
		if (stateTime > 6) {
			status = Status.House;
		}
		if (stateTime > 8) {
			status = Status.Main;
		}
		if (stateTime > 14) {
			status = Status.Highscore;
		}
		if (stateTime > 18) {
			status = Status.Title;
			stateTime = 0;
		}

	}

	private enum Status {
		Title, Help, House, Main, Highscore
	}
}
