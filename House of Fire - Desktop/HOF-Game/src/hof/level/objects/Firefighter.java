package hof.level.objects;

import hof.player.Player;
import com.badlogic.gdx.graphics.Texture;

public class Firefighter extends AbstractPerson {
	private Player player;
	
	public Firefighter(Texture body, int x, int y, int width, int height, Player player) {
		super(body, x, y, width, height);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
