package hof.net.userMessages;

public class LevelInfoMessage extends AbstractMessage {
	public static final int STARTED = 1;
	public static final int FINISHED = 2;
	
	public static final int NO_MEDAL = 0;
	public static final int GOLD_MEDAL = 1;
	public static final int SILVER_MEDAL = 2;
	public static final int BRONZE_MEDAL = 4;
	
	
	private static final long serialVersionUID = 37295872L;
	private int level;
	private int eventType;
	private int medalType;
	
	
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


	public int getMedalType() {
		return medalType;
	}


	public void setMedalType(int medalType) {
		this.medalType = medalType;
	}


	
}
