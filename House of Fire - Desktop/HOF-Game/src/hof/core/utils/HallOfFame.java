package hof.core.utils;

import hof.player.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class HallOfFame {
	
	private static HallOfFame instance;
	private TreeSet<Item> highscoreSet;
	private File hihscoreFile;
	private BitmapFont font;
	
	private HallOfFame() {
		String dirPath = Settings.highScoreFilePath.substring(0, Settings.highScoreFilePath.indexOf(File.separatorChar));
		File dir = new File(dirPath);
		dir.mkdir();
		hihscoreFile = new File(Settings.highScoreFilePath);
		
		highscoreSet = loadHighscore();
		
		font = Assets.text50Font;
		
		if (highscoreSet.size() < Settings.highScoreSize) {
			for (int i = highscoreSet.size(); i < Settings.highScoreSize; i++) {
				highscoreSet.add(new Item());
			}
			saveHighscore();
		}
	}
	
	public static HallOfFame getInstance() {
		if (instance == null) {
			instance = new HallOfFame();
		}
		return instance;
	}
	
	public void addPlayer(Player player) {
		highscoreSet.add(new Item(player));
		while (highscoreSet.size() > Settings.highScoreSize) {
			highscoreSet.pollLast();
		}
		saveHighscore();
	}
	
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
	
	private TreeSet<Item> loadHighscore() {
		TreeSet<Item> set = new TreeSet<Item>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(hihscoreFile));
			String line = reader.readLine();
			while (line != null) {
				Scanner tokenizer = new Scanner(line);
				
				//ignore
				tokenizer.next();
				
				//name and score
				String name = tokenizer.next();
				int score = tokenizer.nextInt();
				
				Player player = new Player(name, null);
				player.setScore(score);
				set.add(new Item(player));
				
				tokenizer.close();
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			return set;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			//ignore
		}
		return set;
	}
	
	public String getHighscore() {
		String highscore = "";
		int i = 1;
		for (Item item : highscoreSet) {
//			if (i < 10) highscore += "  ";
			highscore += i + ":  "; 
			
			highscore += item.toString() + "\r\n";
			i++;
		}
		
		return highscore;
	}
	
	public void draw(SpriteBatch spriteBatch) {
		int i = 1;
		int height = Gdx.graphics.getHeight();
		for (Item item : highscoreSet) {
			String text = "" + i + ":  " + item.toString();
			TextBounds bounds = font.getBounds(text);
			height -= bounds.height * 1.35;
			font.draw(spriteBatch, text, Gdx.graphics.getWidth()/2 - bounds.width /2, height);
			i++;
		}
	}
	
	public void draw(SpriteBatch spriteBatch, int x, int y){
		int i = 1;
		int height = y;
		for (Item item : highscoreSet) {
			String text = "" + i + ":  " + item.toString();
			TextBounds bounds = font.getBounds(text);
			height -= bounds.height * 1.35;
			font.draw(spriteBatch, text, x - bounds.width /2, height);
			i++;
		}
	}
	
	class Item implements Comparable<Item>{
		
		private String playerName;
		private int playerScore;
		private Date date;
		
		public Item() {
			playerName = "default";
			playerScore = 0;
			date = new Date();
		}
		
		public Item (Player player) {
			playerName = player.getName();
			playerScore = player.getScore();
			date = new Date();
		}
		
		@Override
		public String toString() {
			return playerName + "  " + playerScore;
		}

		@Override
		public int compareTo(Item otherItem) {
			if (playerScore > otherItem.playerScore) {
				return -1;
			} else if (playerScore < otherItem.playerScore) {
				return 1;
			} else  {
				if (date.after(otherItem.date)) {
					return 1;
				} else {
					return -1;
				}
			}
		}
	}
}
