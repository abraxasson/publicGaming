package hof.net.userMessages;

import java.io.DataOutputStream;
import java.io.IOException;

public class WaterPressureInfoMessage extends AbstractMessage {
	private static final long serialVersionUID = 37295872L;
	private float pressure;			//Wasserdruck in Prozent

	public WaterPressureInfoMessage(float pressure) {
		super(AbstractMessage.WaterPressure);
		if (pressure > 1) {
			pressure = 1;
		} else if (pressure < 0) {
			pressure = 0;
		}
		this.setPressure(pressure);
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		if (pressure > 1) {
			pressure = 1;
		} else if (pressure < 0) {
			pressure = 0;
		}
		this.pressure = pressure;
	}
	
	public void serialize(DataOutputStream stream) throws IOException {
		super.serialize(stream);
		stream.writeFloat(pressure);
	}
}
