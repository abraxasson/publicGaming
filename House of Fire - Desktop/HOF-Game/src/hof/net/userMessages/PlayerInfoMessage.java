package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerInfoMessage extends AbstractMessage {

	private static final long serialVersionUID = 37295872L;
	private String name;

	public PlayerInfoMessage(String name) {
		super(AbstractMessage.PlayerInfo);
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
	
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeUTF(name);
	}
}
