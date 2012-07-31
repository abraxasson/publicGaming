package hof.net.userMessages;

public class ValidationInfoMessage extends AbstractMessage {

	private static final long serialVersionUID = 37295872L;

	private float r;
	private float g;
	private float b;
	
	public ValidationInfoMessage(float r, float g, float b) {
		super(AbstractMessage.ValidationInfo);
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}
}
