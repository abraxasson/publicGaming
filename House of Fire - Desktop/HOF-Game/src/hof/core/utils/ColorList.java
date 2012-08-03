package hof.core.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;

public class ColorList {

	private ArrayList<Color> colorList;
	private LinkedList<Color> availableColors;
	private int index;
	
	public ColorList() {
		colorList = new ArrayList<Color>();
		availableColors = new LinkedList<Color>();
		fillList();
		index = 0;
		Collections.shuffle(colorList);
		availableColors.addAll(colorList);
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
		Color color = availableColors.remove(index % availableColors.size());
		index++;
		return color;
	}
	
	public void reuseColor(Color color){
		this.availableColors.add(color);
	}
}
