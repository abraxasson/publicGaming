package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class WaitingForPlayersScreen extends GameScreen<HouseOfFireGame> {

	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private SimpleButton returnButton;
	
	private Vector3 touchPoint;
    private boolean wasTouched;
	private OrthographicCamera  menuCam;
	
	public WaitingForPlayersScreen(HouseOfFireGame game) {
		super(game);
		font = new BitmapFont();
		font.setColor(0, 0, 0, 1);
		
		menuCam = new OrthographicCamera();
		menuCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(menuCam.combined);
		touchPoint = new Vector3();
		
		returnButton = new SimpleButton("Return", Assets.textFont, Color.BLACK);
		returnButton.centerHorizontallyOn(Gdx.graphics.getWidth() / 2);
		returnButton.topOn(300);
	}


	
	@Override
	public void render(float delta) {
		updateButtons(delta);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE) || returnButton.wasPressed()) {
			game.setScreen(game.mainMenuScreen);
		}
		
		spriteBatch.begin();
		font.draw(spriteBatch, "Waiting for Players", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		returnButton.draw(spriteBatch);
		spriteBatch.end();
	}
	
	private void updateButtons(float delta) {
		touchPoint = screenToViewport(Gdx.input.getX(), Gdx.input.getY());
        boolean justTouched = Gdx.input.justTouched();
        boolean isTouched = Gdx.input.isTouched();
        boolean justReleased = wasTouched && !isTouched;
        wasTouched = isTouched;

		returnButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
	}
	
	private Vector3 screenToViewport (float x, float y) {
        menuCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        return touchPoint;
	}
}
