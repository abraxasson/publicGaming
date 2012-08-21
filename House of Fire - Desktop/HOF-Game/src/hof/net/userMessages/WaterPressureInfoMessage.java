package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This messages contains information about the current water level of the player.
 *
 */
public class WaterPressureInfoMessage extends AbstractMessage {
	/**
	 * The current water level. Between 0 and 1.
	 */
	private float pressure;			

	/**
	 * Creates a new WaterPressureInfoMessage with the given water level.
	 * @param pressure the water level
	 */
	public WaterPressureInfoMessage(float pressure) {
		super(AbstractMessage.WaterPressure);
		if (pressure > 1) {
			pressure = 1;
		} else if (pressure < 0) {
			pressure = 0;
		}
		this.pressure = pressure;
	}

	/**
	 * Returns the water level.
	 * @return the water level
	 */
	public float getPressure() {
		return pressure;
	}
	
	/**
	 * Writes the water level into the given stream.
	 * @param stream the stream to write into
	 */
	@Override
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeFloat(pressure);
	}
}
