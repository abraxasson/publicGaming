package hof.net.userMessages;


public class SMSInfoMessage extends AbstractMessage {

	public static final int LIGHTNING = 0;
	public static final int RAIN = 1;
	public static final int PRESSURE = 2;
	private static final long serialVersionUID = 37295872L;
	
	private int type;
	
	public SMSInfoMessage(int effect) {
		super(AbstractMessage.SMSInfo);
		type = effect;
	}

	public int getEffectType() {
		return type;
	}
	
	@Override
	public String toString() {
		String typ = "";
		switch (type) {
		case LIGHTNING:
			typ = "LIGHTNING";
			break;
		case RAIN:
			typ = "RAIN";
			break;
		case PRESSURE:
			typ = "PRESSURE";
			break;
		default: 
			typ = "default";
			break;
		}
		return super.toString() + " " + typ;
	}
}
