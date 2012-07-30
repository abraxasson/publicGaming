package hof.net.userMessages;

import java.io.Serializable;
import java.net.InetAddress;

public abstract class AbstractMessage implements Serializable {

	private static final long serialVersionUID = 37295872L;

	private Type type;
	private InetAddress ia;

	public AbstractMessage(Type type) { 
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public InetAddress getIa() {
		return ia;
	}
	
	public void setIa(InetAddress ia) {
		this.ia = ia;
	}

	@Override
	public String toString() {
		return "Type: " + type;
	}

	public static enum Type {
		PlayerInfo, ButtonInfo, LogoutInfo, ValidationInfo, GameOver, WaterPressure, SensorInfo, LevelInfo, SMSInfo;
	}
}
