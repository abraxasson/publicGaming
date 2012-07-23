package hof.net.userMessages;

public class ButtonInfoMessage extends AbstractMessage {
	private static final long serialVersionUID = 37295872L;
	public static final int NORMAL = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 4;
	private int state;
	
	public ButtonInfoMessage(int state) {
		super(Type.ButtonInfo);
		this.state = state;
	}
	
	public int getState(){
		return state;
	}
	
	public void setState(int state){
		this.state = state;
	}

	@Override
	public String toString() {
		return super.toString() + " -Button pressed!";
	}
}
