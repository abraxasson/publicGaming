package hof.net.userMessages;

public class PlayerInfoMessage extends AbstractMessage {

	private static final long serialVersionUID = 37295872L;
	private String name;

	public PlayerInfoMessage(String name) {
		super(Type.PlayerInfo);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return super.toString() + " " + name;
	}
}
