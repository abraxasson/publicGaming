package hof.level.objects;

import hof.core.utils.Assets;
import hof.core.utils.Settings;
import hof.net.MessageProcessing;
import hof.player.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StatusBar {

	private Texture texture;
	private int x;
	private int y;
	private Ranking ranking;
	private SMS sms;
	
	
	public StatusBar()  {
		texture = Assets.pureWhiteTextureRegion;
		x = Assets.TIMELINE_WIDTH + (Assets.TIMELINE_WIDTH_OFFSET);
		y= 0;
		ranking = new Ranking();
		sms = new SMS();
	}
	
	public void draw (SpriteBatch spriteBatch) {
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(texture, x, y, Assets.STATUS_BAR_WIDTH, Assets.STATUS_BAR_HEIGHT, 0, 0, 8, 8, false, false);
		spriteBatch.setColor(oldColor);
		sms.draw(spriteBatch);
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
			players = "0/" + Settings.maxPlayers;
			x = Assets.TIMELINE_WIDTH + Assets.TIMELINE_WIDTH_OFFSET + 10;
			y = Assets.RANKING_HEIGHT;
			wrap = Assets.STATUS_BAR_WIDTH;
			
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
	
	class SMS {
		
		private BitmapFont font;
		private BitmapFont font2;
		private String heading;
		private String telephoneNumber;
		private String lightningEffect;
		private String rainEffect;
		private String pressureEffect;
		private int x;
		private int y;
		
		public SMS() {
			font = Assets.text45Font;
			font2 = Assets.text30Font;
			heading = "SMS-Effects: ";
			lightningEffect = Settings.lightningKeyWord;
			rainEffect = Settings.rainKeyWord;
			pressureEffect = Settings.pressureKeyWord;
			telephoneNumber = Settings.TELEPHONE_NUMBER;
			x = Assets.TIMELINE_WIDTH + Assets.TIMELINE_WIDTH_OFFSET + 10;
			y = Assets.STATUS_BAR_HEIGHT;
		}
		
		public void draw (SpriteBatch spriteBatch) {
			y = Assets.STATUS_BAR_HEIGHT;
			font.setColor(Color.BLACK);
			font.draw(spriteBatch, heading, x, y);
			y -= 80;
			font2.setColor(Color.BLACK);
			
			TextBounds bounds;
			bounds = font2.getBounds(lightningEffect);
			spriteBatch.draw(Assets.pureWhiteTextureRegion, x, y, bounds.width, bounds.height, 0, 0, 8, 8, false, false);
			font2.draw(spriteBatch, lightningEffect, x, y);
			
			y -= 80;
			bounds = font2.getBounds(rainEffect);
			spriteBatch.draw(Assets.pureWhiteTextureRegion, x, y, bounds.width, bounds.height, 0, 0, 8, 8, false, false);
			font2.draw(spriteBatch, rainEffect, x, y);
			
			y -= 80;
			bounds = font2.getBounds(pressureEffect);
			spriteBatch.draw(Assets.pureWhiteTextureRegion, x, y, bounds.width, bounds.height, 0, 0, 8, 8, false, false);
			font2.draw(spriteBatch, pressureEffect, x, y);
			
			y -= 80;
			font2.draw(spriteBatch, telephoneNumber, x, y);
		}
		
	}
}
