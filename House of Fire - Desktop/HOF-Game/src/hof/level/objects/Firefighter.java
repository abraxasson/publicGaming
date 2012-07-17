package hof.level.objects;

import hof.player.Player;
import com.badlogic.gdx.graphics.Texture;

public class Firefighter extends Person {
	private Player player;
	
	public Firefighter(Texture body, int healthpoints, int x, int y, Player player) {
		super(body, x, y);
	}

	public Player getPlayer() {
		return player;
	}

}
