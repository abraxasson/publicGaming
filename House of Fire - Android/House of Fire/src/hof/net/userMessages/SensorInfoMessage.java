package hof.net.userMessages;

public class SensorInfoMessage extends AbstractMessage {
	private float x;
	private float y;
	private float z;
	private static final long serialVersionUID = 37295872L;
	
	public SensorInfoMessage(float x, float y, float z){
		super(Type.SensorInfo);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getZ(){
		return z;
	}
	
	@Override
	public String toString() {
		return super.toString() + " X: " + x + " Y: " + y + " Z: " + z;
	}
}
