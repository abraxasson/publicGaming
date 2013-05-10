package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.Settings;
import hof.level.objects.House;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;


public class HouseOfFireGame extends Game {

	Screen mainMenuScreen;
	Screen instructionsScreen;
	Screen highScoreScreen;
	Screen waitingForPlayersScreen;
	Screen playingScreen;
	Screen gameOverScreen;
	Screen levelFinishedScreen;
	Screen gameFinishedScreen;
	
	
	ArrayList<House> houseList;
	int houseIndex;
	
	@Override
	public void create() {
		Assets.load();
		Settings.load();
		houseList = new ArrayList<>();
		int flames = 10;
		int life = 0;
		for (Texture texture: Assets.houseList) {
			houseList.add(new House(texture, flames,Settings.houseHealthpoints+life));
			flames += Settings.fireIncrease;
			life += Settings.healthpointsIncrease;
		}
		houseIndex = 0;
		
		mainMenuScreen = new MainMenuScreen(this);
		instructionsScreen = new InstructionsScreen(this);
		highScoreScreen = new HighScoreScreen(this);
		waitingForPlayersScreen = new WaitingForPlayersScreen(this);
		playingScreen = new PlayingScreen(this);
		gameOverScreen = new GameOverScreen(this);
		levelFinishedScreen = new LevelFinishedScreen(this);
		gameFinishedScreen = new GameFinishedScreen(this);
		
		this.setScreen(mainMenuScreen);
	}	
	
	@Override
	public void dispose() {
		Assets.dispose();
		Settings.savePrefs();	
		mainMenuScreen.dispose();
		instructionsScreen.dispose();
		highScoreScreen.dispose();
		waitingForPlayersScreen.dispose();
		playingScreen.dispose();
		gameOverScreen.dispose();
		levelFinishedScreen.dispose();
		gameFinishedScreen.dispose();
	}
}
