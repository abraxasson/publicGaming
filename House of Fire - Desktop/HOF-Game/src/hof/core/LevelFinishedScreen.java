package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.LevelInfoMessage;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class LevelFinishedScreen extends GameScreen<HouseOfFireGame> {

	private MessageProcessing processing;
	private UdpClientThread udpClient;
	private long startTime;
	private boolean lastLevel;
	
	public LevelFinishedScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		udpClient = UdpClientThread.getInstance();
		lastLevel = false;
	}
	
	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		
		game.houseIndex++;
		
		if (game.houseIndex < game.houseList.size()) {
			lastLevel = false;
		} else  {
			lastLevel = true;
		}
		int medal = getMedal();
		for (Player player: processing.getPlayerList()) {
			udpClient.sendObject(new LevelInfoMessage(LevelInfoMessage.FINISHED, game.houseIndex , lastLevel, medal), player.getIp());
		}
		
	}

	private int getMedal() {
		int medal;
		int diff = game.houseList.size() / 3;
		int  bronze = diff;
		int silver = 2 * diff;
		int gold = 3 * diff;
		
		if (game.houseIndex == bronze) {
			medal = LevelInfoMessage.BRONZE;
		} else if (game.houseIndex == silver) {
			medal = LevelInfoMessage.SILVER;
		} else if (game.houseIndex == gold) {
			medal = LevelInfoMessage.GOLD;
		} else {
			medal = LevelInfoMessage.NOTHING;
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
		Assets.text50Font.draw(spriteBatch, "Level finished", Assets.CANVAS_WIDTH / 2, Assets.CANVAS_HEIGHT / 2);
		spriteBatch.end();
		
		if (System.currentTimeMillis() - startTime >= 4000l) {
			if (lastLevel) {
				game.setScreen(game.gameFinishedScreen);
			} else {
				game.setScreen(game.playingScreen);
			}			
		}
	}
	
	@Override
	public void hide() {
		for (Player player: processing.getPlayerList()) {
			udpClient.sendObject(new LevelInfoMessage(LevelInfoMessage.STARTED,game.houseIndex + 1),player.getIp());
			player.setLastInput(System.currentTimeMillis());
		}
		processing.emptyInputQueues();
	}

	

}
