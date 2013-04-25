package hof.start;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class HighscoreDialog {

	private JDialog dialog;
	
	public HighscoreDialog(JFrame parent) {
		dialog = new JDialog(parent, "Highscore", false);
		FrameUtils.setIcon(dialog);
		
		JPanel contentPane = (JPanel) dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		JTextPane textPane = new JTextPane();
		textPane.setText("Highscore");
		textPane.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textPane);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	
		dialog.pack();
		dialog.setSize(300, 300);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}

}
