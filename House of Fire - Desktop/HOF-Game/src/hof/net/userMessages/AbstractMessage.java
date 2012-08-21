package hof.net.userMessages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * This class is the base for the network communication. <br>
 * Sub-classes of this class get serialized, send over the network and deserialized. 
 * The Serialization and the Deserialization is executed in this class <br>
 * 
 *
 */
public abstract class AbstractMessage {

	/**
	 * Informs about a new player.
	 */
	public static final int PlayerInfo = 0;
	/**
	 * Informs about hte button inout.
	 */
	public static final int ButtonInfo = 1;
	/**
	 * Informs about a Logout.
	 */
	public static final int LogoutInfo = 2;
	/**
	 *	Validates a received message. 
	 */
	public static final int ValidationInfo = 4;
	/**
	 * Informs about a Game Over.
	 */
	public static final int GameOver = 6;
	/**
	 * Informs about the current waterPressure.
	 */
	public static final int WaterPressure = 8;
	/**
	 * Informs about the sensor input.
	 */
	public static final int SensorInfo = 10;
	/**
	 * Informs about the current level.
	 */
	public static final int LevelInfo = 12;
	/**
	 * Informs about received SMS-Effects.
	 */
	public static final int SMSInfo = 14;
	
	/**
	 * The type of the message.
	 */
	private int type;
	/**
	 * The InetAddress from where the message is send.
	 */
	private InetAddress ia;

	/**
	 * Constructs a new Message from the given type.
	 * <br> This constructor is called from the sub-classes with the right type.
	 * @param type - the type of the message
	 */
	public AbstractMessage(int type) { 
		this.type = type;
	}

	/**
	 * Returns the type of this message.
	 * @return the type of this message
	 */
	public int getType() {
		return type;
	}

	/**
	 * Returns the InetAddress from where this message came from.
	 * @return the InetAddress
	 */
	public InetAddress getIa() {
		return ia;
	}
	
	/**
	 * Sets the InetAddress from where this message came from.
	 * <br> This is normally called after the message is received.
	 * @param ia - the InetAddress
	 */
	public void setIa(InetAddress ia) {
		this.ia = ia;
	}

	/**
	 * Returns a String containing the type of this message.
	 * @return a String with the type
	 */
	@Override
	public String toString() {
		return "Type: " + type;
	}
	
	/**
	 * Serializes the messages and prepares it for the network communication. <br>
	 * Every sub-class has to overwrite this method and call the super-method. <br>
	 * Then they need to add their own attributes.
	 * @param stream - the stream where the messages gets written into.
	 * @throws IOException  if the stream is closed
	 */
	public void serialize(DataOutputStream stream) throws IOException {
		stream.writeInt(getType());
	}
	
	/**
	 * Deserializes the messages received from the network communication. <br>
	 * This method has to be expanded for every new sub-class.
	 * @param stream - the stream where the message was written into
	 * @return a new Message with the right type
	 * @throws IOException if the stream is closed
	 */
	public static AbstractMessage deserialize(DataInputStream stream) throws IOException {
		int type = stream.readInt();
		switch (type) {
		case ButtonInfo:
			return new ButtonInfoMessage(stream.readInt());
		case GameOver:
			return new GameOverInfoMessage();
		case LevelInfo:
			int eventType = stream.readInt();
			int level = stream.readInt();
			boolean lastLevel = stream.readBoolean();
			int medal = stream.readInt();
			if (eventType == LevelInfoMessage.STARTED) {
				return new LevelInfoMessage(level);
			} else {
				return new LevelInfoMessage(level, lastLevel, medal);
			}
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