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
		
		playButton = new SimpleButton("Play", Assets.textFont, Color.WHITE);
		playButton.centerHorizontallyOn(Gdx.graphics.getWidth()/2);
		playButton.centerVerticallyOn(Gdx.graphics.getHeight()/2);
		
		float posX = playButton.getX();
		float posY = playButton.getY() - 40;
		
		instructionsButton = new SimpleButton("Instructions", Assets.textFont, Color.WHITE);
		instructionsButton.leftOn(posX);
		instructionsButton.topOn(posY);
		
		posY -= 40;
		
		settingsButton = new SimpleButton("Settings", Assets.textFont, Color.WHITE);
		settingsButton.leftOn(posX);
		settingsButton.topOn(posY);
		
		posY -= 40;
		
		highScoreButton = new SimpleButton("Highscore", Assets.textFont, Color.WHITE);
		highScoreButton.leftOn(posX);
		highScoreButton.topOn(posY);
		
		posY -= 40;

		endButton = new SimpleButton("End", Assets.textFont, Color.WHITE);
		endButton.leftOn(posX);
		endButton.topOn(posY);
	}

	@Override
	public void render(float delta) {
		updateButtons(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
			System.exit(0);
		}


		
		spriteBatch.begin();
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
