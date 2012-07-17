package hof.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.awt.*;


public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "HouseOfFire";
		cfg.useGL20 = true;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		cfg.width = d.width;
		cfg.height = d.height;
		cfg.fullscreen = true;
		cfg.resizable = false;
		new LwjglApplication(new Game(), cfg);	
	}
}
