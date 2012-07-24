package hof.core.utils;

import java.io.File;


public class Settings {
	
	public static float houseHealthpoints;
	public static float fireDamage;
	public static float waterDamage;
	
	//Highscore Settings
	public static String highScoreFilePath;
	public static int highScoreSize;
	
	public static void load() {
		houseHealthpoints = 1000;
		fireDamage = 1;
		waterDamage = 3;

		loadHighScoreSettings();
	}
	
	private static void loadHighScoreSettings() {
		highScoreSize = 10;
		highScoreFilePath = "Highscore" + File.separator + "highscore.txt";
	}
	
}
