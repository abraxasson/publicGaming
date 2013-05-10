package hof.core;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


/**
 *	Main-class to start the game
 */
public class Main {

	/**
	 * Used to configure the LwjglApplication and start the game.
	 */
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "House of Fire";
		cfg.useGL20 = true;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		cfg.width = d.width;
		cfg.height = d.height;
		cfg.fullscreen = true;
		cfg.resizable = false;
		cfg.addIcon("textures/icon_32x32.png", FileType.Internal);
		new LwjglApplication(new HouseOfFireGame(), cfg);	
	}
}
