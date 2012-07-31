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
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameFinishedScreen extends GameScreen<HouseOfFireGame> {

	private long startTime;
	private MessageProcessing processing;
	private HallOfFame fame;
	private ParticleEffect fireworkParticles;
	private TextureRegion currentFirework;
	private float stateTime;
	
	public GameFinishedScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		fame = HallOfFame.getInstance();
		fireworkParticles = Assets.loadFireWorksParticles();
		fireworkParticles.getEmitters().get(0).setPosition(Assets.FRAME_WIDTH / 2, 0);
	}
	
	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		
		for (Player player: processing.getPlayerList()) {
			fame.addPlayer(player);
		}
		processing.getPlayerList().clear();
		stateTime = 0;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClearColor(0.11f, 0.48f, 0.98f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		stateTime += delta;
		currentFirework = Assets.fireWorksAnimation.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		fireworkParticles.draw(spriteBatch, delta);
		spriteBatch.draw(currentFirework, (int)(Math.random() * 10) + Assets.FRAME_WIDTH / 2 - 10,(int) Math.random() * 10 + Assets.FRAME_HEIGHT* 9/10);
		spriteBatch.draw(Assets.gameFinishedScreen, 0, 0, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT);
		Assets.text50Font.draw(spriteBatch, "Game finished", Assets.CANVAS_WIDTH / 2, Assets.CANVAS_HEIGHT / 2);
		spriteBatch.end();
		
		if (System.currentTimeMillis() - startTime >= 4000l) {
			game.houseIndex = 0;
			game.setScreen(game.waitingForPlayersScreen);
		}
	}
}
