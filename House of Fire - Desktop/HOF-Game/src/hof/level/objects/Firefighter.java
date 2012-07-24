package hof.level.objects;

import hof.core.utils.Assets;
import hof.net.userMessages.ButtonInfoMessage;
import hof.player.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Firefighter extends AbstractPerson {
	private Player player;
	private WaterJet waterJet;
	private int state;
	
	public Firefighter(Player player, int state) {
		this(Assets.firefighter_left, (int)(Math.random() * Assets.CANVAS_WIDTH),-80, 150, 200, player);
	}
	
	private Firefighter(Texture body, int x, int y, int width, int height, Player player) {
		super(body, x, y, width, height);
		this.player = player;
		waterJet = new WaterJet();
	}

	public Player getPlayer() {
		return player;
	}
	
	public void draw(SpriteBatch spriteBatch) {
		updateFighter();
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
	
	private void updateFighter() {
		switch(state) {
		case ButtonInfoMessage.LEFT:
			setBody(Assets.firefighter_left);
			waterJet.setPosition(getX()+20, getY()+125);
			break;
		case ButtonInfoMessage.RIGHT:
			setBody(Assets.firefighter_right);
			waterJet.setPosition(getX()+105, getY()+125);
			break;
		case ButtonInfoMessage.NORMAL:
			break;
		default:
			waterJet.setPosition(getX()+20, getY()+125);
			break;
		}
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
