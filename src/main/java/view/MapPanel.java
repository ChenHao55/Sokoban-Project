package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameControllerI;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;

public class MapPanel extends JPanel implements MapPanelI{

	private static final long serialVersionUID = 1L;
	private static final char WALL = '+';
	private static final char EMPTY = '.';
	private static final char GOAL = '*';
	private static final char BOX = '#';
	private static final char PLAYER = 'W';
	private static final char GOALBOX = '@';
	private char[][] level;

	private static final ImageIcon wallIcon = new ImageIcon("img" + File.separator + "wall.png");
	private static final ImageIcon emptyIcon = new ImageIcon("img" + File.separator + "ground.png");
	private static final ImageIcon boxIcon = new ImageIcon("img" + File.separator + "box.png");
	private static final ImageIcon playerIcon = new ImageIcon("img" + File.separator + "player.png");
	private static final ImageIcon goalIcon = new ImageIcon("img" + File.separator + "goal.png");
	private static final ImageIcon goalBoxImg = new ImageIcon("img" + File.separator + "goal_box.png");


	private JLabel turnCount;
	private JLabel turnBox;
	private JLabel turnWarehouseman;
	
	private JLabel levelName;
	
	private transient GameControllerI gc;
	
	public JLabel getTurnCount() {
		return this.turnCount;
	}
	
	public JLabel getTurnBox() {
		return this.turnBox;
	}
	
	public JLabel getTurnWarehouseMan() {
		return this.turnWarehouseman;
	}
	
	public JLabel getLevelName() {
		return this.levelName;
	}
	
	public GameControllerI getGc() {
		return this.gc;
	}

	public void setGc(GameControllerI gc) {
		this.gc = gc;
	}

	public MapPanel() throws IlegalPositionException, ObjectPositionNotFoundException {
		setLayout(new BorderLayout());
		setSize(400,500);
		
		//Creating count panel
		JPanel countPanel = new JPanel(new FlowLayout());

		turnWarehouseman = new JLabel("W: 0");
		countPanel.add(turnWarehouseman);

		turnBox = new JLabel("P: 0");
		countPanel.add(turnBox);

		turnCount = new JLabel("T: 0");
		countPanel.add(turnCount);
		
		//Creating count panel
		JPanel levelPanel = new JPanel(new FlowLayout());
		
		levelName = new JLabel("Level: 1");
		levelPanel.add(levelName);
		
		//Creating buttons panel
		JButton newGame = new JButton("New Game");
		JButton loadGame = new JButton("Load Game");
		JButton saveGame = new JButton("Save Game");
	    JButton restartLevel = new JButton("Restart Level");

	    // Agregar acción a los botones
	    
	    //New Game Button
	    newGame.addActionListener(e -> {
			try {
				gc.newGame(new File("maps" + File.separator + "level_1.txt").getAbsolutePath());
			} catch (IlegalPositionException | FileNotFoundException | IlegalMap | ObjectPositionNotFoundException e1) {
				e1.getMessage();
			}
		});
	    
	    //Save Game Button
	    saveGame.addActionListener(e -> {
			try {
				gc.saveGame();
			} catch (ObjectPositionNotFoundException | IlegalPositionException e2) {
				e2.getMessage();
			}
		});
	    
	    //Load Game Button
	    loadGame.addActionListener(e -> {
	    	try {
				gc.loadGame();
			} catch (NumberFormatException | IlegalPositionException | ObjectPositionNotFoundException e1) {
				e1.getMessage();
			}
	    });
	    
	    //Restart Game Button --> no implementado
	    restartLevel.addActionListener(e -> {
			try {
				gc.restartLevel();
			} catch (FileNotFoundException | IlegalPositionException | IlegalMap | ObjectPositionNotFoundException e1) {
				e1.getMessage();
			}
		});
	    JPanel buttonsPanel = new JPanel(new FlowLayout());

	    buttonsPanel.add(newGame);
	    buttonsPanel.add(loadGame);
	    buttonsPanel.add(saveGame);
	    buttonsPanel.add(restartLevel);
	    
	    //Creating container for the panels
	    JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        container.add(countPanel);
        container.add(buttonsPanel);
        container.add(levelPanel);

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
					case GOALBOX:
						image = goalBoxImg.getImage();
						break;
					default:
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
