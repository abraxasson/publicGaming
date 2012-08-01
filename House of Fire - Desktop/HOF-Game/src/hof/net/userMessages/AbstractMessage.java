package hof.net.userMessages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public abstract class AbstractMessage implements Serializable {

	private static final long serialVersionUID = 37295872L;
	public static final int PlayerInfo = 0;
	public static final int ButtonInfo = 1;
	public static final int LogoutInfo = 2;
	public static final int ValidationInfo = 4;
	public static final int GameOver = 6;
	public static final int WaterPressure = 8;
	public static final int SensorInfo = 10;
	public static final int LevelInfo = 12;
	public static final int SMSInfo = 14;
	

	private int type;
	private InetAddress ia;

	public AbstractMessage(int type) { 
		this.type = type;
	}

	public int getType() {
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
	
	public void serialize(DataOutputStream stream) throws IOException {
		stream.writeInt(getType());
		
	}
	
	public static AbstractMessage deserialize(DataInputStream stream) throws IOException {
		int type = stream.readInt();
		switch (type) {
		case ButtonInfo:
			return new ButtonInfoMessage(stream.readInt());
		case GameOver:
			return new GameOverInfoMessage();
		case LevelInfo:
			return new LevelInfoMessage(stream.readInt(), stream.readInt(), stream.readBoolean(), stream.readInt());
		case LogoutInfo:
			return new LogoutInfoMessage();
		case PlayerInfo:
			return new PlayerInfoMessage(stream.readUTF());
		case SensorInfo:
			return new SensorInfoMessage(stream.readFloat(), stream.readFloat(), stream.readFloat());
		case SMSInfo:
			return new SMSInfoMessage(stream.readInt());
		case ValidationInfo:
			return new ValidationInfoMessage(stream.readFloat(), stream.readFloat(), stream.readFloat());
		case WaterPressure:
			return new WaterPressureInfoMessage(stream.readFloat());
				
		}
		return null;
	}
}