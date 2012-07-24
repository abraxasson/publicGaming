package hof.core.utils;

public class Settings {
	
	public static float houseHealthpoints;
	public static float fireDamage;
	public static float waterDamage;
	
	public static void load() {
		houseHealthpoints = 1000;
		fireDamage = 1;
		waterDamage = 3;
	}
	
}
