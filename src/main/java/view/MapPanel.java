package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final char WALL = '+';
	private final char EMPTY = '.';
	private final char GOAL = '*';
	private final char BOX = '#';
	private final char PLAYER = 'W';

	private char[][] level;
	// private int level_map = 1;

	private final ImageIcon wallIcon = new ImageIcon("img/wall.png");
	private final ImageIcon emptyIcon = new ImageIcon("img/ground.png");
	private final ImageIcon boxIcon = new ImageIcon("img/box.png");
	private final ImageIcon playerIcon = new ImageIcon("img/player.png");
	private final ImageIcon goalIcon = new ImageIcon("img/goal.png");

	private JLabel turnCount;
	private JLabel turnBox;
	private JLabel turnWarehouseman;

	public MapPanel() throws IlegalPositionException, ObjectPositionNotFoundException {
		setLayout(new BorderLayout());
		setSize(400,500);
		
		//Creating count panel
		JPanel CountPanel = new JPanel(new FlowLayout());

		turnWarehouseman = new JLabel("W: 0");
		CountPanel.add(turnWarehouseman);

		turnBox = new JLabel("P: 0");
		CountPanel.add(turnBox);

		turnCount = new JLabel("T: 0");
		CountPanel.add(turnCount);
		
		//Creating buttons panel
		JButton newGame = new JButton("New Game");
	    JButton loadGame = new JButton("Load Game");

	    // Agregar acción a los botones
	    newGame.addActionListener(e -> {
//			try {
//				gc.createMap(new File("maps/map_level_1.txt").getAbsolutePath());
//			} catch (IlegalPositionException | ObjectPositionNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		});
	    loadGame.addActionListener(e -> JOptionPane.showMessageDialog(null, "Cargar Partida"));
	    JPanel buttonsPanel = new JPanel(new FlowLayout());

	    buttonsPanel.add(newGame);
	    buttonsPanel.add(loadGame);
	    
	    //Creating container for the panels
	    JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        container.add(CountPanel);
        container.add(buttonsPanel);

		add(container, BorderLayout.AFTER_LAST_LINE);
	}
	
	public void createMap(char[][] level) {
		this.level = level;
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    if (level == null) {
	        return; // No hay nada que pintar si no hay un mapa
	    }

	    int numRows = level.length;
	    int numCols = level[0].length;

	    int cellSize = Math.min(getWidth() / numCols, getHeight() / numRows);

	    for (int y = 0; y < numRows; y++) {
	        for (int x = 0; x < numCols; x++) {
	            Image image = null;
	            int xPos = x * cellSize;
	            int yPos = y * cellSize;

	            switch (level[y][x]) {
	                case WALL:
	                    image = wallIcon.getImage();
	                    break;
	                case EMPTY:
	                    image = emptyIcon.getImage();
	                    break;
	                case GOAL:
	                    image = goalIcon.getImage();
	                    break;
	                case BOX:
	                    image = boxIcon.getImage();
	                    break;
	                case PLAYER:
	                    image = playerIcon.getImage();
	                    break;
	            }
	            if (image != null) {
	                g.drawImage(image, xPos, yPos, cellSize, cellSize, null);
	            }
	        }
	    }
	}
	
	@Override
	public Dimension getPreferredSize() {
	    if (level == null) {
	        return super.getPreferredSize();
	    }
	    int numRows = level.length;
	    int numCols = level[0].length;
	    int cellSize = Math.min(getWidth() / numCols, getHeight() / numRows);
	    return new Dimension(numCols * cellSize, numRows * cellSize);
	}

}
