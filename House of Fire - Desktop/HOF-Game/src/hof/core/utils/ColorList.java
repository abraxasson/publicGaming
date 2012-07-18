package hof.core.utils;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class ColorList {

	private ArrayList<Color> colorList;
	private int index;
	
	public ColorList() {
		colorList = new ArrayList<Color>();
		fillList();
		index = 0;
	}
	
	private void fillList() {
		colorList.add(Color.RED);
		colorList.add(Color.BLUE);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.WHITE);
		colorList.add(Color.BLACK);
	}
	
	public Color getNextColor() {
		if (index < colorList.size()) 
		return colorList.get(index++);
		else return null;
	}
}
