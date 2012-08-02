package hof.core;

import hof.core.utils.Settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsDialog {

	private JFrame frame;
	private Preferences prefs;
	@SuppressWarnings("unused")
	private String[] keys;
	private JPanel contentPane;
	private JTabbedPane tabs;

	private JPanel telephoneNumber;
	private JPanel fireDamage;
	private JPanel houseHealthpoints;
	private JPanel fireIncrease;
	private JPanel rainKeyWord;
	private JPanel lightningKeyWord;
	private JPanel pressureKeyword;
	private JPanel lightningLifetime;
	private JPanel waterPressureLifetime;
	private JPanel rainDamage;
	private JPanel rainLifeTime;
	private JPanel rainCooldown;
	private JPanel pressureCooldown;
	private JPanel lightningCooldown;
	private JPanel waterDamage;
	private JPanel waterPressureIcrease;
	private JPanel waterAimSize;
	private JPanel playerTimeout;
	private JPanel maxPlayers;
	private JPanel highscoreSize;

	public SettingsDialog() {
		prefs = Preferences.userRoot().node("settings");
		try {
			keys = prefs.keys();
		} catch (BackingStoreException e) {
			System.out.println("loading preferences failed");
			e.printStackTrace();
		}
		createAndShowGUI();

	}

	public void createAndShowGUI() {
		frame = new JFrame("Settings");
		try {
			frame.setIconImage(ImageIO.read(new File(
					"assets/textures/ic_launche.png")));
		} catch (IOException e) {
			// Ignore
		}
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		tabs = new JTabbedPane();
		tabs.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				frame.pack();
				frame.repaint();				
			}
		});
		
		contentPane.add(tabs, BorderLayout.NORTH);
		addSettings();
		addButtons();
		frame.pack();
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2
				- frame.getHeight() / 2);
		frame.setVisible(true);
	}

	private void addSettings() {
		JPanel page1 = new JPanel(new GridLayout(2,1));
		JPanel page2 = new JPanel(new GridLayout(2,1));
		JPanel page3 = new JPanel(new GridLayout(2,1));
		tabs.addTab("Schwierigkeit", page1);
		tabs.addTab("SMS", page2);
		tabs.addTab("Allgemein", page3);
		
		
		fireDamage = addSlider(Settings.fireDamageID, 0, 3 , 1, (int)prefs.getFloat(Settings.fireDamageID, 1));
		page1.add(fireDamage);
		
		houseHealthpoints = addSlider(Settings.houseHealthpointsID, 5, 15, 1,(int) prefs.getFloat(Settings.houseHealthpointsID, 1000) / 100);
		page1.add(houseHealthpoints);
		
		fireIncrease = addSlider(Settings.fireIncreaseID, 3, 10, 1, prefs.getInt(Settings.fireIncreaseID, 5));
		page1.add(fireIncrease);
		
		waterAimSize = addSlider(Settings.waterAimSizeID, 15, 25 , 1, (int) prefs.getFloat(Settings.waterAimSizeID, 20));
		page1.add(waterAimSize);
		
		waterDamage = addSlider(Settings.waterDamageID, 2, 5, 1, (int)prefs.getFloat(Settings.waterDamageID, 3));
		page1.add(waterDamage);
		
		telephoneNumber = addTextField(Settings.TELEPHONE_NUMBER_ID,prefs.get(Settings.TELEPHONE_NUMBER_ID, "0666/666666"));
		page2.add(telephoneNumber);
		
		rainKeyWord = addTextField(Settings.rainKeyWordID,prefs.get(Settings.rainKeyWordID, "RAIN"));
		page2.add(rainKeyWord);
		
		lightningKeyWord = addTextField(Settings.lightningKeyWordID,prefs.get(Settings.lightningKeyWordID, "LIGHTNING"));
		page2.add(lightningKeyWord);
		
		pressureKeyword = addTextField(Settings.pressureKeyWordID,prefs.get(Settings.pressureKeyWordID, "PRESSURE"));
		page2.add(pressureKeyword);
		
		lightningLifetime = addSlider(Settings.lightningLifeTimeID, 1,1,0, (int)prefs.getFloat(Settings.lightningLifeTimeID, 1));
		page2.add(lightningLifetime);
		
		waterPressureLifetime = addSlider(Settings.waterPressureLifeTimeID, 5,5,0, (int) prefs.getFloat(Settings.waterPressureLifeTimeID, 1));
		page2.add(waterPressureLifetime);
		
		rainLifeTime = addSlider(Settings.rainLifeTimeID, 5,5,0, prefs.getInt(Settings.rainLifeTimeID, 5));
		page2.add(rainLifeTime);
		
		rainDamage = addSlider(Settings.rainDamageID, 10, 30, 2, (int) prefs.getFloat(Settings.rainDamageID, 20));
		page2.add(rainDamage);
		
		rainCooldown = addSlider(Settings.rainCooldownID, 5, 15, 1, (int) prefs.getFloat(Settings.rainCooldownID, 10000) / 1000);
		page2.add(rainCooldown);
		
		pressureCooldown = addSlider(Settings.pressureCooldownID, 5, 15, 1, (int) prefs.getFloat(Settings.pressureCooldownID, 10000) / 1000);
		page2.add(pressureCooldown);
		
		lightningCooldown = addSlider(Settings.lightningCooldownID, 10, 20, 2,(int) prefs.getFloat(Settings.lightningCooldownID, 10000) / 1000);
		page2.add(lightningCooldown);
		
		waterPressureIcrease = addSlider(Settings.waterPressureIncID, 3, 7, 1, prefs.getInt(Settings.waterPressureIncID, 5));
		page2.add(waterPressureIcrease);
			
		playerTimeout = addSlider(Settings.playerTimeoutID, 5, 30, 5, (int)prefs.getLong(Settings.playerTimeoutID, 10000l));
		page3.add(playerTimeout);
		
		maxPlayers = addSlider(Settings.maxPlayersID, 3, 9, 1, prefs.getInt(Settings.maxPlayersID, 6));
		page3.add(maxPlayers);
		
		highscoreSize = addSlider(Settings.highScoreSizeID, 5, 20, 1, prefs.getInt(Settings.highScoreSizeID, 10));
		page3.add(highscoreSize);
		
	}

	private JPanel addSlider(String name, int min, int max, int step, int value) {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JPanel textPanel = new JPanel();
		textPanel.add(new JLabel(name));
		panel.add(textPanel);
		
		JPanel sliderPanel = new JPanel();
		if (value < min || value > max) {
			value =  min + (int) (Math.random() * (max - min));
		}
		JSlider slider = new JSlider(min, max, value);
		slider.setMajorTickSpacing(step);
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		sliderPanel.add(slider);
		panel.add(sliderPanel);
		return panel;
	}
	
	private JPanel addTextField(String name, String text) {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JPanel textPanel = new JPanel();
		textPanel.add(new JLabel(name));
		panel.add(textPanel);
		
		JPanel fieldPanel = new JPanel();
		JTextField textField = new JTextField(text);
		textField.setPreferredSize(new Dimension(100,20));
		fieldPanel.add(textField);
		panel.add(fieldPanel);
		return panel;
	}

	private void addButtons() {
		JPanel panel = new JPanel();
		JButton button;
		button = new JButton("Ok");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveChanges();
				Settings.load();
				closeDialog();
			}
		});
		panel.add(button);

		button = new JButton("Abbruch");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeDialog();
			}
		});
		panel.add(button);
		contentPane.add(panel, BorderLayout.SOUTH);
	}

	private void closeDialog() {
		frame.dispose();
	}

	private void saveChanges() {
		prefs.put(Settings.TELEPHONE_NUMBER_ID, getFieldText(telephoneNumber));
		
		prefs.putFloat(Settings.fireDamageID, getSliderValue(fireDamage));			
		prefs.putFloat(Settings.houseHealthpointsID, getSliderValue(houseHealthpoints) * 100);	
		prefs.putInt(Settings.fireIncreaseID, getSliderValue(fireIncrease));
		
		prefs.put(Settings.rainKeyWordID, getFieldText(rainKeyWord));
		prefs.put(Settings.lightningKeyWordID, getFieldText(lightningKeyWord));
		prefs.put(Settings.pressureKeyWordID, getFieldText(pressureKeyword));
		
		prefs.putFloat(Settings.lightningLifeTimeID, getSliderValue(lightningLifetime));	
		prefs.putFloat(Settings.waterPressureLifeTimeID, getSliderValue(waterPressureLifetime));			
		prefs.putFloat(Settings.rainDamageID, getSliderValue(rainDamage));		
		prefs.putFloat(Settings.rainLifeTimeID, getSliderValue(rainLifeTime));
		prefs.putFloat(Settings.rainCooldownID, getSliderValue(rainCooldown) * 1000);
		prefs.putFloat(Settings.lightningCooldownID, getSliderValue(lightningCooldown) * 1000);
		prefs.putFloat(Settings.pressureCooldownID, getSliderValue(pressureCooldown) * 1000);
		
		prefs.putFloat(Settings.waterDamageID, getSliderValue(waterDamage));		
		prefs.putInt(Settings.waterPressureIncID, getSliderValue(waterPressureIcrease));		
		prefs.putFloat(Settings.waterAimSizeID, getSliderValue(waterAimSize));
		
		prefs.putLong(Settings.playerTimeoutID, getSliderValue(playerTimeout) * 1000);
		prefs.putLong(Settings.maxPlayersID, getSliderValue(maxPlayers));
		prefs.putInt(Settings.highScoreSizeID, getSliderValue(highscoreSize));
		
	}
	
	private String getFieldText(JPanel panel) {
		JPanel p = (JPanel) panel.getComponents()[1];
		JTextField field = (JTextField) p.getComponents()[0];
		return field.getText();
	}
	
	private int getSliderValue(JPanel panel) {
		JPanel p = (JPanel) panel.getComponents()[1];
		JSlider slider = (JSlider) p.getComponents()[0];
		return slider.getValue();
	}

	public static void main(String[] args) {
//		Settings.load();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
//				new SettingsDialog();
			}
		});
	}
}
