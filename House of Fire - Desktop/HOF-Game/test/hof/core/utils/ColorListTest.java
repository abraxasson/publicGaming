package hof.core.utils;


import hof.core.utils.ColorList.PlayerColor;

import org.junit.Assert;
import org.junit.Test;


public class ColorListTest {

	@Test
	public void testColorsNotSame() {
		final ColorList colorList = new ColorList();
		
		final PlayerColor firstColor = colorList.getNextColor();
		PlayerColor currentColor = colorList.getNextColor();
		Assert.assertNotSame(firstColor, currentColor);
		
		PlayerColor nextColor = colorList.getNextColor();
		while (currentColor != nextColor) {
			currentColor = nextColor;
			nextColor = colorList.getNextColor();
		}
		
		final PlayerColor defaultColor = colorList.getNextColor();
		Assert.assertSame(defaultColor, currentColor);
		
		colorList.reuseColor(firstColor);
		
		final PlayerColor thirdColor = colorList.getNextColor();;
		Assert.assertSame(thirdColor, firstColor);
	}
}