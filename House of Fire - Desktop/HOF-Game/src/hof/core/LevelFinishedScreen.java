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
	
	public LevelFinishedScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		udpClient = UdpClientThread.getInstance();
	}
	
	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		
		for (Player player: processing.getPlayerList()) {
			udpClient.setIA(player.getIp());
			udpClient.sendObject(new LevelInfoMessage(LevelInfoMessage.FINISHED,game.houseIndex + 1));
		}
		
		game.houseIndex++;
		
		
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
			if (game.houseIndex < game.houseList.size()) {
				game.setScreen(game.playingScreen);
			} else {
				game.setScreen(game.gameFinishedScreen);
			}			
		}
	}
	
	@Override
	public void hide() {
		for (Player player: processing.getPlayerList()) {
			udpClient.setIA(player.getIp());
			udpClient.sendObject(new LevelInfoMessage(LevelInfoMessage.STARTED,game.houseIndex + 1));
		}
	}

	

}
