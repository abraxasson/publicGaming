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
		for (Texture texture: Assets.houseList) {
			houseList.add(new House(texture, flames));
		}
		
		houseIndex = 0;
		
		mainMenuScreen = new MainMenuScreen(this);
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
	}
}
