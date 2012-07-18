package hof.net.userMessages;

public class RetryInfoMessage extends AbstractMessage{
	private static final long serialVersionUID = 37295872L;
	private boolean again;
	
	public RetryInfoMessage(boolean again) {
		super(Type.Retry);
		this.again = again;
	}
	
	public boolean getAgain(){
		return again;
	}
}
