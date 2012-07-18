package hof.net.userMessages;

public class LevelFinishedInfoMessage extends AbstractMessage{
	private static final long serialVersionUID = 37295872L;
	private int level;
	
	public LevelFinishedInfoMessage(int level) {
		super(Type.LevelFinished);
		this.level = level;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}

}
