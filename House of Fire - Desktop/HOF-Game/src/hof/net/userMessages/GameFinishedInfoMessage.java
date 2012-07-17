package hof.net.userMessages;

public class GameFinishedInfoMessage extends AbstractMessage{
	private static final long serialVersionUID = 37295872L;
	private String result;		//gewonnen oder verloren
	
	public GameFinishedInfoMessage(String result) {
		super(Type.GameFinished);
		this.result = result;
	}

	public String getResult() {
		return result;
	}
	
}
