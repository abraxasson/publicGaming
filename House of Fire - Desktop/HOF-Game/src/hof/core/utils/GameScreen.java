package hof.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * This class is the basis for all Screens.
 * By inheriting from this class the other Screen do not have to implement all methods from the Screen-Interface. <br>
 * This class also manages the SpriteBatch, the Vector3 and the OrthographicCamera (for the SimpleButton).
 *
 * @param <HouseOfFire>
 */
public abstract class GameScreen<HouseOfFire> implements Screen {

	/**
	 * The main game.
	 */
	protected HouseOfFire game;
	/**
	 * The camera for the screen.
	 */
	protected OrthographicCamera  menuCam;
	/**
	 * Vector for the SimpleButton.
	 */
	protected Vector3 touchPoint;
	/**
	 * The SpriteBatch to draw all things.
	 */
	protected SpriteBatch spriteBatch;
	
	/**
	 * @param the current game
	 */
	public GameScreen(HouseOfFire game) {
		this.game = game;
		menuCam = new OrthographicCamera();
		menuCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(menuCam.combined);
		touchPoint = new Vector3();
	}
	
	protected Vector3 screenToViewport (float x, float y) {
        menuCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        return touchPoint;
	}

	
	/**
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {	}

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {	}

	/**
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() { }

	/**
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() { }

	/**
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() { }

	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() { }

	/** 
	 * Disposes the SpriteBatch.
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

}
