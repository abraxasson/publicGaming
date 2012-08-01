package hof.core;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "House of Fire";
		cfg.useGL20 = true;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		cfg.width = d.width;
		cfg.height = d.height;
		cfg.fullscreen = true;
		cfg.resizable = false;
		new LwjglApplication(new HouseOfFireGame(), cfg);	
	}
}
