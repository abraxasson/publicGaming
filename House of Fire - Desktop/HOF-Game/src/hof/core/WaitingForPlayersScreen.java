package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.HallOfFame;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.PlayerInfoMessage;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class WaitingForPlayersScreen extends GameScreen<HouseOfFireGame> {

	private MessageProcessing processing;
	
	private HallOfFame fame;
	
	private boolean isWaiting;
    
    private float stateTime;
	
	public WaitingForPlayersScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		
		fame = HallOfFame.getInstance();
	}

	
	@Override
	public void show(){
		isWaiting = true;
		stateTime = 0;
	}

	
	@Override
	public void render(float delta) {
		if (isWaiting) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE) ) {
				game.setScreen(game.mainMenuScreen);
			}
			
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
			
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				InetAddress ia;
				try {
					ia = InetAddress.getLocalHost();
					UdpClientThread.getInstance().sendMessage(new PlayerInfoMessage("Florian"), ia);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
			
			processing.processMessageQueue();
			
			stateTime += delta;
			if (stateTime > 5.0) {
//				Gdx.app.exit();
			}
			spriteBatch.begin();
			spriteBatch.draw(Assets.waitingForPlayerTitle,Assets.FRAME_WIDTH / 2 - Assets.waitingForPlayerTitle.getWidth()/2 ,Assets.FRAME_HEIGHT /2 - Assets.waitingForPlayerTitle.getHeight()/2);
//			fame.draw(spriteBatch, Assets.text50Font, Color.BLACK);
			spriteBatch.end();
			
			if (!processing.getPlayerList().isEmpty()) {
				isWaiting = false;
			}
		} else {
			game.setScreen(game.playingScreen);
		}	
		
	}
}
