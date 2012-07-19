package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StatusBar {

	private Texture texture;
	private int x;
	private int y;
	private Ranking ranking;
	
	public StatusBar()  {
		texture = Assets.pureWhiteTextureRegion.getTexture();
		x = Assets.TIMELINE_WIDTH;
		y= 0;
		ranking = new Ranking();
	}
	
	public void draw (SpriteBatch spriteBatch) {
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(texture, x, y, Assets.STATUS_BAR_WIDTH, Assets.STATUS_BAR_HEIGHT, 0, 0, 8, 8, false, false);
		spriteBatch.setColor(oldColor);
		ranking.draw(spriteBatch);
	}
	
	
	class Ranking {
		
		private BitmapFont font;
		private String heading;
		@SuppressWarnings("unused")
		private String ranking;
		private int x;
		private int y;
		private int wrap;
		
		public Ranking() {
			font = Assets.textFont;
			heading = "Active Players: ";
			ranking = "";
			x = Assets.TIMELINE_WIDTH + 10;
			y = Assets.RANKING_HEIGHT;
			wrap = Assets.RANKING_WIDTH;
			
		}
		
		public void draw (SpriteBatch spriteBatch) {
			font.setColor(Color.BLACK);
			font.drawWrapped(spriteBatch, heading, x, y, wrap);
			
		}
		
		public void updateText() {
			
			ranking = "";
		}
	}
}
