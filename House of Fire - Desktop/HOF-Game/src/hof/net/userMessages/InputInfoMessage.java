package hof.net.userMessages;

public class InputInfoMessage extends AbstractMessage {
	private static final long serialVersionUID = 37295872L;

	public InputInfoMessage() {
		super(Type.InputInfo);
	}

	@Override
	public String toString() {
		return super.toString() + " -Button pressed!";
	}
}
