package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

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
		super(AbstractMessage.LevelInfo);
		this.level = level;
		this.eventType = eventType;
		this.lastLevel = false;
		this.medal = NO_MEDAL;
	}
	
	public LevelInfoMessage(int eventType, int level, boolean lastLevel, int medal) {
		super(AbstractMessage.LevelInfo);
		this.level = level;
		this.eventType = eventType;
		this.lastLevel = lastLevel;
		this.medal = medal;
	}

	public int getMedalType() {
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
	
	@Override
	public String toString() {
		return super.toString() + " " + level + " " + lastLevel + " " + medal;
	}
	
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeInt(eventType);
		stream.writeInt(level);
		stream.writeBoolean(lastLevel);
		stream.writeInt(medal);
	}
}
