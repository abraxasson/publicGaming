package userMessages;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {

	private static final long serialVersionUID = 37295872L;

	private Type type;
	protected final static int PlayerInfo = 0;
	protected final static int InputInfo = 1;
	protected final static int LogoutInfo = 2;

	public AbstractMessage(int z) { // 0 fuer PlayerInfo 1 fuer InputInfo 2 fuer
									// LogoutInfo
		if (z == InputInfo) {
			this.type = Type.InputInfo;
		} else if (z == PlayerInfo) {
			this.type = Type.PlayerInfo;
		} else if (z == LogoutInfo) {
			this.type = Type.LogoutInfoMessage;
		} else {
			System.out.println("Error: Unknown Player");
		}

	}

	public String getType() {
		return type.name();
	}

	@Override
	public String toString() {
		return "Type: " + type;
	}

	public enum Type {
		PlayerInfo, InputInfo, LogoutInfoMessage;
		int type;

		private Type() {
		}

		private Type(int type) {
			this.type = type;
		}
	}
}
