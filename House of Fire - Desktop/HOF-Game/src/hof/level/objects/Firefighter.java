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
	private int width;
	private int negativeWidth;
	
	public Firefighter(Player player, int state) {
		this(Assets.firefighter_left, (int)(Math.random() * Assets.CANVAS_WIDTH),-80, 150, 200, player);
	}
	
	private Firefighter(Texture body, int x, int y, int width, int height, Player player) {
		super(body, x, y, width, height);
		this.width = width;
		this.negativeWidth = -width;
		this.player = player;
		waterJet = new WaterJet();
		Color playerColor = player.getColor();
		if(playerColor.equals(Color.RED)){
			this.setBody(Assets.firefighter_red);
		}
		else if(playerColor.equals(Color.GREEN)){
			this.setBody(Assets.firefighter_green);
		}
		else if(playerColor.equals(Color.ORANGE)){
			this.setBody(Assets.firefighter_orange);
		}
		else if(playerColor.equals(Color.MAGENTA)){
			this.setBody(Assets.firefighter_magenta);
		}
		else if(playerColor.equals(Color.BLUE)){
			this.setBody(Assets.firefighter_blue);
		}
		else if(playerColor.equals(Color.CYAN)){
			this.setBody(Assets.firefighter_cyan);
		}
	}

	public Player getPlayer() {
		return player;
	}
	
	public void draw(SpriteBatch spriteBatch) {
		updateFighter();
		waterJet.draw(spriteBatch);
		spriteBatch.draw(getBody(), getX(), getY(), getWidth(), getHeight());
	}
	
	private void updateFighter() {
		switch(state) {
		case ButtonInfoMessage.LEFT:
			this.setWidth(this.negativeWidth);
			waterJet.setPosition(getX()+20+this.negativeWidth, getY()+125);
			break;
		case ButtonInfoMessage.RIGHT:
			this.setWidth(this.width);
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
