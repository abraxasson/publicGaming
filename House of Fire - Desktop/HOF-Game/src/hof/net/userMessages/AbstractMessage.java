package hof.net.userMessages;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {

	private static final long serialVersionUID = 37295872L;

	private Type type;

	public AbstractMessage(Type type) { 
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Type: " + type;
	}

	public static enum Type {
		PlayerInfo, ButtonInfo, LogoutInfo, ValidationInfo, LevelFinished, Achievement, GameFinished, Retry, WaterPressure, SensorInfo;
	}
}
