package hof.net.userMessages;

public class LevelInfoMessage extends AbstractMessage {
	public static final int STARTED = 1;
	public static final int FINISHED = 2;
	
	public static final int NO_MEDAL = 0;
	public static final int BRONZE_MEDAL = 1;
	public static final int SILVER_MEDAL = 2;
	public static final int GOLD_MEDAL = 4;
	private static final long serialVersionUID = 37295872L;
	private int level;
	private int eventType;
	private boolean lastLevel;
	private int medal;
	
	
	public LevelInfoMessage(int eventType, int level) {
		super(Type.LevelInfo);
		this.level = level;
		this.eventType = eventType;
		this.medal = NO_MEDAL;
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
