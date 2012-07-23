package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class MainMenuScreen extends GameScreen<HouseOfFireGame> {

	
	private SimpleButton playButton;
	private SimpleButton instructionsButton;
	private SimpleButton settingsButton;
	private SimpleButton highScoreButton;
	private SimpleButton endButton;
	
    private boolean wasTouched;
    
	public MainMenuScreen(HouseOfFireGame game) {
		super(game);		
		createButtons();
	}

	private void createButtons() {
		int distance = 20;
		
		playButton = new SimpleButton("Play", Assets.menu45Font, Color.WHITE);
		playButton.centerHorizontallyOn(Gdx.graphics.getWidth()/2);
		playButton.centerVerticallyOn(Gdx.graphics.getHeight()/2);
		
		float posY = playButton.getY() - distance;
		
		settingsButton = new SimpleButton("Settings", Assets.menu45Font, Color.WHITE);
		settingsButton.centerHorizontallyOn(Gdx.graphics.getWidth()/2);
		settingsButton.topOn(posY);
		
		posY = settingsButton.getY() - distance;
		
		instructionsButton = new SimpleButton("Instructions", Assets.menu45Font, Color.WHITE);
		instructionsButton.centerHorizontallyOn(Gdx.graphics.getWidth()/2);
		instructionsButton.topOn(posY);
		
		posY = instructionsButton.getY() - distance;		
		
		highScoreButton = new SimpleButton("Highscore", Assets.menu45Font, Color.WHITE);
		highScoreButton.centerHorizontallyOn(Gdx.graphics.getWidth()/2);
		highScoreButton.topOn(posY);
		
		posY = highScoreButton.getY() - distance;

		endButton = new SimpleButton("End", Assets.menu45Font, Color.WHITE);
		endButton.centerHorizontallyOn(Gdx.graphics.getWidth()/2);
		endButton.topOn(posY);
	}

	@Override
	public void render(float delta) {
		updateButtons(delta);
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Keys.SPACE) || playButton.wasPressed()) {
			game.setScreen(game.waitingForPlayersScreen);
		}

		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			startGame();
		}
		
		if (instructionsButton.wasPressed()) {
			System.out.println("Anleitung");
		}
		
		if (settingsButton.wasPressed()) {
			System.out.println("Einstellungen");
		}
		
		if (highScoreButton.wasPressed()) {
			System.out.println("Highscore");
		}

		if (Gdx.input.isKeyPressed(Keys.ESCAPE) || endButton.wasPressed()) {
			Gdx.app.exit();
		}

		
		spriteBatch.begin();
		spriteBatch.draw(Assets.mainMenu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0,0,3500,2000,false,false);
		drawButtons();
		spriteBatch.end();

	}
	
	private void startGame() {
		game.setScreen(game.playingScreen);
	}

	private void updateButtons(float delta) {
		touchPoint = screenToViewport(Gdx.input.getX(), Gdx.input.getY());
        boolean justTouched = Gdx.input.justTouched();
        boolean isTouched = Gdx.input.isTouched();
        boolean justReleased = wasTouched && !isTouched;
        wasTouched = isTouched;

		playButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
		instructionsButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
		settingsButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
		highScoreButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
		endButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
	}
	
	private void drawButtons() {
		playButton.draw(spriteBatch);
		instructionsButton.draw(spriteBatch);
		settingsButton.draw(spriteBatch);
		highScoreButton.draw(spriteBatch);
		endButton.draw(spriteBatch);
	}
}
