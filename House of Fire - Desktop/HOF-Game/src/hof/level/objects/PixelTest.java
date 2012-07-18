package hof.level.objects;


import java.awt.Color;

public class PixelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		House house = new House();
		house.setBurningArea(255,242,10, "TestHouseFire3.png");
		//house.setBurningArea(Color.black);
		System.out.println(house.getRandomBurningArea().toString());
	}

}
