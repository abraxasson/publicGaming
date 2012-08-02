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
	private int lastState;
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
		this.lastState = ButtonInfoMessage.NORMAL;
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
		stayInBounds();
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
			if(this.lastState != this.state){
				this.setX(this.getX()+this.width);
			}
			this.lastState = ButtonInfoMessage.LEFT;
			this.setWidth(this.negativeWidth);
			waterJet.setPosition(getX()+20+this.negativeWidth, getY()+125);
			break;
		case ButtonInfoMessage.RIGHT:
			if(this.lastState != this.state){
				this.setX(this.getX()-this.width);
			}
			this.lastState = ButtonInfoMessage.RIGHT;
			this.setWidth(this.width);
			waterJet.setPosition(getX()+105, getY()+125);
			break;
		case ButtonInfoMessage.NORMAL:
			break;
		default:
			waterJet.setPosition(getX()+105, getY()+125);
			break;
		}
	}

	public void stayInBounds() {
		if (getX() - this.width < 0 && this.state==ButtonInfoMessage.LEFT) {
			setX(this.width);
			this.setState(ButtonInfoMessage.NORMAL);
		}
		if (getX() > Assets.CANVAS_WIDTH - getWidth() && this.state == ButtonInfoMessage.RIGHT){
			setX(Assets.CANVAS_WIDTH  - getWidth());
			this.setState(ButtonInfoMessage.NORMAL);
		}
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
