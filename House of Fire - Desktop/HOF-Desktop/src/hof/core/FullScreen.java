package hof.core;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Graphics.DisplayMode;

public class FullScreen extends DisplayMode{

	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
	
	public FullScreen(int width, int height, int refreshRate,
			int bitsPerPixel) {
		super(width, height, refreshRate, bitsPerPixel);
	}
	
	

}
