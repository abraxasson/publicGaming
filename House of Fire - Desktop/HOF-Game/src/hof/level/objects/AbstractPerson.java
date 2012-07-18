package hof.level.objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class AbstractPerson {
	private Texture body;
	private int x;
	private int y;
	private int width;
	private int height;

	public AbstractPerson(Texture body, int x, int y, int width, int height) {
		super();
		this.body = body;
		this.x = x;
		this.y = y;
		this.setWidth(width);
		this.setHeight(height);
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
