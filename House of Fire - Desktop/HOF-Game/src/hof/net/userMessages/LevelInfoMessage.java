package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This message shows that a level has finished or has started. <br>
 * It also contains information about any received Medals or if this was last level.
 *
 */
public class LevelInfoMessage extends AbstractMessage {
	/**
	 * Shows that the level has started.
	 */
	public static final int STARTED = 1;
	/**
	 * Shows that the level has finished.
	 */
	public static final int FINISHED = 2;
	
	/**
	 * Shows that no medal was received.
	 */
	public static final int NO_MEDAL = 0;
	/**
	 * Shows that the bronze medal was received.
	 */
	public static final int BRONZE_MEDAL = 1;
	/**
	 * Shows that the silver medal was received.
	 */
	public static final int SILVER_MEDAL = 2;
	/**
	 * Shows that the gold medal was received.
	 */
	public static final int GOLD_MEDAL = 4;
	/**
	 * The level of finished or started level.
	 */
	private int level;
	/**
	 * Shows if the level is started or finished.
	 */
	private int eventType;
	/**
	 * Is the last level - true or false.
	 */
	private boolean lastLevel;
	/**
	 * Holds the received medal.
	 */
	private int medal;
	
	/**
	 * Creates a new LevelInfoMessage with the eventType STARTED and the given level.
	 * <br> This constructor is in purpose for creating messages when a new level starts.
	 * @param level the level which starts
	 */
	public LevelInfoMessage(int level) {
		super(AbstractMessage.LevelInfo);
		this.level = level;
		this.eventType = STARTED;
		this.lastLevel = false;
		this.medal = NO_MEDAL;
	}
	
	/**
	 * Creates a new LevelInfoMessage with the eventType FINISHED.
	 * <br> This constructor is in purpose for creating messages when a level ends.
	 * @param level the level which was finished
	 * @param lastLevel true if this was the last level
	 * @param medal the received medal
	 */
	public LevelInfoMessage(int level, boolean lastLevel, int medal) {
		super(AbstractMessage.LevelInfo);
		this.level = level;
		this.eventType = FINISHED;
		this.lastLevel = lastLevel;
		this.medal = medal;
	}

	/**
	 * Returns which medal was received. <br>
	 * Possible Values: <b>NO_MEDAL, BRONZE_MEDAL, SILVER_MEDAL or GOLD_MEDAL</br>
	 * @return the received medal
	 */
	public int getMedalType() {
		return medal;
	}

	/**
	 * Returns if this was the last level.
	 * @return true if the last level was finished
	 */
	public boolean isLastLevel() {
		return lastLevel;
	}

	/**
	 * Shows if the level starts or has just finished.
	 * @return  the eventType of this message
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * Returns the level which has finished or starts.
	 * @return the current level
	 */
	public int getLevel() {
		return level;
	}	
	
	/**
	 * Returns all attributes to a String.
	 * @return a String with all attributes.
	 */
	@Override
	public String toString() {
		return super.toString() + " " + level + " " + lastLevel + " " + medal;
	}
	
	/**
	 * Writes all attributes in the given stream.
	 * @param stream the strem to write into
	 */
	@Override
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeInt(eventType);
		stream.writeInt(level);
		stream.writeBoolean(lastLevel);
		stream.writeInt(medal);
	}
}
