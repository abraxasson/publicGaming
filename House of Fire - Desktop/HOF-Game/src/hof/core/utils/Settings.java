package hof.core.utils;

import java.io.File;
import java.util.prefs.Preferences;


public class Settings {
	public static final String TELEPHONE_NUMBER_ID = "Telephone Number";
	public static final String houseHealthpointsID = "House Healthpoints";
	public static final String fireDamageID = "Fire Damage";
	public static final String waterDamageID = "Water Damage";
	public static final String waterPressureIncID = "Water Pressure Increase";
	public static final String rainLifeTimeID = "Rain Life Time";
	public static final String lightningLifeTimeID = "Lightning Lifetime";
	public static final String waterPressureLifeTimeID = "Water Pressure Lifetime";
	public static final String rainDamageID = "Rain Damage";
	public static final String waterAimSizeID = "Wateraim-Size";
	public static final String playerTimeoutID = "Playertimeout";
	public static final String maxPlayersID = "Max Players";
	public static final String highScoreSizeID = "Highscore Size";
	public static final String fireIncreaseID = "Fire-Increase";
	public static final String rainKeyWordID = "Rain-KeyWord";
	public static final String lightningKeyWordID = "Lightning-KeyWord";
	public static final String pressureKeyWordID = "Pressure-KeyWord";
	public static final String rainCooldownID = "Rain Cooldown";
	public static final String lightningCooldownID = "Lightning Cooldown";
	public static final String pressureCooldownID = "Pressure Cooldown";

	
	private static final String settingsPath = "settings";
	private static Preferences prefs;
	
	
	public static String TELEPHONE_NUMBER = "0666/666666";
	
	public static float houseHealthpoints;	
	public static float fireDamage;	
	public static int fireIncrease;
	public static int healthpointsIncrease;
	
	public static float waterDamage;	
	public static int waterPressureInc;	
	public static float waterAimSize;
	
	//Special Effects
	public static float rainLifeTime;	
	public static float rainDamage;
	public static float lightningLifeTime;	
	public static float waterPressureLifeTime;
	public static float rainCooldown;
	public static float lightningCooldown;
	public static float pressureCooldown;

	public static String rainKeyWord;
	public static String lightningKeyWord;
	public static String pressureKeyWord;

	public static long playerTimeout;
	public static int maxPlayers;
	
	
	//Highscore Settings
	public static String highScoreFilePath;
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
		waterAimSize = prefs.getFloat(waterAimSizeID, 20);
		playerTimeout = prefs.getLong(playerTimeoutID, 10000l);
		maxPlayers = prefs.getInt(maxPlayersID, 6);
		rainKeyWord = prefs.get(rainKeyWordID, "RAIN");
		lightningKeyWord = prefs.get(lightningKeyWordID, "LIGHTNING");
		pressureKeyWord = prefs.get(pressureKeyWordID, "PRESSURE");
		fireIncrease = prefs.getInt(fireIncreaseID, 5);
		rainCooldown = prefs.getFloat(rainCooldownID, 10000);
		lightningCooldown = prefs.getFloat(lightningCooldownID, 15000);
		pressureCooldown = prefs.getFloat(pressureCooldownID, 10000);
		healthpointsIncrease = 200;

		loadHighScoreSettings();
		loadWordFilterSettings();
	}
	
	public static void savePrefs() {
		prefs.put(TELEPHONE_NUMBER_ID, TELEPHONE_NUMBER);
		
		prefs.putFloat(fireDamageID, fireDamage);			
		prefs.putFloat(houseHealthpointsID, houseHealthpoints);	
		prefs.putInt(fireIncreaseID, fireIncrease);
		
		prefs.put(rainKeyWordID, rainKeyWord);
		prefs.put(lightningKeyWordID, lightningKeyWord);
		prefs.put(pressureKeyWordID, pressureKeyWord);
		
		prefs.putFloat(lightningLifeTimeID, lightningLifeTime);	
		prefs.putFloat(waterPressureLifeTimeID, waterPressureLifeTime);			
		prefs.putFloat(rainDamageID, rainDamage);		
		prefs.putFloat(rainLifeTimeID, rainLifeTime);
		prefs.putFloat(rainCooldownID, rainCooldown);
		prefs.putFloat(lightningCooldownID, lightningCooldown);
		prefs.putFloat(pressureCooldownID, pressureCooldown);
		
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
		Settings.savePrefs();
	}
}
