package hof.net.userMessages;

import hof.core.utils.Settings;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This message is send when a phone receives a SMS with a keyword.
 * <br> It indicates that a Special Effect should take place.
 *
 */
public class SMSInfoMessage extends AbstractMessage {

	/**
	 * A Lightning was sent.
	 */
	public static final int LIGHTNING = 0;
	/**
	 * Rain was send.
	 */
	public static final int RAIN = 1;
	/**
	 * Increased water pressure was send.
	 */
	public static final int PRESSURE = 2;
	/**
	 * The type of the SMS Special Effect.
	 */
	private int type;
	
	/**
	 * Creates a new SMSInfoMEssage with the given effect.
	 * @param effect the effect to activate
	 */
	public SMSInfoMessage(int effect) {
		super(AbstractMessage.SMSInfo);
		type = effect;
	}

	/**
	 * Returns the type of the received effect.
	 * @return the type of the effect.
	 */
	public int getEffectType() {
		return type;
	}
	
	/**
	 * Returns super.toString() plus the keyword for the received effect.
	 */
	@Override
	public String toString() {
		String typ = "";
		switch (type) {
		case LIGHTNING:
			typ = Settings.lightningKeyWord;
			break;
		case RAIN:
			typ = Settings.rainKeyWord;
			break;
		case PRESSURE:
			typ = Settings.pressureKeyWord;
			break;
		default: 
			typ = "default";
			break;
		}
		return super.toString() + " " + typ;
	}
	
	/**
	 * Writes the effect into the given stream.
	 */
	@Override
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeInt(getEffectType());
	}

}
