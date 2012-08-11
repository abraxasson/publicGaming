package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

public class ButtonInfoMessage extends AbstractMessage {
	private static final long serialVersionUID = 37295872L;
	public static final int NORMAL = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 4;
	private int state;
	
	public ButtonInfoMessage(int state) {
		super(AbstractMessage.ButtonInfo);
		this.state = state;
	}
	
	public int getState(){
		return state;
	}
	
	@Override
	public String toString() {
		return super.toString() + " -Button pressed! " + state;
	}

	@Override
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeInt(getState());
	}
	
}
