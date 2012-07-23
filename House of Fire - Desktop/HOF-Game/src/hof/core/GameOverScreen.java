package hof.core;



import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.net.UdpClientThread;
import hof.net.userMessages.GameFinishedInfoMessage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class GameOverScreen extends GameScreen<HouseOfFireGame> {

	private long startTime;
	
	public GameOverScreen(HouseOfFireGame game) {
		super(game);
		
	}
	
	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		UdpClientThread.getInstance().sendObject(new GameFinishedInfoMessage(false));
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
			Gdx.app.exit();
		}
	}
}
