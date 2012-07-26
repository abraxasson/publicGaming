package hof.core.utils;

import java.io.File;
import java.util.prefs.Preferences;


public class Settings {
	private static final String TELEPHONE_NUMBER_ID = "TELEPHONE_NUMBER";
	private static final String houseHealthpointsID = "houseHealthpoints";
	private static final String fireDamageID = "fireDamage";
	private static final String waterDamageID = "waterDamage";
	private static final String waterPressureIncID = "waterPressureInc";
	private static final String rainLifeTimeID = "rainLifeTime";
	private static final String lightningLifeTimeID = "lightningLifeTime";
	private static final String waterPressureLifeTimeID = "waterPressureLifeTime";
	private static final String rainDamageID = "rainDamage";
	private static final String waterAimSizeID = "waterAimSize";
	private static final String playerTimeoutID = "playerTimeout";
	private static final String maxPlayersID = "maxPlayers";
	
	private static final String settingsPath = "settings";
	private static Preferences prefs;
	
	
	public static String TELEPHONE_NUMBER = "0666/666666";
	
	public static float houseHealthpoints;	
	public static float fireDamage;	
	
	public static float waterDamage;	
	public static int waterPressureInc;	
	public static float waterAimSize;
	
	//Special Effects
	public static float rainLifeTime;	
	public static float rainDamage;
	public static float lightningLifeTime;	
	public static float waterPressureLifeTime;


	public static long playerTimeout;
	public static int maxPlayers;
	
	
	//Highscore Settings
	public static String highScoreFilePath;
	private static final String highScoreSizeID = "highScoreSize";
	public static int highScoreSize;
	
	//WordFilter Settings
	public static String filterListPath;
	
	
	public static void load() {
		prefs = Preferences.userRoot().node(settingsPath);

		houseHealthpoints = prefs.getFloat(houseHealthpointsID, 1000);
		fireDamage = prefs.getFloat(fireDamageID, 1);
		waterDamage = prefs.getFloat(waterDamageID, 3);
		waterPressureInc = prefs.getInt(waterPressureIncID, 5);
		rainLifeTime = prefs.getFloat(rainLifeTimeID, 5);
		lightningLifeTime = prefs.getFloat(lightningLifeTimeID, 1);
		waterPressureLifeTime = prefs.getFloat(waterPressureLifeTimeID, 5);
		rainDamage = prefs.getFloat(rainDamageID, 20);
		waterAimSize = prefs.getFloat(waterAimSizeID, 10);
		playerTimeout = prefs.getLong(playerTimeoutID, 5000l);
		maxPlayers = prefs.getInt(maxPlayersID, 6);

		loadHighScoreSettings();
		loadWordFilterSettings();
	}
	
	public static void savePrefs() {
		prefs.put(TELEPHONE_NUMBER_ID, TELEPHONE_NUMBER);
		
		prefs.putFloat(fireDamageID, fireDamage);			
		prefs.putFloat(houseHealthpointsID, houseHealthpoints);	
		
		prefs.putFloat(lightningLifeTimeID, lightningLifeTime);	
		prefs.putFloat(waterPressureLifeTimeID, waterPressureLifeTime);			
		prefs.putFloat(rainDamageID, rainDamage);		
		prefs.putFloat(rainLifeTimeID, rainLifeTime);
		
		prefs.putFloat(waterDamageID, waterDamage);		
		prefs.putInt(waterPressureIncID, waterPressureInc);		
		prefs.putFloat(waterAimSizeID, waterAimSize);
		
		prefs.putLong(playerTimeoutID, playerTimeout);
		prefs.putLong(maxPlayersID, maxPlayers);
		prefs.putInt(highScoreSizeID, highScoreSize);
	}
	
	private static void loadHighScoreSettings() {
		highScoreSize = prefs.getInt(highScoreSizeID, 10);
		highScoreFilePath = "Highscore" + File.separator + "highscore.txt";
	}
	
	private static void loadWordFilterSettings() {
		filterListPath = "WordFilter" + File.separator + "badwords.txt";
	}
	
	public static void main(String[] args) {
		Settings.load();
//		Settings.savePrefs();
	}
}
