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
	private MessageProcessing messageProcessing;
	private HallOfFame fame;
	private ParticleEffect fireworkParticles;
	private ParticleEmitter particleEmitter;
	
	private TextureRegion currentFirework;
	
	private float stateTime;
	private boolean started;
	private float height;
	private Sprite trophy;
	
	private Random rand;
	
	private float delay;
	
	public GameFinishedScreen(HouseOfFireGame game) {
		super(game);
		messageProcessing = MessageProcessing.getInstance();
		fame = HallOfFame.getInstance();
		fireworkParticles = Assets.loadFireWorksParticles();
		particleEmitter = fireworkParticles.getEmitters().get(0);
		particleEmitter.setPosition(Assets.FRAME_WIDTH / 2, 0);
		
		float var = 200;
		particleEmitter.getLife().setHigh(Assets.FRAME_HEIGHT + var);
		particleEmitter.getLife().setLow(Assets.FRAME_HEIGHT - var);

		height = particleEmitter.getVelocity().getLowMin() * particleEmitter.getLife().getHighMin() / 1000;
		delay = height / particleEmitter.getVelocity().getHighMin() + (particleEmitter.getDelay().getLowMax() / 1000);
		rand = new Random();
		
		trophy = new Sprite(Assets.trophyTexture);
		trophy.setX(Assets.FRAME_WIDTH/2 - trophy.getWidth()/2);
		trophy.setY(Assets.FRAME_HEIGHT/3);
	}

	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		Assets.fanfare.play();
		Assets.firework.loop();
		
		for (Player player: messageProcessing.getPlayerList()) {
			fame.addPlayer(player);
		}
		messageProcessing.clearPlayerList();
		stateTime = 0;
		started = false;
	}
	
	@Override
	public void hide(){
		Assets.firework.stop();
		Assets.firework2.stop();
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
			Assets.firework2.loop();
		}
		if (started) {
			currentFirework = Assets.fireWorksAnimation.getKeyFrame(stateTime, true);
		}
		
		spriteBatch.begin();
		particleEmitter.setPosition(Assets.FRAME_WIDTH / 4, 0);
		fireworkParticles.draw(spriteBatch, delta);
		particleEmitter.setPosition(Assets.FRAME_WIDTH * 0.75f, 0);
		fireworkParticles.draw(spriteBatch, delta);
		if (started) {
			drawFireworkAnimation();
			
		}
		
		spriteBatch.draw(Assets.gameFinishedScreen, 0, 0, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT);
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.BLACK);
		int offset = 3;
		spriteBatch.draw(Assets.trophyTexture, trophy.getX() - offset, trophy.getY() + offset, trophy.getWidth(), trophy.getHeight());
		spriteBatch.setColor(oldColor);
		
		trophy.draw(spriteBatch);
		spriteBatch.draw(Assets.gameFinishedText, (Assets.FRAME_WIDTH - Assets.gameFinishedText.getWidth())/2, Assets.FRAME_HEIGHT * 9/10 - Assets.gameFinishedText.getHeight()/2);
		spriteBatch.end();
		
		if (System.currentTimeMillis() - startTime >= (particleEmitter.getDuration().getLowMax() + particleEmitter.getDelay().getLowMax()) * 2) {
			game.houseIndex = 0;
			game.setScreen(game.waitingForPlayersScreen);
		}
	}

	private void drawFireworkAnimation() {
		float size = 200;
		float x = 0;
		float y = rand.nextInt(50) + height;
		
		spriteBatch.draw(currentFirework, x + (Assets.FRAME_WIDTH / 4 - 100), y, size, size);
		spriteBatch.draw(currentFirework, x + (Assets.FRAME_WIDTH / 4 - 200), y, size, size);
		spriteBatch.draw(currentFirework, x + (Assets.FRAME_WIDTH / 4 + 25), y, size, size);
		spriteBatch.draw(currentFirework, x + (int)(Assets.FRAME_WIDTH * 0.75 - 100), y, size, size);
		spriteBatch.draw(currentFirework, x + (int)(Assets.FRAME_WIDTH * 0.75 - 200), y, size, size);
		spriteBatch.draw(currentFirework, x + (int)(Assets.FRAME_WIDTH * 0.75 + 25), y, size, size);
	}
}
