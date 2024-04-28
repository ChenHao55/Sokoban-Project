package view;

import javax.swing.*;

import controller.GameController;

import java.awt.*;

public class CongratsPanel extends JPanel {

    public CongratsPanel(GameController gc) {
        setLayout(new BorderLayout());
        setBackground(Color.GREEN);

        // Create a label for the congratulations message
        JLabel congratsLabel = new JLabel("Congratulations! You've completed the Sokoban game!", SwingConstants.CENTER);
        congratsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        congratsLabel.setForeground(Color.BLUE);

        // Add the label to the panel
        add(congratsLabel, BorderLayout.CENTER);

        // Create a button that allows the user to play again
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> {
        });

        // Add the button to the panel
        add(playAgainButton, BorderLayout.SOUTH);
    }
}
