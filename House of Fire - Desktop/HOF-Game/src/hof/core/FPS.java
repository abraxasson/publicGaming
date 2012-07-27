package hof.core;

import hof.core.utils.Assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FPS {
	
	long firstFrame;
	int frames;
	long currentFrame;
	int fps;
	
	BitmapFont font;
	
	public FPS() {
		firstFrame = System.currentTimeMillis();
		frames = 0;
		currentFrame = 0;
		fps = 0;
		font = Assets.text30Font;
	}

	public void draw(SpriteBatch spriteBatch) {
		 //nun in paint() / update() bzw. paintComponent() ...
		 
		  frames++;
		  currentFrame = System.currentTimeMillis();
		  if(currentFrame > firstFrame + 1000){
		     firstFrame = currentFrame;
		     fps = frames;
		     frames = 0;
		  }
		  font.setColor(Color.BLACK);
		  font.draw(spriteBatch, fps + " fps", Assets.TIMELINE_WIDTH  + Assets.TIMELINE_WIDTH_OFFSET +10, Assets.RANKING_HEIGHT / 5);
	}
}