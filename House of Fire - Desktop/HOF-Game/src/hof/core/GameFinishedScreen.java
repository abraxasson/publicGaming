package hof.core;

import java.util.Random;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.HallOfFame;
import hof.net.MessageProcessing;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameFinishedScreen extends GameScreen<HouseOfFireGame> {

	private long startTime;
	private MessageProcessing processing;
	private HallOfFame fame;
	private ParticleEffect fireworkParticles;
	private TextureRegion[] currentFireworks;
	
	private float stateTime;
	private boolean started;
	private float height;
	
	private Random rand;
	
	private float delay;
	private ParticleEmitter pm;
	
	public GameFinishedScreen(HouseOfFireGame game) {
		super(game);
		processing = MessageProcessing.getInstance();
		fame = HallOfFame.getInstance();
		fireworkParticles = Assets.loadFireWorksParticles();
		pm = fireworkParticles.getEmitters().get(0);
		pm.setPosition(Assets.FRAME_WIDTH / 2, 0);
		
		float var = 250;
		pm.getLife().setHigh(Assets.FRAME_HEIGHT + var);
		pm.getLife().setLow(Assets.FRAME_HEIGHT - var);

		height = pm.getVelocity().getLowMin() * pm.getLife().getHighMin() / 1000;
		delay = height / pm.getVelocity().getHighMin() + (pm.getDelay().getLowMax() / 1000);
		rand = new Random();
		currentFireworks = new TextureRegion[4];
	}

	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		
		for (Player player: processing.getPlayerList()) {
			fame.addPlayer(player);
		}
		processing.getPlayerList().clear();
		stateTime = 0;
		started = false;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClearColor(0.11f, 0.48f, 0.98f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		stateTime += delta;
		if (stateTime >= delay && !started) {
			started = true;
			stateTime = 0;
		}
		if (started) {
			for (int i = 0; i < currentFireworks.length; i++) {
				currentFireworks[i] = Assets.fireWorksAnimation.getKeyFrame(stateTime, true);
			}
		}
		
		spriteBatch.begin();
		fireworkParticles.draw(spriteBatch, delta);
		if (started) {
			float x = rand.nextInt(200) + (Assets.FRAME_WIDTH / 2 - 200);
			float y = rand.nextInt(50) + height;
			for (TextureRegion region: currentFireworks) {
				spriteBatch.draw(region, x, y, 200, 200);

				x = rand.nextInt(200) + (Assets.FRAME_WIDTH / 2 - 200);
				y = rand.nextInt(50) + height;
			}
		}
		
		spriteBatch.draw(Assets.gameFinishedScreen, 0, 0, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT);
		Assets.text50Font.draw(spriteBatch, "Game finished", Assets.CANVAS_WIDTH / 2, Assets.CANVAS_HEIGHT / 2);
		spriteBatch.end();
		
		if (System.currentTimeMillis() - startTime >= (pm.getDuration().getLowMax() + pm.getDelay().getLowMax()) * 2) {
			game.houseIndex = 0;
			game.setScreen(game.waitingForPlayersScreen);
		}
	}
}
