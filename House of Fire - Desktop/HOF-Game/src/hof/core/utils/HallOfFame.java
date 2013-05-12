package hof.core.utils;

import hof.player.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The Hall of Fame stores the best scores in a txt - File in the game directory. 
 * If there is no highscore yet, default values are generated.
 * The Hall of Fame is designed as a Singleton.
 *
 */
public class HallOfFame {

	/**
	 * The instance of this class.
	 */
	private static HallOfFame instance;
	/**
	 * The set where the highscore data is stored.
	 */
	private TreeSet<Item> highscoreSet;
	/**
	 * The file where the highscore data is saved.
	 */
	private File hihscoreFile;

	private HallOfFame() {
		String dirPath = Settings.highScoreFilePath.substring(0,
				Settings.highScoreFilePath.indexOf(File.separatorChar));
		File dir = new File(dirPath);
		dir.mkdir();
		hihscoreFile = new File(Settings.highScoreFilePath);

		highscoreSet = loadHighscore();


		if (highscoreSet.size() < Settings.highScoreSize) {
			for (int i = highscoreSet.size(); i < Settings.highScoreSize; i++) {
				highscoreSet.add(new Item());
			}
			saveHighscore();
		}
	}

	/**
	 * Returns the instance of this class. <br>
	 * A new instance is created if there is none.
	 * @return the instance of this class
	 */
	public static HallOfFame getInstance() {
		if (instance == null) {
			instance = new HallOfFame();
		}
		return instance;
	}

	/**
	 * Adds a player to the highscore.
	 * The player at the last position is removed.
	 * @param player - the player to add
	 */
	public void addPlayer(Player player) {
		final Item item = new Item(player.getName(), player.getScore());
		highscoreSet.add(item);
		while (highscoreSet.size() > Settings.highScoreSize) {
			highscoreSet.pollLast();
		}
		saveHighscore();
	}

	/**
	 * Saves the highscore to the txt-file.
	 */
	private void saveHighscore() {
		try {
			FileWriter fw = new FileWriter(hihscoreFile, false);
			fw.write(getHighscore());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the highscore from the txt-file
	 * @return
	 */
	private TreeSet<Item> loadHighscore() {
		TreeSet<Item> set = new TreeSet<Item>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					hihscoreFile));
			String line = reader.readLine();
			while (line != null) {
				Scanner tokenizer = new Scanner(line);

				// ignore
				tokenizer.next();

				// name and score
				String name = tokenizer.next();
				int score = tokenizer.nextInt();

				set.add(new Item(name, score));

				tokenizer.close();
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			return set;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			// ignore
		}
		return set;
	}

	/**
	 * Returns the highscore as a String 
	 * @return String in the form: <br>
	 * -rank-: -name-  -points- 
	 */
	public String getHighscore() {
		String highscore = "";
		int i = 1;
		for (Item item : highscoreSet) {
			// if (i < 10) highscore += "  ";
			highscore += i + ":  ";

			highscore += item.toString() + "\r\n";
			i++;
		}

		return highscore;
	}

	/**
	 * Draws the highscore in the middle of the screen at the top of the screen.
	 * @param spriteBatch - the spriteBatch where to draw
	 * @param font - the Font in which the text should be written
	 * @param color - the color for the text
	 */
	public void draw(SpriteBatch spriteBatch, BitmapFont font, Color color) {
		int i = 1;
		int height = Gdx.graphics.getHeight();
		Color oldColor = font.getColor();
		font.setColor(color);
		for (Item item : highscoreSet) {
			String text = "" + i + ":  " + item.toString();
			TextBounds bounds = font.getBounds(text);
			height -= bounds.height * 1.35;
			font.draw(spriteBatch, text, Gdx.graphics.getWidth() / 2
					- bounds.width / 2, height);
			i++;
		}
		font.setColor(oldColor);
	}
	
	/**
	 * Draws the highscore at the given Position.
	 * @param spriteBatch - the spriteBatch where to draw
	 * @param x - the x-Position of the text
	 * @param y - the y-Position of the text
	 * @param font - the Font in which the text should be written
	 * @param color - the color for the text
	 */
	public void draw(SpriteBatch spriteBatch, int x, int y, BitmapFont font, Color color) {
		int i = 1;
		int height = y;
		Color oldColor = font.getColor();
		font.setColor(color);
		for (Item item : highscoreSet) {
			String text = "" + i + ":  " + item.toString();
			TextBounds bounds = font.getBounds(text);
			height -= bounds.height * 1.35;
			font.draw(spriteBatch, text, Gdx.graphics.getWidth() / 2
					- bounds.width / 2, height);
			i++;
		}
		font.setColor(oldColor);
	}

	/**
	 * Draws the highscore at the given Position with the given limit.
	 * @param spriteBatch - the spriteBatch where to draw
	 * @param x - the x-Position of the text
	 * @param y - the y-Position of the text
	 * @param limit - the limit for the text
	 * @param font - the Font in which the text should be written
	 * @param color - the color for the text
	 */
	public void draw(SpriteBatch spriteBatch, int x, int y, int limit ,BitmapFont font, Color color) {
		int i = 1;
		int height = y;
		Color oldColor = font.getColor();
		font.setColor(color);
		TextBounds bounds = font.getBounds("Highscore");
		font.draw(spriteBatch, "Highscore\n", x - bounds.width / 2, height);
		for (Item item : highscoreSet) {
			String text = "" + i + ":  " + item.toString();
			bounds = font.getBounds(text);
			height -= bounds.height * 1.35;
			if(height -bounds.height > limit){
				font.draw(spriteBatch, text, x - bounds.width / 2, height);
				i++;
			}
		}
		font.setColor(oldColor);
	}

	/**
	 * Is used to store information about the players in the highscore.
	 * The players name and the players score are saved.
	 *
	 */
	class Item implements Comparable<Item> {

		private String playerName;
		private int playerScore;

		public Item() {
			playerName = "default";
			playerScore = 0;
		}
		
		public Item(final String playerName, final int playerScore) {
			this.playerName = playerName;
			this.playerScore = playerScore;
		}

		@Override
		public String toString() {
			String score = "";
			String hilf = "" + playerScore;
			int dots = hilf.length() / 3;
			if (hilf.length() % 3 == 0) {
				dots--;
			}
			while (dots > 0) {
				score = "." + hilf.substring(hilf.length() - 3, hilf.length())
						+ score;
				hilf = hilf.substring(0, hilf.length() - 3);
				dots--;
			}
			score = hilf + score;
			return playerName + "  " + score;
		}

		@Override
		public int compareTo(Item otherItem) {
			return otherItem.playerScore - playerScore;
		}
	}
}
