package hof.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import com.badlogic.gdx.*;
import javax.swing.*;

public class SettingsDialog {
	
	private JDialog frame;
	private Preferences prefs;
	private String[] keys;
	
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
		frame = new JDialog();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addText();
		addButtons();
		frame.pack();
		frame.setSize(300, 300);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2 - frame.getHeight() / 2);
		frame.setVisible(true);
	}
	
	private void addText() {
		JPanel panel = new JPanel();
		for (String string : keys) {
			JLabel label = new JLabel(string);
			panel.add(label);
		}
		frame.add(panel, BorderLayout.CENTER);
	}
	
	private void addButtons() {
		JPanel panel = new JPanel();
		JButton button;
		button = new JButton("Ok");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		panel.add(button);
		
		button = new JButton("Abbruch");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Gdx.app.exit();
				System.exit(0);					
			}
		});
		panel.add(button);
		frame.add(panel, BorderLayout.SOUTH);
	}
}
