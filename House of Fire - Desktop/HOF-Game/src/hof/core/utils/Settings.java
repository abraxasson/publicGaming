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
	public static int rainDamage;
	public static int maxPlayers;
	public static float waterPressureLifeTime;
	public static float waterAimSize;
	public static long playerTimeout;
	
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
		lightningLifeTime = 1;
		rainDamage = 20;
		waterPressureLifeTime = 5;
		maxPlayers = 6;
		waterAimSize = 10;
		playerTimeout = 5000l;

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
