package hof.net.userMessages;

public class LevelInfoMessage extends AbstractMessage {
	public static final int STARTED = 1;
	public static final int FINISHED = 2;
	private static final long serialVersionUID = 37295872L;
	private int level;
	private int eventType;
	
	
	public LevelInfoMessage(int eventType, int level) {
		super(Type.LevelInfo);
		this.level = level;
		this.eventType = eventType;
	}


	public int getEventType() {
		return eventType;
	}


	public int getLevel() {
		return level;
	}	
	
}
