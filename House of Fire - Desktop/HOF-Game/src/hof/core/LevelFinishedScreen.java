package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.LevelInfoMessage;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class LevelFinishedScreen extends GameScreen<HouseOfFireGame> {

	private MessageProcessing processing;
	private UdpClientThread udpClient;
	private long startTime;
	private boolean lastLevel;
	private float collumnWidth;
	private BitmapFont infoFont;
	private BitmapFont nameFont;

	public LevelFinishedScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		udpClient = UdpClientThread.getInstance();
		lastLevel = false;
		collumnWidth = Gdx.graphics.getWidth() / 8;
		infoFont = Assets.text45Font;
		nameFont = Assets.menu45Font;
	}

	@Override
	public void show() {
		startTime = System.currentTimeMillis();

		game.houseIndex++;

		if (game.houseIndex < game.houseList.size()) {
			lastLevel = false;
		} else {
			lastLevel = true;
		}
		int medal = getMedal();
		for (Player player : processing.getPlayerList()) {
			udpClient.sendObject(new LevelInfoMessage(
					LevelInfoMessage.FINISHED, game.houseIndex, lastLevel,
					medal), player.getIp());
		}

	}

	private int getMedal() {
		int medal;
		int diff = game.houseList.size() / 3;
		int bronze = diff;
		int silver = 2 * diff;
		int gold = 3 * diff;

		if (game.houseIndex == bronze) {
			medal = LevelInfoMessage.BRONZE_MEDAL;
		} else if (game.houseIndex == silver) {
			medal = LevelInfoMessage.SILVER_MEDAL;
		} else if (game.houseIndex == gold) {
			medal = LevelInfoMessage.GOLD_MEDAL;
		} else {
			medal = LevelInfoMessage.NO_MEDAL;
		}
		return medal;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		spriteBatch.begin();
		spriteBatch.draw(Assets.levelFinishedScreen, 0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.BLACK);
		TextBounds bounds = infoFont.getBounds("Test");
		spriteBatch
				.draw(Assets.pureWhiteTexture,
						this.collumnWidth,
						(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (5.1 * bounds.height)),
						this.collumnWidth * 6, 15);
		spriteBatch.setColor(oldColor);
		showScore();
		spriteBatch.end();

		if (System.currentTimeMillis() - startTime >= 4000l) {
			if (lastLevel) {
				game.setScreen(game.gameFinishedScreen);
			} else {
				game.setScreen(game.playingScreen);
			}
		}
	}

	private void showScore() {
		int i = 0;
		for (Player player : processing.getPlayerList()) {
			i++;
			this.infoFont.setColor(player.getColor());
			this.nameFont.setColor(player.getColor());
			String oldScore = "" + player.getScore() + " Points";
			String bonuspoints = "" + player.getBonuspoints() + " Bonuspts";
			TextBounds bounds = infoFont.getBounds(player.getName());
			nameFont.draw(spriteBatch, player.getName(), i * this.collumnWidth,
					(float) (Gdx.graphics.getHeight() * 0.6));
			infoFont.draw(spriteBatch, oldScore, i * this.collumnWidth,
					(float) (Gdx.graphics.getHeight() * 0.6)
							- (2 * bounds.height));
			infoFont.draw(
					spriteBatch,
					bonuspoints,
					i * this.collumnWidth,
					(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (3.5 * bounds.height)));
			int newPoints = player.getScore() + player.getBonuspoints();
			String newScore = "" + newPoints + " Points";
			infoFont.draw(
					spriteBatch,
					newScore,
					i * this.collumnWidth,
					(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (5.5 * bounds.height)));
		}
	}

	@Override
	public void hide() {
		for (Player player : processing.getPlayerList()) {
			udpClient.sendObject(new LevelInfoMessage(LevelInfoMessage.STARTED,
					game.houseIndex + 1), player.getIp());
			player.setLastInput(System.currentTimeMillis());
		}
		processing.emptyInputQueues();
	}

}
