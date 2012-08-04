package hof.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;
import hof.core.utils.HallOfFame;
import hof.level.objects.Pixel;
import hof.net.MessageProcessing;
import hof.net.UdpClientThread;
import hof.net.userMessages.GameOverInfoMessage;
import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class GameOverScreen extends GameScreen<HouseOfFireGame> {

	private long startTime;
	private HallOfFame fame;
	private MessageProcessing processing;
	private ParticleEffect[] smokeArray;
	private ArrayList<Pixel> smokingArea;
	private static int  minRankingHeight = 1000000;
	private static int minRankingWidth = 1000000;

	public GameOverScreen(HouseOfFireGame game) {
		super(game);
		smokingArea = new ArrayList<Pixel>();
		this.setSmokingArea(Assets.smokingArea);
		this.setMinRankingArea(Assets.smokingArea);
		smokeArray = new ParticleEffect[5];
		Pixel pixel;
		for(int i = 0;i<smokeArray.length;i++){
			smokeArray[i] = Assets.loadSmokeParticles();
			pixel = this.getRandomSmokingArea();
			smokeArray[i].getEmitters().get(0).setPosition(pixel.getX(), pixel.getY());
		}
		fame = HallOfFame.getInstance();
	}

	@Override
	public void show() {
		processing = MessageProcessing.getInstance();
		UdpClientThread udpClient = UdpClientThread.getInstance();
		for (Player player : processing.getPlayerList()) {
			fame.addPlayer(player);
			udpClient.sendMessage(new GameOverInfoMessage(), player.getIp());
		}
		startTime = System.currentTimeMillis();
		Assets.loser.play(0.5f);
		Assets.evilLaugh.play();
	}

	@Override
	public void hide() {
		processing.clearPlayerList();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		spriteBatch.begin();
		spriteBatch.draw(Assets.gameOverScreen, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		fame.draw(spriteBatch, (int) (Gdx.graphics.getWidth() * 0.8),
				(int) (Gdx.graphics.getHeight() * 0.60), getMinRankingHeight(),
				Assets.standardFont40, com.badlogic.gdx.graphics.Color.WHITE);
		for(ParticleEffect smoke : smokeArray){
			smoke.draw(spriteBatch,Gdx.graphics.getDeltaTime());
		}
		spriteBatch.end();

		if (System.currentTimeMillis() - startTime >= 10000l) {
			game.houseIndex = 0;
			game.setScreen(game.waitingForPlayersScreen);
		}
	}

	private Pixel getRandomSmokingArea() {
		Pixel pixel = smokingArea
				.get((int) (Math.random() * smokingArea.size()));
		return pixel;
	}

	private void setMinRankingArea(BufferedImage img){
		Color c = Color.WHITE;
		double verhaeltnisX = ((double) Gdx.graphics.getWidth() / ((double) img
				.getWidth()));
		double verhaeltnisY = ((double) Gdx.graphics.getHeight() / ((double) img
				.getHeight()));
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb);
				if (color.toString().equals(c.toString())) {
					int x1 = (int) (x * verhaeltnisX);
					int y1 = (int) ((img.getHeight() - y) * verhaeltnisY);
					if(minRankingHeight > y1){
						minRankingHeight = y1;
					}
					else if(minRankingWidth < x1){
						minRankingWidth = x1;
					}
				}
			}
		}
	}
	
	private void setSmokingArea(BufferedImage img) {
		Color c = Color.BLACK;
		double verhaeltnisX = ((double) Gdx.graphics.getWidth() / ((double) img
				.getWidth()));
		double verhaeltnisY = ((double) Gdx.graphics.getHeight() / ((double) img
				.getHeight()));
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb);
				if (color.toString().equals(c.toString())) {
					int x1 = (int) (x * verhaeltnisX);
					int y1 = (int) ((img.getHeight() - y) * verhaeltnisY);
					Pixel pixel = new Pixel(x1, y1, color);
					smokingArea.add(pixel);
				}
			}
		}
	}
	
	public static int getMinRankingWidth(){
		return minRankingWidth;
	}
	
	public static int getMinRankingHeight(){
		return minRankingHeight;
	}
}
