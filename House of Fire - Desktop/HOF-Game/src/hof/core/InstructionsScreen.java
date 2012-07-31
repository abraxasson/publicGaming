package hof.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import hof.core.utils.Assets;
import hof.core.utils.GameScreen;

public class InstructionsScreen extends GameScreen<HouseOfFireGame> {

	private SimpleButton returnButton;
	private boolean wasTouched;
	
	public InstructionsScreen(HouseOfFireGame game) {
		super(game);
		returnButton = new SimpleButton("Return", Assets.menu45Font, Color.BLACK);
		returnButton.centerHorizontallyOn(Gdx.graphics.getWidth()/2);
		returnButton.centerVerticallyOn(Gdx.graphics.getHeight()/2);
	}
	
	@Override
	public void render(float delta) {
		updateButtons(delta);
		Gdx.gl20.glClearColor(1, 1, 1, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (returnButton.wasPressed()) {
			game.setScreen(game.mainMenuScreen);
		}
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		spriteBatch.begin();
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

}
