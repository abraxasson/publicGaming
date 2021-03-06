package hof.start;

import hof.core.utils.Settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsDialog {

	private JDialog dialog;
	private Preferences prefs;
	@SuppressWarnings("unused")
	private String[] keys;
	private JPanel contentPane;
	private JTabbedPane tabs;

	private JPanel telephoneNumber;
	private JPanel fireDamage;
	private JPanel houseHealthpointsIncrease;
	private JPanel fireIncrease;
	private JPanel rainKeyWord;
	private JPanel lightningKeyWord;
	private JPanel pressureKeyword;
	private JPanel rainDamage;
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
		this(null);
	}

	public SettingsDialog(JFrame parent) {
		prefs = Preferences.userRoot().node("settings");
		try {
			keys = prefs.keys();
		} catch (BackingStoreException e) {
			System.out.println("loading preferences failed");
			// TODO logger
			e.printStackTrace();
		}
		createAndShowGUI(parent);

	}

	public void createAndShowGUI(JFrame parent) {
		dialog = new JDialog(parent, "Settings", true);
		FrameUtils.setIcon(dialog);

		contentPane = (JPanel) dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		tabs = new JTabbedPane();
		tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				dialog.pack();
				dialog.repaint();
			}
		});

		contentPane.add(tabs, BorderLayout.NORTH);
		addSettings();
		addButtons();

		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}

	private void addSettings() {
		JPanel page1 = new JPanel(new GridLayout(2, 1));
		JPanel page2 = new JPanel(new GridLayout(2, 1));
		JPanel page3 = new JPanel(new GridLayout(2, 1));
		tabs.addTab("Schwierigkeit", page1);
		tabs.addTab("SMS", page2);
		tabs.addTab("Allgemein", page3);

		fireDamage = addSlider(Settings.fireDamageID, 0, 3, 1,
				(int) Settings.fireDamage);
		page1.add(fireDamage);
		houseHealthpointsIncrease = addSlider(
				Settings.houseHealthpointsIncreaseID, 100, 500, 50,
				(int) Settings.healthpointsIncrease);
		page1.add(houseHealthpointsIncrease);
		fireIncrease = addSlider(Settings.fireIncreaseID, 3, 10, 1,
				Settings.fireIncrease);
		page1.add(fireIncrease);
		waterAimSize = addSlider(Settings.waterAimSizeID, 20, 30, 1,
				(int) Settings.waterAimSize);
		page1.add(waterAimSize);
		waterDamage = addSlider(Settings.waterDamageID, 2, 5, 1,
				(int) Settings.waterDamage);
		page1.add(waterDamage);

		telephoneNumber = addTextField(Settings.TELEPHONE_NUMBER_ID,
				Settings.TELEPHONE_NUMBER);
		page2.add(telephoneNumber);
		rainKeyWord = addTextField(Settings.rainKeyWordID, Settings.rainKeyWord);
		page2.add(rainKeyWord);
		lightningKeyWord = addTextField(Settings.lightningKeyWordID,
				Settings.lightningKeyWord);
		page2.add(lightningKeyWord);
		pressureKeyword = addTextField(Settings.pressureKeyWordID,
				Settings.pressureKeyWord);
		page2.add(pressureKeyword);
		rainDamage = addSlider(Settings.rainDamageID, 10, 30, 2,
				(int) Settings.rainDamage);
		page2.add(rainDamage);
		rainCooldown = addSlider(Settings.rainCooldownID, 5, 15, 1,
				(int) Settings.rainCooldown / 1000);
		page2.add(rainCooldown);
		pressureCooldown = addSlider(Settings.pressureCooldownID, 5, 15, 1,
				(int) Settings.pressureCooldown / 1000);
		page2.add(pressureCooldown);
		lightningCooldown = addSlider(Settings.lightningCooldownID, 10, 20, 2,
				(int) Settings.lightningCooldown / 1000);
		page2.add(lightningCooldown);
		waterPressureIcrease = addSlider(Settings.waterPressureIncID, 3, 7, 1,
				Settings.waterPressureInc);
		page2.add(waterPressureIcrease);

		playerTimeout = addSlider(Settings.playerTimeoutID, 5, 30, 5,
				(int) Settings.playerTimeout / 1000);
		page3.add(playerTimeout);
		maxPlayers = addSlider(Settings.maxPlayersID, 3, 9, 1,
				Settings.maxPlayers);
		page3.add(maxPlayers);
		highscoreSize = addSlider(Settings.highScoreSizeID, 5, 15, 1,
				Settings.highScoreSize);
		page3.add(highscoreSize);

	}

	private JPanel addSlider(String name, int min, int max, int step, int value) {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JPanel textPanel = new JPanel();
		textPanel.add(new JLabel(name));
		panel.add(textPanel);

		JPanel sliderPanel = new JPanel();
		if (value < min || value > max) {
			value = min + (int) (Math.random() * (max - min));
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
		textField.setPreferredSize(new Dimension(100, 20));
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

		button = new JButton("Apply");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveChanges();
				Settings.load();
			}
		});
		panel.add(button);

		button = new JButton("Default");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					prefs.clear();
					Settings.load();
					saveChanges();
					dialog.repaint();
				} catch (BackingStoreException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(button);

		button = new JButton("Cancel");
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
		dialog.dispose();
	}

	private void saveChanges() {
		prefs.put(Settings.TELEPHONE_NUMBER_ID, getFieldText(telephoneNumber));

		prefs.putFloat(Settings.fireDamageID, getSliderValue(fireDamage));
		prefs.putFloat(Settings.houseHealthpointsIncreaseID,
				getSliderValue(houseHealthpointsIncrease) * 100);
		prefs.putInt(Settings.fireIncreaseID, getSliderValue(fireIncrease));

		prefs.put(Settings.rainKeyWordID, getFieldText(rainKeyWord));
		prefs.put(Settings.lightningKeyWordID, getFieldText(lightningKeyWord));
		prefs.put(Settings.pressureKeyWordID, getFieldText(pressureKeyword));

		prefs.putFloat(Settings.rainDamageID, getSliderValue(rainDamage));
		prefs.putFloat(Settings.rainCooldownID,
				getSliderValue(rainCooldown) * 1000);
		prefs.putFloat(Settings.lightningCooldownID,
				getSliderValue(lightningCooldown) * 1000);
		prefs.putFloat(Settings.pressureCooldownID,
				getSliderValue(pressureCooldown) * 1000);

		prefs.putFloat(Settings.waterDamageID, getSliderValue(waterDamage));
		prefs.putInt(Settings.waterPressureIncID,
				getSliderValue(waterPressureIcrease));
		prefs.putFloat(Settings.waterAimSizeID, getSliderValue(waterAimSize));

		prefs.putLong(Settings.playerTimeoutID,
				getSliderValue(playerTimeout) * 1000);
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
}
