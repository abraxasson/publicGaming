package hof.start;

import hof.core.HouseOfFireGame;
import hof.core.utils.Settings;
import hof.start.InstructionsDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameLauncher {

	private JFrame frame;
	
	public GameLauncher() {
		Settings.load();
		generateGUI();
	}

	private void generateGUI() {
		frame = new JFrame("House of Fire");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FrameUtils.setIcon(frame);
		
		addButtons();
		
		frame.pack();
		frame.setSize(250, 200);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - frame.getWidth()) / 2, (d.height - frame.getHeight())/2) ;
		frame.setVisible(true);
	}
	
	private void addButtons() {
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(new GridLayout(0,1,0,7));
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startGame();
			}
		});
		contentPane.add(startButton);
		JButton instructionsButton = new JButton("Anleitung");
		instructionsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showInstructions();
			}
		});
		contentPane.add(instructionsButton);
		
		JButton highscoreButton = new JButton("Highscore");
		highscoreButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showHighscore();
			}
		});
		contentPane.add(highscoreButton);
		
		JButton settingsButton = new JButton("Einstellungen");
		settingsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showSettingDialog();
			}
		});
		contentPane.add(settingsButton);
		
		JButton endButton = new JButton("Beenden");
		endButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
				
			}
		});
		contentPane.add(endButton);
	}
	
	public void startGame() {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "House of Fire";
		cfg.useGL20 = true;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		cfg.width = d.width;
		cfg.height = d.height;
		cfg.fullscreen = true;
		cfg.resizable = false;
		new LwjglApplication(new HouseOfFireGame(), cfg);	
	}

	private void showInstructions() {
		new InstructionsDialog(frame);
		
	}

	private void showHighscore() {
		new HighscoreDialog(frame);
		
	}

	private void exit() {
		System.exit(0);
	}

	private void showSettingDialog() {
		new SettingsDialog(frame);
	}
	
	public static void main(String[] args) {
		new GameLauncher();
	}

}
