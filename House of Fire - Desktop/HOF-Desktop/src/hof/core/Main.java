package hof.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.awt.*;


public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
		cfg.title = "HouseOfFire";
		cfg.useGL20 = true;
		cfg.width = d.width;
		cfg.height = d.height;
		cfg.setFromDisplayMode(new FullScreen(d.width,d.height,60,32));
		new LwjglApplication(new Game(), cfg);		
	}
}
