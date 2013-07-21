package hof.core.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Stores for the player available Colors.
 *
 */
public class ColorList {

	private static final PlayerColor DEFAULT_COLOR = PlayerColor.PINK;
	
	/**
	 * List of all colors which are used.
	 */
	private List<PlayerColor> colorList;
	/**
	 * List of all at the moment available colors.
	 */
	private List<PlayerColor> availableColors;
	/**
	 * current index.
	 */
	private int index;
	
	
	/**
	 * Creates a new instance of the color list.
	 */
	public ColorList() {
		colorList = new ArrayList<>();
		availableColors = new LinkedList<>();
		fillList();
		index = 0;
		Collections.shuffle(colorList);
		availableColors.addAll(colorList);
	}
	
	
	/**
	 * Fills the list with the six colors.
	 */
	private void fillList() {
		List<PlayerColor> allEnums = Arrays.asList(PlayerColor.values());
		colorList.addAll(allEnums);
	}
	
	/**
	 * Returns the next available color from the list.
	 * If there is none pink is returned.
	 * @return the next available color
	 */
	public PlayerColor getNextColor() {
		if (availableColors.size() > 0) {
			PlayerColor color = availableColors.remove(index % availableColors.size());
			index++;
			return color;
		} else {
			return DEFAULT_COLOR;
		}
	}
	
	/**
	 * Refills the given color in the list.
	 * @param color - the color to refill
	 */
	public void reuseColor(final PlayerColor color){
		this.availableColors.add(color);
	}
	
	/**
	 * Refills all colors in the list.
	 * Has to be called when the player-list is cleared.
	 */
	public void reuseColors(){
		availableColors.clear();
		this.availableColors.addAll(colorList);
	}
	
	public enum PlayerColor {
		RED("FF0000"),
		GREEN("00FF00"),
		BLUE("0000FF"),
		YELLOW("FFFC00"),
		MAGENTA("DE00FF"),
		ORANGE("FF9E00"),
		PINK("FF0084");
		
		private PlayerColor(final String hex) {
			this.hex = hex;
		}
		
		private String hex;
		
		public String getRGBHex() {
			return hex;
		}
	}
}
