package hof.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.LevelFinishedInfoMessage;
import hof.player.Player;

public class LevelFinishedScreen extends GameScreen<HouseOfFireGame> {

	
	public LevelFinishedScreen(HouseOfFireGame game) {
		super(game);
		
	}
	
	@Override
	public void show() {
		MessageProcessing processing = MessageProcessing.getInstance();
		UdpClientThread udpClient = UdpClientThread.getInstance();
		for (Player player: processing.getPlayerList()) {
			udpClient.setIA(player.getIp());
			udpClient.sendObject(new LevelFinishedInfoMessage(1));
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
		Assets.text50Font.draw(spriteBatch, "Level finished", Assets.CANVAS_WIDTH / 2, Assets.CANVAS_HEIGHT / 2);
		spriteBatch.end();
	}

	

}
