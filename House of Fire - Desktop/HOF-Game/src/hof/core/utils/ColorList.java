package hof.core.utils;


import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Color;

public class ColorList {

	private ArrayList<Color> colorList;
	private int index;
	
	public ColorList() {
		colorList = new ArrayList<Color>();
		fillList();
		index = 0;
		Collections.shuffle(colorList);
	}
	
	private void fillList() {
		colorList.add(Color.RED);
		colorList.add(Color.BLUE);
		colorList.add(Color.ORANGE);
		colorList.add(Color.GREEN);
		colorList.add(Color.CYAN);
		colorList.add(Color.MAGENTA);
	}
	
	public Color getNextColor() {
		Color color = colorList.get(index % colorList.size());
		index++;
		return color;
	}
}
