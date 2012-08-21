package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This message contains data from the sensor and is send from the phone regularly.
 *
 */
public class SensorInfoMessage extends AbstractMessage {
	/**
	 * The x-coordinate from the sensor.
	 */
	private float x;
	/**
	 * The y-coordinate from the sensor.
	 */
	private float y;
	/**
	 * The z-coordinate from the sensor.
	 */
	private float z;
	
	/**
	 * Creates a new SensorInfoMessage with the given coordinates.
	 * @param x the x Coordinate
	 * @param y the y Coordinate
	 * @param z the z Coordinate
	 */
	public SensorInfoMessage(float x, float y, float z){
		super(AbstractMessage.SensorInfo);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the x Coordinate.
	 * @return the x coordinate
	 */
	public float getX(){
		return x;
	}
	
	/**
	 * Returns the y Coordinate.
	 * @return the y coordinate
	 */
	public float getY(){
		return y;
	}
	
	
	/**
	 * Returns the z Coordinate.
	 * @return the z coordinate
	 */
	public float getZ(){
		return z;
	}
	
	/**
	 * Returns the super.toString() plus the Coordinates.
	 * @return super.toString() plus the Coordinates
	 */
	@Override
	public String toString() {
		return super.toString() + " X: " + x + " Y: " + y + " Z: " + z;
	}
	
	/**
	 * Writes the three coordinates into the given stream.
	 * @param stream the given stream
	 */
	@Override
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeFloat(x);
		stream.writeFloat(y);
		stream.writeFloat(z);
	}
}
