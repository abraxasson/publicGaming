package hof.net.userMessages;


public class LogoutInfoMessage extends AbstractMessage {

	private static final long serialVersionUID = 37295872L;

	public LogoutInfoMessage() {
		super(AbstractMessage.LogoutInfo);
	}

	@Override
	public String toString() {
		return super.toString() + " -Logout pressed!";
	}
}
