package hof.core.utils;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * Class where all important Settings are stored and can be loaded from here. <br>
 * The Settings are saved in Preferences and can be modified through the {@link hof.core.SettingsDialog Settings-Dialog}.
 *
 */
public class Settings {
	/**
	 * The keyword for the {@link TELEPHONE_NUMBER} in the Preferences.
	 */
	public static final String TELEPHONE_NUMBER_ID = "Telephone Number";
	/**
	 * The keyword for the {@link houseHealthpointsIncrease} in the Preferences.
	 */
	public static final String houseHealthpointsIncreaseID = "House Healthpoints Increase";
	/**
	 * The keyword for the {@link fireDamage} in the Preferences.
	 */
	public static final String fireDamageID = "Fire Damage";
	/**
	 * The keyword for the {@link waterDamage} in the Preferences.
	 */
	public static final String waterDamageID = "Water Damage";
	/**
	 * The keyword for the {@link waterPressureInc} in the Preferences.
	 */
	public static final String waterPressureIncID = "Water Pressure Increase";
	/**
	 * The keyword for the {@link rainDamage} in the Preferences.
	 */
	public static final String rainDamageID = "Rain Damage";
	/**
	 * The keyword for the {@link waterAimSize} in the Preferences.
	 */
	public static final String waterAimSizeID = "Wateraim-Size";
	/**
	 * The keyword for the {@link playerTimeout} in the Preferences.
	 */
	public static final String playerTimeoutID = "Playertimeout";
	/**
	 * The keyword for the {@link maxPlayers} in the Preferences.
	 */
	public static final String maxPlayersID = "Max Players";
	/**
	 * The keyword for the {@link highScoreSize} in the Preferences.
	 */
	public static final String highScoreSizeID = "Highscore Size";
	/**
	 * The keyword for the {@link fireIncrease} in the Preferences.
	 */
	public static final String fireIncreaseID = "Fire-Increase";
	/**
	 * The keyword for the {@link rainKeyWord} in the Preferences.
	 */
	public static final String rainKeyWordID = "Rain-KeyWord";
	/**
	 * The keyword for the {@link lightningKeyWord} in the Preferences.
	 */
	public static final String lightningKeyWordID = "Lightning-KeyWord";
	/**
	 * The keyword for the {@link pressureKeyword} in the Preferences.
	 */
	public static final String pressureKeyWordID = "Pressure-KeyWord";
	/**
	 * The keyword for the {@link rainCooldown} in the Preferences.
	 */
	public static final String rainCooldownID = "Rain Cooldown";
	/**
	 * The keyword for the {@link lightningCooldown} in the Preferences.
	 */
	public static final String lightningCooldownID = "Lightning Cooldown";
	/**
	 * The keyword for the {@link pressureCooldown} in the Preferences.
	 */
	public static final String pressureCooldownID = "Pressure Cooldown";

	
	private static final String settingsPath = "settings";
	private static Preferences prefs;
	
	/**
	 * The telephone number for the SMS-Effects.
	 */
	public static String TELEPHONE_NUMBER; 
	
	/**
	 * The health-points for the levels. Cannot be changed in the {@link hof.core.SettingsDialog Settings-Dialog}.
	 */
	public static final float houseHealthpoints = 1000;
	
	/**
	 * Indicates how much the healthpoints of the houses increase per level.
	 */
	public static int healthpointsIncrease;
	/**
	 * Indicates how much a single flame damages the house.
	 */
	public static float fireDamage;	
	/**
	 * Indicates how much more flames are each level.
	 */
	public static int fireIncrease;
	
	/**
	 * Indicates how much the water-jet damages the fire.
	 */
	public static float waterDamage;
	/**
	 * Determines the sizes of the collision-detection rectangle at the top of the water-jet.
	 */
	public static float waterAimSize;
	
	//Special Effects

	/**
	 * Indicates how much damage the rain inflicts to the fire.
	 */
	public static float rainDamage;
	/**
	 * Indicates the cooldown for the <b>rain</b> effect.
	 */
	public static float rainCooldown;
	/**
	 * Indicates the cooldown for the <b>lightning</b> effect.
	 */
	public static float lightningCooldown;
	/**
	 * Indicates the cooldown for the <b>pressure</b> effect.
	 */
	public static float pressureCooldown;
	/**
	 * The keyword for the <b>rain</b> effect displayed on the screen.
	 */
	public static String rainKeyWord;
	/**
	 * The keyword for the <b>lightning</b> effect displayed on the screen.
	 */
	public static String lightningKeyWord;
	/**
	 * The keyword for the <b>pressure</b> effect displayed on the screen.
	 */
	public static String pressureKeyWord;
	/**
	 * Determines how much the pressure effect increases the {@link waterAimSize}.
	 */
	public static int waterPressureInc;	

	//Lifetime is not settable in dialog
	/**
	 * How long the rain effect is displayed on the screen.
	 * <br> Cannot be changed in the {@link hof.core.SettingsDialog Settings-Dialog}.
	 */
	public static final float rainLifeTime = 5;	
	/**
	 * How long the lightning effect is displayed on the screen.
	 * <br> Cannot be changed in the {@link hof.core.SettingsDialog Settings-Dialog}.
	 */
	public static final float lightningLifeTime = 1;	
	/**
	 * How long the pressure effect is displayed on the screen.
	 * <br> Cannot be changed in the {@link hof.core.SettingsDialog Settings-Dialog}.
	 */
	public static final float waterPressureLifeTime = 5;
	//--
	
	/**
	 * Indicates after what time the players are kicked out of the game.
	 */
	public static long playerTimeout;
	/**
	 * Determines how many players are allowed to play the game.
	 */
	public static int maxPlayers;
	
	
	//Highscore Settings
	/**
	 * The path where the highscore is stored.
	 */
	public static String highScoreFilePath;
	/**
	 * Determines how many players are saved in the highscore.
	 */
	public static int highScoreSize;
	
	//WordFilter Settings
	/**
	 * The path where the bad words list is saved.
	 */
	public static String filterListPath;
	
	/**
	 * All settings are loaded from the Preferences, if there is no Preference file to be found the default vlaue is used.
	 */
	public static void load() {
		prefs = Preferences.userRoot().node(settingsPath);

		TELEPHONE_NUMBER = prefs.get(TELEPHONE_NUMBER_ID, "0681/20675089");
		healthpointsIncrease = prefs.getInt(houseHealthpointsIncreaseID, 200);
		fireDamage = prefs.getFloat(fireDamageID, 1);
		waterDamage = prefs.getFloat(waterDamageID, 3);
		waterPressureInc = prefs.getInt(waterPressureIncID, 5);
		rainDamage = prefs.getFloat(rainDamageID, 20);
		waterAimSize = prefs.getFloat(waterAimSizeID, 25);
		playerTimeout = prefs.getLong(playerTimeoutID, 10000l);
		maxPlayers = prefs.getInt(maxPlayersID, 6);
		rainKeyWord = prefs.get(rainKeyWordID, "RAIN");
		lightningKeyWord = prefs.get(lightningKeyWordID, "LIGHTNING");
		pressureKeyWord = prefs.get(pressureKeyWordID, "PRESSURE");
		fireIncrease = prefs.getInt(fireIncreaseID, 5);
		rainCooldown = prefs.getFloat(rainCooldownID, 10000);
		lightningCooldown = prefs.getFloat(lightningCooldownID, 15000);
		pressureCooldown = prefs.getFloat(pressureCooldownID, 10000);

		loadHighScoreSettings();
		loadWordFilterSettings();
	}
	
	/**
	 * Saves the current value into the Preferences file.
	 * This will be called by the  {@link hof.core.SettingsDialog Settings-Dialog} upon saving.
	 */
	public static void savePrefs() {
		prefs.put(TELEPHONE_NUMBER_ID, TELEPHONE_NUMBER);
		
		prefs.putFloat(fireDamageID, fireDamage);			
		prefs.putFloat(houseHealthpointsIncreaseID, healthpointsIncrease);	
		prefs.putInt(fireIncreaseID, fireIncrease);
		
		prefs.put(rainKeyWordID, rainKeyWord);
		prefs.put(lightningKeyWordID, lightningKeyWord);
		prefs.put(pressureKeyWordID, pressureKeyWord);
		
		prefs.putFloat(rainDamageID, rainDamage);		
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
}
