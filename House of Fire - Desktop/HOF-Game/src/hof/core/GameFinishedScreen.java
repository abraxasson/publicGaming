package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.HallOfFame;
import hof.net.MessageProcessing;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class GameFinishedScreen extends GameScreen<HouseOfFireGame> {

	private long startTime;
	private MessageProcessing processing;
	private HallOfFame fame;
	private ParticleEffect fireworks;
	
	public GameFinishedScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		fame = HallOfFame.getInstance();
		fireworks = Assets.loadFireWorksParticles();
		fireworks.getEmitters().get(0).setPosition(Assets.FRAME_WIDTH / 2, 0);
	}
	
	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		
		for (Player player: processing.getPlayerList()) {
			fame.addPlayer(player);
		}
		processing.getPlayerList().clear();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClearColor(0.11f, 0.48f, 0.98f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		spriteBatch.begin();
		fireworks.draw(spriteBatch, Gdx.graphics.getDeltaTime());
		spriteBatch.draw(Assets.gameFinishedScreen, 0, 0, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT);
		Assets.text50Font.draw(spriteBatch, "Game finished", Assets.CANVAS_WIDTH / 2, Assets.CANVAS_HEIGHT / 2);
		spriteBatch.end();
		
		if (System.currentTimeMillis() - startTime >= 4000l) {
			game.houseIndex = 0;
			game.setScreen(game.waitingForPlayersScreen);
		}
	}
}
