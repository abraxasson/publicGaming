package hof.net.userMessages;

public class LevelInfoMessage extends AbstractMessage {
	public static final int STARTED = 1;
	public static final int FINISHED = 2;
	
	public static final int NOTHING = 4;
	public static final int BRONZE = 6;
	public static final int SILVER = 8;
	public static final int GOLD = 10;
	private static final long serialVersionUID = 37295872L;
	private int level;
	private int eventType;
	private boolean lastLevel;
	private int medal;
	
	
	public LevelInfoMessage(int eventType, int level, boolean lastLevel) {
		super(Type.LevelInfo);
		this.level = level;
		this.eventType = eventType;
		this.lastLevel = lastLevel;
		this.medal = NOTHING;
	}
	
	public LevelInfoMessage(int eventType, int level, boolean lastLevel, int medal) {
		super(Type.LevelInfo);
		this.level = level;
		this.eventType = eventType;
		this.lastLevel = lastLevel;
		this.medal = medal;
	}

	public int getMedal() {
		return medal;
	}

	public boolean isLastLevel() {
		return lastLevel;
	}

	public int getEventType() {
		return eventType;
	}


	public int getLevel() {
		return level;
	}	
	
}
