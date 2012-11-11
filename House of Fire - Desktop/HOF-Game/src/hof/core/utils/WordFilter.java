package hof.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * This class is used to check if the used name is okay. <br>
 * Bad words are listed in a txt-file and that file can easily be extended.
 */
public class WordFilter {

	/**
	 * The file where the bad words are stored.
	 */
	private File wordFile;
	/**
	 * The bad words list.
	 */
	private HashSet<String> wordSet;
	
	/**
	 * Loads the bad words list. 
	 */
	public WordFilter() {
		String dirPath = Settings.filterListPath.substring(0, Settings.filterListPath.indexOf(File.separatorChar));
		File dir = new File(dirPath);
		dir.mkdir();
		wordFile = new File(Settings.filterListPath);
		
		wordSet = loadWords();
		rewriteList();
	}
	
	/**
	 * Splits the given String in single words and checks the words if they are contained in the bad words list.
	 * @param word - the word to check
	 * @return true if the word is okay, else false
	 */
	private boolean checkWord(String word) {
		String testWord = word.toLowerCase().trim();
		ArrayList<String> words = new ArrayList<>();
		boolean ok = true;
		while (testWord.contains(" ")) {
			int index = testWord.indexOf(" ");			
			words.add(testWord.substring(0, index));
			testWord = testWord.substring(index).trim();
		} 
		words.add(testWord);
		
		for (String string: words) {
			if (wordSet.contains(string)) {
				ok = false;
			}
		}
		
		return ok;
	}
	
	/**
	 * Checks the given name if it is okay.
	 * @param name - the name to check
	 * @return the name if it is okay, otherwise return a random name
	 */
	public String checkName(String name) {
		if (checkWord(name)) {
			return name;
		} else {
			return generateName();
		}
	}
	
	/**
	 * Loads the bad words from the txt-file and saves them in a set.
	 * @return
	 */
	private HashSet<String> loadWords() {
		HashSet<String> set = new HashSet<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(wordFile));
			String line = reader.readLine();
			while (line != null) {
				String name = line.toLowerCase().trim();

				set.add(name);
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
	
	/**
	 * Generates a random name.
	 * @return a random name
	 */
	private String generateName() {
		String [] wordList = { "Adam Baum", "Al Bino", "Armand Hammer",
				"Barb E. Dahl", "Barry Cade", "Bill Foldes", "Brighton Early",
				"Bud Light", "Cam Payne", "Chris Cross", "Chuck U. Farley",
				"Crystal Claire Waters", "Dick Burns", "Dick Mussell",
				"Dr. E. Ville", "Easton West", "Fanny O'Rear", "Forrest Green",
				"Harry R. M. Pitts", "Hazle Nutt", "Heidi Clare", "Helen Back",
				"Helen Waite", "Herb Rice", "Holly McRell", "Holly Day",
				"Holly Wood", "Hugh Jass", "Hugh Jorgan", "Hugh Morris",
				"Hy Ball", "Bea Lowe", "Hy Marx", "I.D. Clair", "I. Lasch",
				"I.M. Boring", "I.P. Freely", "Ileane Wright", "Ima Hogg",
				"Iona Ford", "Iona Frisbee", "Iona Stonehouse", "Ivan Oder",
				"Ivana Mandic", "Ivy Leage", "Jack Goff", "Jack Haas",
				"Jack Hammer", "Jack Knoff", "Jack Tupp", "Jay Walker",
				"Jean Poole", "Jed Dye", "Jenny Tull", "Jim Laucher",
				"Jim Shorts", "Jo King", "Joe Kerr", "Jordan Rivers",
				"Joy Kil", "Joy Rider", "June Bugg", "Justin Case",
				"Justin Casey Howells", "Justin Miles North", "Kandi Apple",
				"Keelan Early", "Kelly Green", "Kenny Penny", "Kenya Dewit",
				"Kerry Oki", "King Queene", "Lake Speed", "Lance Boyle",
				"Laura Lynne Hardy", "Laura Norder", "Leigh King", "Les Moore",
				"Les Payne", "Les Plack", "Levon Coates", "Mike Easter",
				"Mike Hunt", "Mike Raffone", "Mike Rotch", "Mike Stand",
				"Mike Sweeney", "Minny van Gogh", "Mister Bates", "Neil Down",
				"Neil McNeil", "Nick O. Time", "Noah Riddle", "Noah Lott",
				"Norma Leigh Lucid", "Olive Yew", "Penny Lane",
				"Penny Nichols", "Penny Wise", "Pepe Roni", "Pete Moss",
				"Peter Johnson", "Peter Peed", "Peter Wacko", "Phil Bowles",
				"Phil Graves", "Phil Rupp", "Pierce Cox", "Rhoda Booke",
				"Richard P. Cox", "Richard Chopp", "Rick O'Shea", "Rick Shaw",
				"Rip Torn", "Robin Andis Merryman", "Robin Banks",
				"Robert Soles", "Rock Pounder", "Rocky Beach",
				"Rocky Mountain", "Rocky Rhoades", "Rod N. Reel",
				"Roman Holiday", "Rose Bush", "Sandy Banks", "Seth Poole",
				"Seymour Butz", "Seymour Wiener", "Shanda Lear",
				"Sharon Fillerup", "Sharon Needles", "Sheila Blige",
				"Skip Roper", "Sonny Day", "Sno White", "Stan Still",
				"Stanley Cupp", "Dr. Steven Sumey", "Sue Flay",
				"Sue Yu, Sue Jeu", "Summer Camp", "Summer Day",
				"Summer Greene", "Sy Burnette", "Tad Moore", "Tad Pohl",
				"Tanya Hyde", "Terry Achey", "Terry Bull", "Tess Steckle",
				"Therese R. Green", "Thomas Richard Harry", "Tim Burr",
				"Tom A. Toe", "Tom Katt", "Tom Morrow", "Tommy Gunn",
				"Tommy Hawk", "Virginia Beach", "Walter Melon", "Wanda Rinn",
				"Wanna Hickey", "Warren Peace", "Warren T.", "Will Power",
				"Will Race", "Will Wynn", "Willie B. Hardigan", "Willie Leak",
				"Willie Stroker", "Winsom Cash", "Woody Forrest", "X. Benedict" };
		Random rand = new Random();
		return wordList[rand.nextInt(wordList.length)];
	}
	
	/**
	 * Writes the Set to the txt-file.
	 */
	private void rewriteList() {
		try {
			FileWriter fw = new FileWriter(wordFile, false);
			for (String string : wordSet) {
				fw.write(string + "\n");
			}
			
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}