package hof.core;

import hof.core.utils.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;


public class HouseOfFireGame extends Game {

	Screen mainMenuScreen;
	Screen waitingForPlayersScreen;
	Screen playingScreen;
	
	@Override
	public void create() {
		Assets.load();
		
		mainMenuScreen = new MainMenuScreen(this);
		waitingForPlayersScreen = new WaitingForPlayersScreen(this);
		playingScreen = new PlayingScreen(this);
		
		this.setScreen(mainMenuScreen);
	}	
}
