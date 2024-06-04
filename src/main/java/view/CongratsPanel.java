package view;

import javax.swing.*;

import java.awt.*;

public class CongratsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public CongratsPanel(MainFrame mf, int punctuation) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Create a label for the congratulations message
        JLabel congratsLabel = new JLabel("<html><div style='text-align: center;'>Congratulations!<br>"
        		+ "You've completed the Sokoban game!<br><br>Punctuation:" + Integer.toString(punctuation) + "</div></html> ", SwingConstants.CENTER);
        congratsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        congratsLabel.setForeground(Color.WHITE);
        // Add the label to the panel
        add(congratsLabel, BorderLayout.CENTER);

        // Create a button that allows the user to play again
        JPanel buttonPanel = new JPanel(new FlowLayout());
	    buttonPanel.setBackground(Color.BLACK);
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> {
        	mf.createButtons();
        });

        // Add the button to the panel
        buttonPanel.add(continueButton, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
