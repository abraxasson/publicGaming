package hof.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameScreen<HouseOfFire> implements Screen {

	protected HouseOfFire game;
	protected OrthographicCamera  menuCam;
	protected Vector3 touchPoint;
	protected SpriteBatch spriteBatch;
	
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

	@Override
	public void render(float delta) {	}

	@Override
	public void resize(int width, int height) {	}

	@Override
	public void show() {	}

	@Override
	public void hide() {	}

	@Override
	public void pause() {	}

	@Override
	public void resume() {	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

}
