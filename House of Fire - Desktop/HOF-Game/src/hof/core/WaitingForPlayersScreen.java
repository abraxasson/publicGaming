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
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class WaitingForPlayersScreen extends GameScreen<HouseOfFireGame> {

	private BitmapFont font;
	private SimpleButton returnButton;
	
	@SuppressWarnings("unused")
	private UdpServerThread udpServer;
	private MessageProcessing processing;
	
	private HallOfFame fame;
	
	private boolean isWaiting;
    private boolean wasTouched;
	
	public WaitingForPlayersScreen(HouseOfFireGame game) {
		super(game);
		udpServer = UdpServerThread.getInstance();
		processing = MessageProcessing.getInstance();
		
		font = Assets.text50Font;
		font.setColor(0, 0, 0, 1);
		
		returnButton = new SimpleButton("Return", Assets.text50Font, Color.BLACK);
		returnButton.centerHorizontallyOn(Gdx.graphics.getWidth() / 2);
		returnButton.topOn(Gdx.graphics.getHeight() / 8);
		
		fame = HallOfFame.getInstance();
	}

	
	@Override
	public void show(){
		isWaiting = true;
	}

	
	@Override
	public void render(float delta) {
		if (isWaiting) {
			updateButtons(delta);
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE) || returnButton.wasPressed()) {
				game.setScreen(game.mainMenuScreen);
			}
			
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
			
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				InetAddress ia;
				try {
					ia = InetAddress.getLocalHost();
					UdpClientThread.getInstance().sendObject(new PlayerInfoMessage("Florian"), ia);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
			
			processing.processMessageQueue();
			spriteBatch.begin();
			fame.draw(spriteBatch);
			String text = "Waiting for Players";
			font.draw(spriteBatch, text, Gdx.graphics.getWidth()/2 - font.getBounds(text).width /2, Gdx.graphics.getHeight() / 4);
			returnButton.draw(spriteBatch);
			spriteBatch.end();
			
			if (!processing.getPlayerList().isEmpty()) {
				isWaiting = false;
			}
		} else {
			game.setScreen(game.playingScreen);
		}	
		
	}
	
	private void updateButtons(float delta) {
		touchPoint = screenToViewport(Gdx.input.getX(), Gdx.input.getY());
        boolean justTouched = Gdx.input.justTouched();
        boolean isTouched = Gdx.input.isTouched();
        boolean justReleased = wasTouched && !isTouched;
        wasTouched = isTouched;

		returnButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
	}
}
