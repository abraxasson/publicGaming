package hof.level.objects;

import hof.core.utils.Assets;
import hof.net.MessageProcessing;
import hof.player.Player;

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
		
		private MessageProcessing processing;
		private BitmapFont font;
		private BitmapFont font2;
		private String heading;
		private String ranking;
		private String players;
		private int x;
		private int y;
		private int wrap;
		
		public Ranking() {
			processing = MessageProcessing.getInstance();
			font = Assets.text45Font;
			font2 = Assets.text30Font;
			heading = "Active Players: ";
			ranking = "";
			players = "0/6";
			x = Assets.TIMELINE_WIDTH + 10;
			y = Assets.RANKING_HEIGHT;
			wrap = Assets.RANKING_WIDTH;
			
		}
		
		public void draw (SpriteBatch spriteBatch) {
			updateText();
			font.setColor(Color.BLACK);
			font.drawWrapped(spriteBatch, players, x, y, wrap);
			font2.setColor(Color.BLACK);
			font2.drawWrapped(spriteBatch, heading, x, y - 40, wrap);
			font2.drawMultiLine(spriteBatch, ranking, x, 300);
		}
		
		public void updateText() {
			players = processing.getPlayerList().size() + "/6";
			ranking = "";
			for (Player player: processing.getPlayerList()) {
				ranking += player.getName() + " " + player.getScore() + "\n";
			}
		}
	}
}
