package hof.core.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;

/**
 * Stores for the player available Colors.
 *
 */
public class ColorList {

	/**
	 * List of all colors which are used.
	 */
	private ArrayList<Color> colorList;
	/**
	 * List of all at the moment available colors.
	 */
	private LinkedList<Color> availableColors;
	/**
	 * current index.
	 */
	private int index;
	
	/**
	 * Creates, fills and shuffles all lists.
	 */
	public ColorList() {
		colorList = new ArrayList<Color>();
		availableColors = new LinkedList<Color>();
		fillList();
		index = 0;
		Collections.shuffle(colorList);
		availableColors.addAll(colorList);
	}
	
	
	/**
	 * Fills the list with the six colors.
	 */
	private void fillList() {
		colorList.add(Color.RED);
		colorList.add(Color.BLUE);
		colorList.add(Color.ORANGE);
		colorList.add(Color.GREEN);
		colorList.add(Color.CYAN);
		colorList.add(Color.MAGENTA);
	}
	
	/**
	 * Returns the next available color from the list.
	 * If there is none pink is returned.
	 * @return the next available color
	 */
	public Color getNextColor() {
		if (availableColors.size() > 0) {
			Color color = availableColors.remove(index % availableColors.size());
			index++;
			return color;
		} else {
			return Color.PINK;
		}
		
	}
	
	/**
	 * Refills the given color in the list.
	 * @param color - the color to refill
	 */
	public void reuseColor(Color color){
		this.availableColors.add(color);
	}
	
	/**
	 * Refills all colors in the list.
	 * Has to be called when the player-list is cleared.
	 */
	public void reuseColors(){
		this.availableColors.addAll(colorList);
	}
}
