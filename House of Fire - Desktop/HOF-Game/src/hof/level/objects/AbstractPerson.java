package hof.level.objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class AbstractPerson {
	private Texture body;
	private int x;
	private int y;

	public AbstractPerson(Texture body, int x, int y) {
		super();
		this.body = body;
		this.x = x;
		this.y = y;
	}

	public Texture getBody() {
		return body;
	}

	public void setBody(Texture body) {
		this.body = body;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
