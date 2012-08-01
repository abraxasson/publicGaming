package hof.core;

import java.util.Random;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.HallOfFame;
import hof.net.MessageProcessing;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	private Sprite trophy;
	
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
		currentFireworks = new TextureRegion[1];
		
		trophy = new Sprite(Assets.trophyTexture);
		trophy.setX(Assets.FRAME_WIDTH/2 - trophy.getWidth()/2);
		trophy.setY(Assets.FRAME_HEIGHT/3);
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
		pm.setPosition(Assets.FRAME_WIDTH / 4, 0);
		fireworkParticles.draw(spriteBatch, delta);
		pm.setPosition(Assets.FRAME_WIDTH * 0.75f, 0);
		fireworkParticles.draw(spriteBatch, delta);
		if (started) {
			float x;
			float y;
			float size = 200;
			
			for (TextureRegion region: currentFireworks) {
				x = 0;//rand.nextInt(200);
				y = rand.nextInt(50) + height;
				spriteBatch.draw(region, x + (Assets.FRAME_WIDTH / 4 - 100), y, size, size);
				spriteBatch.draw(region, x + (Assets.FRAME_WIDTH / 4 - 200), y, size, size);
//				spriteBatch.draw(region, x + (Assets.FRAME_WIDTH / 4 - 300), y, size, size);
//				spriteBatch.draw(region, x + (Assets.FRAME_WIDTH / 4 + 100), y, size, size);
				spriteBatch.draw(region, x + (Assets.FRAME_WIDTH / 4 + 25), y, size, size);
//				spriteBatch.draw(region, x + (Assets.FRAME_WIDTH / 4 + 200), y, size, size);
				spriteBatch.draw(region, x + (int)(Assets.FRAME_WIDTH * 0.75 - 100), y, size, size);
				spriteBatch.draw(region, x + (int)(Assets.FRAME_WIDTH * 0.75 - 200), y, size, size);
//				spriteBatch.draw(region, x + (int)(Assets.FRAME_WIDTH * 0.75 - 300), y, size, size);
//				spriteBatch.draw(region, x + (int)(Assets.FRAME_WIDTH * 0.75 + 100), y, size, size);
				spriteBatch.draw(region, x + (int)(Assets.FRAME_WIDTH * 0.75 + 25), y, size, size);
//				spriteBatch.draw(region, x + (int)(Assets.FRAME_WIDTH * 0.75 + 200), y, size, size);
			}
		}
		
		spriteBatch.draw(Assets.gameFinishedScreen, 0, 0, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT);
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.BLACK);
		int offset = 3;
		spriteBatch.draw(Assets.trophyTexture, trophy.getX() - offset, trophy.getY() + offset, trophy.getWidth(), trophy.getHeight());
		spriteBatch.setColor(oldColor);
		
		trophy.draw(spriteBatch);
		spriteBatch.draw(Assets.gameFinishedText, (Assets.FRAME_WIDTH - Assets.gameFinishedText.getWidth())/2, Assets.FRAME_HEIGHT * 9/10 - Assets.gameFinishedText.getHeight());
		spriteBatch.end();
		
		if (System.currentTimeMillis() - startTime >= (pm.getDuration().getLowMax() + pm.getDelay().getLowMax()) * 2) {
			game.houseIndex = 0;
			game.setScreen(game.waitingForPlayersScreen);
		}
	}
}
