package hof.start;

import java.awt.Window;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FrameUtils {

	private FrameUtils() {}
	
	public static void setIcon(Window frame) {
		try {
			frame.setIconImage(ImageIO.read(new File(
					"assets/textures/ic_launcher.png")));
		} catch (IOException e) {
			// Ignore
		}
	}

}
