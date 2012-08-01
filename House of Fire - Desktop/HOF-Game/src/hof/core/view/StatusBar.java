package hof.core.view;

import hof.core.utils.Assets;
import hof.core.utils.Settings;
import hof.level.effects.Lightning;
import hof.level.effects.Rain;
import hof.level.effects.WaterPressure;
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
	private SMS sms;

	public StatusBar() {
		texture = Assets.statusBar;
		x = Assets.TIMELINE_WIDTH + (Assets.TIMELINE_WIDTH_OFFSET);
		y = 0;
		ranking = new Ranking();
		sms = new SMS();
	}

	public void draw(SpriteBatch spriteBatch) {
		Color oldColor = spriteBatch.getColor();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(texture, x, y, Assets.STATUS_BAR_WIDTH,
				Assets.STATUS_BAR_HEIGHT);
		spriteBatch.setColor(oldColor);
		sms.draw(spriteBatch);
		ranking.draw(spriteBatch);
	}

	class Ranking {

		private MessageProcessing processing;
		private BitmapFont font;
		private BitmapFont font2;
		private String heading;
		private String players;
		private int x;
		private int y;
		private int wrap;

		public Ranking() {
			processing = MessageProcessing.getInstance();
			font = Assets.text45Font;
			font2 = Assets.text30Font;
			heading = "Active Players: ";
			players = "0/" + Settings.maxPlayers;
			x = Assets.TIMELINE_WIDTH + Assets.TIMELINE_WIDTH_OFFSET + 10;
			y = Assets.RANKING_HEIGHT;
			wrap = Assets.STATUS_BAR_WIDTH;

		}

		public void draw(SpriteBatch spriteBatch) {
			y = Assets.RANKING_HEIGHT;
			updateText();
			font.setColor(Color.WHITE);
			font.drawWrapped(spriteBatch, players, x, y, wrap);
			font2.setColor(Color.WHITE);
			y -= 40;
			font2.drawWrapped(spriteBatch, heading, x, y, wrap);
			drawPlayerNames(spriteBatch);
		}

		private void drawPlayerNames(SpriteBatch spriteBatch) {
			String text = "";
			for (Player player : processing.getPlayerList()) {
				y -= 40;
				text = player.getName() + " " + player.getScore();
				font2.setColor(player.getColor());
				font2.draw(spriteBatch, text, x, y);
			}

		}

		public void updateText() {
			players = processing.getPlayerList().size() + "/6";
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
		private float cooldownWidth;
		private float cooldownHeight;
		private double lightningCooldown;
		private double rainCooldown;
		private double pressureCooldown;

		public SMS() {
			font = Assets.text45Font;
			font2 = Assets.text30Font;
			heading = "SMS-Effects: ";
			cooldownWidth = (float) (Assets.STATUS_BAR_WIDTH * 0.75);
			cooldownHeight = Assets.SMSBAR_HEIGHT / 20;
			lightningEffect = Settings.lightningKeyWord;
			rainEffect = Settings.rainKeyWord;
			pressureEffect = Settings.pressureKeyWord;
			telephoneNumber = Settings.TELEPHONE_NUMBER;
			x = Assets.TIMELINE_WIDTH + Assets.TIMELINE_WIDTH_OFFSET + 10;
			y = Assets.STATUS_BAR_HEIGHT;
		}

		public void draw(SpriteBatch spriteBatch) {
			updateCooldown();
			y = Assets.STATUS_BAR_HEIGHT;
			font.setColor(Color.WHITE);
			font.draw(spriteBatch, heading, x, y);
			y -= 80;
			
			font2.setColor(Color.WHITE);
			
			Color oldColor = spriteBatch.getColor();
			spriteBatch.setColor(Color.BLACK);
			spriteBatch.draw(Assets.timeLineTexture, x-2, y-2, cooldownWidth+4, cooldownHeight+4);
			spriteBatch.setColor(oldColor);
			
			spriteBatch.draw(Assets.timeLineTexture, x, y, cooldownWidth,
					cooldownHeight);
			oldColor = spriteBatch.getColor();
			spriteBatch.setColor(Color.GREEN);
			spriteBatch.draw(Assets.timeLineTexture, x, y, (float) (cooldownWidth*this.lightningCooldown), cooldownHeight);
			spriteBatch.setColor(oldColor);
			y -= 10;
			font2.draw(spriteBatch, lightningEffect, x, y);

			y -= Assets.SMSBAR_HEIGHT/5;
			
			oldColor = spriteBatch.getColor();
			spriteBatch.setColor(Color.BLACK);
			spriteBatch.draw(Assets.timeLineTexture, x-2, y-2, cooldownWidth+4, cooldownHeight+4);
			spriteBatch.setColor(oldColor);
			
			spriteBatch.draw(Assets.timeLineTexture, x, y, cooldownWidth,
					cooldownHeight);
			oldColor = spriteBatch.getColor();
			spriteBatch.setColor(Color.GREEN);
			spriteBatch.draw(Assets.timeLineTexture, x, y, (float) (cooldownWidth*this.rainCooldown), cooldownHeight);
			spriteBatch.setColor(oldColor);
			y -= 10;
			font2.draw(spriteBatch, rainEffect, x, y);

			y -= Assets.SMSBAR_HEIGHT/5;
			
			oldColor = spriteBatch.getColor();
			spriteBatch.setColor(Color.BLACK);
			spriteBatch.draw(Assets.timeLineTexture, x-2, y-2, cooldownWidth+4, cooldownHeight+4);
			spriteBatch.setColor(oldColor);
			
			spriteBatch.draw(Assets.timeLineTexture, x, y, cooldownWidth,
					cooldownHeight);
			oldColor = spriteBatch.getColor();
			spriteBatch.setColor(Color.GREEN);
			spriteBatch.draw(Assets.timeLineTexture, x, y, (float) (cooldownWidth*this.pressureCooldown), cooldownHeight);
			spriteBatch.setColor(oldColor);
			y -= 10;
			font2.draw(spriteBatch, pressureEffect, x, y);

			y -= Assets.SMSBAR_HEIGHT/5;
			font2.draw(spriteBatch, telephoneNumber, x, y);
		}

		public void updateCooldown(){
			this.lightningCooldown = ((System.currentTimeMillis()- Lightning.getLastUsed())/Settings.lightningCooldown);
			this.rainCooldown = ((System.currentTimeMillis()- Rain.getLastUsed())/Settings.rainCooldown);
			this.pressureCooldown = ((System.currentTimeMillis()- WaterPressure.getLastUsed())/Settings.pressureCooldown);
			
			if(this.lightningCooldown > 1){
				this.lightningCooldown = 1;
			}
			if(this.rainCooldown > 1){
				this.rainCooldown = 1;
			}
			if(this.pressureCooldown > 1){
				this.pressureCooldown = 1;
			}
		}
	}
}
