package hof.level.objects;

import hof.core.utils.Assets;
import hof.player.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Firefighter extends AbstractPerson {
	private Player player;
	private WaterJet waterJet;
	
	public Firefighter(Player player) {
		this(Assets.firefighter, (int)(Math.random() * Assets.CANVAS_WIDTH),-80, 150, 200, player);
	}
	
	public Firefighter(Texture body, int x, int y, int width, int height, Player player) {
		super(body, x, y, width, height);
		this.player = player;
		waterJet = new WaterJet();
	}

	public Player getPlayer() {
		return player;
	}
	
	public void draw(SpriteBatch spriteBatch) {
		updateJet();
		waterJet.draw(spriteBatch);
		Color oldColor = spriteBatch.getColor();
		if (player != null) {
			spriteBatch.setColor(getPlayer().getColor());
		}
		else
		spriteBatch.setColor(Color.BLACK);
		
		spriteBatch.draw(getBody(), getX(), getY(), getWidth(), getHeight());
		spriteBatch.setColor(oldColor);
	}
	
	private void updateJet() {
		waterJet.setPosition(getX()+105, getY()+125);
	}

	public void stayInBounds() {
		if (getX() < 0) 
			setX(0);
		if (getX() > Assets.CANVAS_WIDTH - getWidth())
			setX(Assets.CANVAS_WIDTH  - getWidth());
	}

	public WaterJet getWaterJet() {
		return waterJet;		
	}
}
