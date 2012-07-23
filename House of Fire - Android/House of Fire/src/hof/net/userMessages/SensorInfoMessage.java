package hof.net.userMessages;

public class SensorInfoMessage extends AbstractMessage {
	private int x;
	private int y;
	private int z;
	private static final long serialVersionUID = 37295872L;
	
	public SensorInfoMessage(int x, int y, int z){
		super(Type.SensorInfo);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
}
