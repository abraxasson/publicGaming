package hof.core;

import java.util.ArrayList;

import hof.core.utils.Assets;
import hof.core.utils.Settings;
import hof.level.objects.House;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;


public class HouseOfFireGame extends Game {

	Screen mainMenuScreen;
	Screen waitingForPlayersScreen;
	Screen playingScreen;
	Screen gameOverScreen;
	Screen levelFinishedScreen;
	
	ArrayList<House> houseList;
	int houseIndex;
	
	@Override
	public void create() {
		Assets.load();
		Settings.load();
		houseList = new ArrayList<>();
		houseList.add(new House(Assets.houseTexture, 20));
		houseIndex = 0;
		
		mainMenuScreen = new MainMenuScreen(this);
		waitingForPlayersScreen = new WaitingForPlayersScreen(this);
		playingScreen = new PlayingScreen(this);
		gameOverScreen = new GameOverScreen(this);
		levelFinishedScreen = new LevelFinishedScreen(this);
		
		this.setScreen(mainMenuScreen);
	}	
	
	@Override
	public void dispose() {
		Assets.dispose();
	}
}
