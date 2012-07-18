package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends GameScreen<HouseOfFireGame> {

	private SpriteBatch spriteBatch;
	private SimpleButton playButton;
	private SimpleButton endButton;
	private Vector3 touchPoint;
    private boolean wasTouched;
	private OrthographicCamera  menuCam;

	
	public MainMenuScreen(HouseOfFireGame game) {
		super(game);
		menuCam = new OrthographicCamera();
		menuCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(menuCam.combined);
		touchPoint = new Vector3();
		createButtons();
	}

	private void createButtons() {
		playButton = new SimpleButton("Play", Assets.textFont, Color.WHITE);
		playButton.centerHorizontallyOn(Gdx.graphics.getWidth()/2);
		playButton.centerVerticallyOn(Gdx.graphics.getHeight()/2);
		
		float posX = playButton.getX();
		float posY = playButton.getY() - 40;
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
		endButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
	}
	
	private void drawButtons() {
		playButton.draw(spriteBatch);
		endButton.draw(spriteBatch);
	}
	
	private Vector3 screenToViewport (float x, float y) {
        menuCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        return touchPoint;
	}

}
