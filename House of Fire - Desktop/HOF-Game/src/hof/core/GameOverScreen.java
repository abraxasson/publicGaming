package hof.core;



import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.HallOfFame;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.GameOverInfoMessage;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class GameOverScreen extends GameScreen<HouseOfFireGame> {

	private long startTime;
	private HallOfFame fame;
	private MessageProcessing processing; 
	
	public GameOverScreen(HouseOfFireGame game) {
		super(game);
		fame = HallOfFame.getInstance();
	}
	
	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		
		processing = MessageProcessing.getInstance();
		UdpClientThread udpClient = UdpClientThread.getInstance();
		for (Player player: processing.getPlayerList()) {
			fame.addPlayer(player);
			udpClient.sendObject(new GameOverInfoMessage(), player.getIp());
		}
	}
	
	@Override
	public void hide() {
		processing.getPlayerList().clear();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		spriteBatch.begin();
		spriteBatch.draw(Assets.GameOverScreen, 0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.WHITE);
		fame.draw(spriteBatch, (int)(Gdx.graphics.getWidth()*0.8), (int)(Gdx.graphics.getHeight()*0.60),Assets.highscore40Font);
		spriteBatch.setColor(oldColor);
		spriteBatch.end();
		
		if (System.currentTimeMillis() - startTime >= 10000l) {
			game.houseIndex = 0;
			game.setScreen(game.waitingForPlayersScreen);
		}
	}
}
