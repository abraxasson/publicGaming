package hof.level.objects;

import hof.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Firefighter extends AbstractPerson {
	private Player player;
	private WaterJet waterJet;
	
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
		spriteBatch.setColor(getPlayer().getColor());
		spriteBatch.draw(getBody(), getX(), getY(), getWidth(), getHeight(), 0, 0, 8, 8, false, false);
		spriteBatch.setColor(oldColor);
	}
	
	private void updateJet() {
		waterJet.setPosition(getX(), getY());
	}

	public void stayInBounds() {
		if (getX() < 0) 
			setX(0);
		if (getX() > Gdx.graphics.getWidth() - getWidth())
			setX(Gdx.graphics.getWidth() - getWidth());
	}

	public WaterJet getWaterJet() {
		return waterJet;		
	}
}
