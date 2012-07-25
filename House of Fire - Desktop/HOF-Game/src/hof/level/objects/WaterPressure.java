package hof.level.objects;

import hof.core.utils.Settings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WaterPressure extends AbstractCloud {
	private static final int WATERPRESSUREINC = Settings.waterPressureInc;

	public WaterPressure(){
		this.type = AbstractCloud.WATERPRESSURE;
	}
	
	public static int getValue() {
		return WATERPRESSUREINC;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		//TODO: Schrift erscheint: "Wasserdruck erhöht"
	}
	
}
