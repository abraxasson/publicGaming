package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class WaitingForPlayersScreen extends GameScreen<HouseOfFireGame> {

	private BitmapFont font;
	private SimpleButton nextButton;
	private SimpleButton returnButton;
	
    private boolean wasTouched;
	
	public WaitingForPlayersScreen(HouseOfFireGame game) {
		super(game);
		font = new BitmapFont();
		font.setColor(0, 0, 0, 1);
		
		
		nextButton = new SimpleButton("Next", Assets.textFont, Color.BLACK);
		nextButton.centerHorizontallyOn(Gdx.graphics.getWidth() / 2);
		nextButton.topOn(350);
		
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
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			System.exit(0);
		}
		
		if (nextButton.wasPressed()) {
			game.setScreen(game.playingScreen);
		}
		
		spriteBatch.begin();
		font.draw(spriteBatch, "Waiting for Players", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		nextButton.draw(spriteBatch);
		returnButton.draw(spriteBatch);
		spriteBatch.end();
	}
	
	private void updateButtons(float delta) {
		touchPoint = screenToViewport(Gdx.input.getX(), Gdx.input.getY());
        boolean justTouched = Gdx.input.justTouched();
        boolean isTouched = Gdx.input.isTouched();
        boolean justReleased = wasTouched && !isTouched;
        wasTouched = isTouched;

        nextButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
		returnButton.update(delta, justTouched, isTouched, justReleased, touchPoint.x, touchPoint.y);
	}
}
