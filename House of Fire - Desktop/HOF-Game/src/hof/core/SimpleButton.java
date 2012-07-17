package hof.core;

import hof.core.utils.Assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleButton {

	private String text;
	private boolean wasPressed;
	private boolean activated;
	private boolean down;
	private BitmapFont font;
	private float textHeight;
	private float width;
	private float height;
	private float x;
	private float y;
	private HAlignment alignment;

	public SimpleButton(String text, BitmapFont font) {
		this.text = text;
		wasPressed = false;
		this.font = font;
		TextBounds bounds = Assets.defaultFont.getBounds(text);
		textHeight = bounds.height;
		width = bounds.width;
		height = bounds.height;
		alignment = HAlignment.CENTER;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setAlignment(HAlignment alignment) {
		this.alignment = alignment;
	}

	private boolean inBounds(float x, float y) {
		return x >= this.x && x < this.x + this.width && y >= this.y
				&& y < this.y + this.height;
	}

	public void update(float delta, boolean justTouched, boolean isTouched,
			boolean justReleased, float x, float y) {
		wasPressed = false;
		if (justTouched && inBounds(x, y)) {
			activated = true;
			down = true;
		} else if (isTouched) {
			down = activated && inBounds(x, y);
		} else if (justReleased) {
			wasPressed = activated && inBounds(x, y);
			activated = false;
			down = false;
		} else {
			activated = false;
		}
	}

	public boolean wasPressed() {
		return this.wasPressed;
	}

	public void draw(SpriteBatch spriteBatch) {
		Color oldColor = font.getColor();
		if (down) {
			spriteBatch.setColor(Color.RED);
		} else {
			spriteBatch.setColor(Color.BLUE);
		}
		spriteBatch.draw(Assets.pureWhiteTextureRegion, x, y, width, height);
		spriteBatch.setColor(Color.WHITE);
		if (down) {
			font.setColor(oldColor.r / 2, oldColor.g / 2, oldColor.b / 2,
					oldColor.a);
		}
		float textX = x;
		float textY = y + height;
		textY -= (height - textHeight) / 2;
		font.drawWrapped(spriteBatch, text, textX, textY, width, alignment);
		font.setColor(oldColor);

	}

	public void rightOn(float right) {
		x = right - width;
	}

	public void leftOn(float left) {
		x = left;
	}

	public void centerHorizontallyOn(float centerX) {
		x = centerX - width / 2;
	}

	public void bottomOn(float bottom) {
		y = bottom;
	}

	public void topOn(float top) {
		y = top - height;
	}

	public void centerVerticallyOn(float centerY) {
		y = centerY - height / 2;
	}

}
