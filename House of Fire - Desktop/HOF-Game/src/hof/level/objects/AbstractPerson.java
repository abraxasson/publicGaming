package hof.level.objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class AbstractPerson {
	private Texture body;
	private int x;
	private int y;
	private final int width;
	private final int height;
	private int currentWith;

	public AbstractPerson(final Texture body, final int x, final int y, final int width, final int height) {
		super();
		this.body = body;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.currentWith = width;
	}

	public Texture getBody() {
		return body;
	}

	public void setBody(Texture body) {
		this.body = body;
	}

	public void move(final int x, final int y) {
		this.x += x;
		this.y += y;
	}
	
	public void moveHorizontal(final int x) {
		move(x, 0);
	}
	
	public void moveVertical(final int y) {
		move(0, y);
	}
	
	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return currentWith;
	}

	protected void setWidthNegative() {
		this.currentWith =  -width;
	}

	protected void setWidthPositive() {
		this.currentWith = width;
	}
	
	public int getHeight() {
		return height;
	}
}
