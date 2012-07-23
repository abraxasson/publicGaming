package hof.net.userMessages;

public class InputInfoMessage extends AbstractMessage {
	private static final long serialVersionUID = 37295872L;
	private boolean left;
	
	public InputInfoMessage(boolean left) {
		super(Type.InputInfo);
		this.left = left;
	}
	
	public boolean getLeft(){
		return left;
	}
	
	public void setLeft(boolean left){
		this.left = left;
	}

	@Override
	public String toString() {
		return super.toString() + " -Button pressed!";
	}
}
