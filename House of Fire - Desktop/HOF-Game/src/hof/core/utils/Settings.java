package hof.core.utils;

import java.io.File;


public class Settings {
	
	public static String TELEPHONE_NUMBER = "0666/666666";
	public static float houseHealthpoints;
	public static float fireDamage;
	public static float waterDamage;
	public static int waterPressureInc;
	public static float rainLifeTime;
	public static float lightningLifeTime;
	
	//Highscore Settings
	public static String highScoreFilePath;
	public static int highScoreSize;
	
	//WordFilter Settings
	public static String filterListPath;
	
	public static void load() {
		houseHealthpoints = 1000;
		fireDamage = 1;
		waterDamage = 3;
		waterPressureInc = 5;
		rainLifeTime = 5;
		lightningLifeTime = 2;

		loadHighScoreSettings();
		loadWordFilterSettings();
	}
	
	private static void loadHighScoreSettings() {
		highScoreSize = 10;
		highScoreFilePath = "Highscore" + File.separator + "highscore.txt";
	}
	
	private static void loadWordFilterSettings() {
		filterListPath = "WordFilter" + File.separator + "badwords.txt";
	}
	
}
