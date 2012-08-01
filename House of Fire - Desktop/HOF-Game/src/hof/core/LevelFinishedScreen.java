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
		collumnWidth = Gdx.graphics.getWidth() / 8 + 30;
		infoFont = Assets.standardFont30;
		nameFont = Assets.standardFont40;
	}

	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		Assets.applause.play();
		Assets.applause2.play();
		game.houseIndex++;

		if (game.houseIndex < game.houseList.size()) {
			lastLevel = false;
		} else {
			lastLevel = true;
		}
		int medal = getMedal();
		LevelInfoMessage message = new LevelInfoMessage(
				LevelInfoMessage.FINISHED, game.houseIndex, lastLevel, medal);
		for (Player player : processing.getPlayerList()) {
			udpClient.sendMessage(message, player.getIp());
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
						(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (6.5 * bounds.height)),
						(this.collumnWidth - 30) * 6, 10);
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
			TextBounds bounds = infoFont.getBounds(player.getName());
			i++;
			if(i==1){
				infoFont.draw(spriteBatch, "Score", i * this.collumnWidth,
						(float) (Gdx.graphics.getHeight() * 0.6)
								- (2 * bounds.height));
				infoFont.draw(
						spriteBatch,
						"Bonuspoints",
						i * this.collumnWidth,
						(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (3.5 * bounds.height)));

				infoFont.draw(
						spriteBatch,
						"Minuspoints",
						i * this.collumnWidth,
						(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (5 * bounds.height)));
				infoFont.draw(
						spriteBatch,
						"Total",
						i * this.collumnWidth,
						(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (7 * bounds.height)));

			}
			else{
				this.infoFont.setColor(player.getColor());
				this.nameFont.setColor(player.getColor());
				String oldScore = "" + player.getScore() + " ";
				String bonuspoints = "" + player.getBonuspoints() + " ";
				String minuspoints = "" + player.getMinuspoints() + " ";
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

				infoFont.draw(
						spriteBatch,
						minuspoints,
						i * this.collumnWidth,
						(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (5 * bounds.height)));

				int newPoints = player.getScore() + player.getBonuspoints()
						+ player.getMinuspoints();
				String newScore = "" + newPoints + " ";

				infoFont.draw(
						spriteBatch,
						newScore,
						i * this.collumnWidth,
						(float) ((float) (Gdx.graphics.getHeight() * 0.6) - (7 * bounds.height)));
			}
			
		}
	}

	@Override
	public void hide() {
		if (!lastLevel) {
			LevelInfoMessage message = new LevelInfoMessage(
					LevelInfoMessage.STARTED, game.houseIndex + 1);
			for (Player player : processing.getPlayerList()) {
				udpClient.sendMessage(message, player.getIp());
				player.setLastInput(System.currentTimeMillis());
			}
		}
		Assets.applause.stop();
		Assets.applause2.stop();
		processing.emptyInputQueues();
	}

}
