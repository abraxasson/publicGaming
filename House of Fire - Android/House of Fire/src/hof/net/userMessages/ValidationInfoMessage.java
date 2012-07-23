package hof.net.userMessages;

public class ValidationInfoMessage extends AbstractMessage {

	private static final long serialVersionUID = 37295872L;

	private float r;
	private float g;
	private float b;

	public ValidationInfoMessage() {
		super(Type.ValidationInfo);
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}
	
	public ValidationInfoMessage(float r, float g, float b) {
		super(Type.ValidationInfo);
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
