package hof.net.userMessages;

public class AchievementInfoMessage extends AbstractMessage{
	private static final long serialVersionUID = 37295872L;
	private Medal medal;
	
	public AchievementInfoMessage(Medal medal) {
		super(Type.Achievement);
		this.medal = medal;
	}

	public Medal getMedal() {
		return medal;
	}	
	
	public static enum Medal {
		Gold,
		Silver,
		Bronze;
	}
	
}
