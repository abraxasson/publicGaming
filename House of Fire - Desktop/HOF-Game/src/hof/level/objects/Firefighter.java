package hof.level.objects;

import hof.core.utils.Assets;
import hof.core.utils.ColorList.PlayerColor;
import hof.net.userMessages.ButtonInfoMessage;
import hof.player.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Firefighter extends AbstractPerson {
	private Player player;
	private WaterJet waterJet;
	private int state;
	private int lastState;
	private int width;
	private int negativeWidth;
	
	public Firefighter(Player player) {
		this(Assets.firefighter_blue, (int)(Math.random() * Assets.CANVAS_WIDTH),-80, 150, 200, player);
	}
	
	private Firefighter(Texture body, int x, int y, int width, int height, Player player) {
		super(body, x, y, width, height);
		this.width = width;
		this.negativeWidth = -width;
		this.player = player;
		waterJet = new WaterJet();
		this.lastState = ButtonInfoMessage.RIGHT;
		chooseBody(player);
		stayInBounds();
	}

	private void chooseBody(Player player) {
		PlayerColor playerColor = player.getPlayerColor();
		final Texture body;
		switch(playerColor) {
		case BLUE: 
			body = Assets.firefighter_red; break;
		case GREEN:
			body = Assets.firefighter_red; break;
		case MAGENTA:
			body = Assets.firefighter_red; break;
		case ORANGE:
			body = Assets.firefighter_red; break;
		case PINK:
			body = Assets.firefighter_red; break;
		case RED:
			body = Assets.firefighter_red; break;
		case YELLOW:
			body = Assets.firefighter_red; break;
		default:
			body = Assets.firefighter_blue;	break;
		}
		this.setBody(body);
	}

	public Player getPlayer() {
		return player;
	}
	
	public void draw(SpriteBatch spriteBatch) {
		stayInBounds();
		updateFighter();
		waterJet.draw(spriteBatch);
		spriteBatch.draw(getBody(), getX(), getY(), getWidth(), getHeight());
	}
	
	private void updateFighter() {
		switch(state) {
		case ButtonInfoMessage.LEFT:
			if(this.lastState != this.state){
				this.moveHorizontal(this.width);
			}
			this.lastState = ButtonInfoMessage.LEFT;
			this.setWidthNegative();
			waterJet.setPosition(getX()+20+this.negativeWidth, getY()+125);
			break;
		case ButtonInfoMessage.RIGHT:
			if(this.lastState != this.state){
				this.moveHorizontal(-this.width);
			}
			this.lastState = ButtonInfoMessage.RIGHT;
			
			this.setWidthPositive();
			waterJet.setPosition(getX()+105, getY()+125);
			break;
		case ButtonInfoMessage.NORMAL:
			break;
		default:
			waterJet.setPosition(getX()+105, getY()+125);
			break;
		}
	}

	private void stayInBounds() {
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
