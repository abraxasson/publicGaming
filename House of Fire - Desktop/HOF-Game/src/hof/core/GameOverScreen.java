package hof.core;



import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.HallOfFame;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.GameFinishedInfoMessage;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class GameOverScreen extends GameScreen<HouseOfFireGame> {

	private long startTime;
	private HallOfFame fame;
	
	public GameOverScreen(HouseOfFireGame game) {
		super(game);
		fame = HallOfFame.getInstance();
	}
	
	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		
		MessageProcessing processing = MessageProcessing.getInstance();
		UdpClientThread udpClient = UdpClientThread.getInstance();
		for (Player player: processing.getPlayerList()) {
			fame.addPlayer(player);
			udpClient.setIA(player.getIp());
			udpClient.sendObject(new GameFinishedInfoMessage(false));
		}
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		spriteBatch.begin();
		Assets.text50Font.draw(spriteBatch, "GAME - OVER", Assets.CANVAS_WIDTH / 2, Assets.CANVAS_HEIGHT / 2);
		spriteBatch.end();
		
		if (System.currentTimeMillis() - startTime >= 4000l) {
			game.houseIndex = 0;
			game.setScreen(game.mainMenuScreen);
		}
	}
}
