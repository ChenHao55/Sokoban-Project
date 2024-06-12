package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.GameController;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;

public class MainFrame extends JFrame implements KeyListener, MainFrameI  {

	private static final long serialVersionUID = 1L;
	private transient GameController gc;
	private char[][] level;

	private static final char WALL = '+';
	private static final char EMPTY = '.';
	private static final char GOAL = '*';
	private static final char BOX = '#';
	private static final char PLAYER = 'W';
	private static final char GOALBOX = '@';

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

	private int boxCount = 0;
	private int count = 0;
	private int globalCount = 0;
	private int levelNumber = 0;

	public MainFrame() {
		setSize(450, 550);  
		setTitle("Sokoban");
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		addKeyListener(this);
		createButtons();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setFocusable(true);
	}

	public void createBottonsFromExternalClasses() {
		this.createButtons();
	}

	private void createButtons() {
		getContentPane().removeAll();
		
		JPanel panel = new JPanel(new BorderLayout());

		JLabel title = new JLabel("<html><div style='text-align: center;'>SOKOBAN</div></html>", SwingConstants.CENTER);
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 70));
		title.setForeground(Color.WHITE);

		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.setBackground(Color.BLACK);

		JButton newGame = new JButton("New Game");
		JButton loadGame = new JButton("Load Game");

		newGame.addActionListener(e -> {
			try {
				gc.newGame(new File("maps" + File.separator + "level_1.txt").getAbsolutePath());
			} catch (IlegalPositionException | FileNotFoundException | IlegalMap | ObjectPositionNotFoundException e1) {
				e1.getMessage();
			}
		});
	    
		loadGame.addActionListener(e -> {
			try {
				gc.loadGameMF();
			} catch (NumberFormatException | IlegalPositionException | ObjectPositionNotFoundException e1) {
				e1.getMessage();
			}
		});

		panel.add(title, BorderLayout.CENTER);
		panel.setBackground(Color.BLACK);

		buttonsPanel.add(newGame);
		buttonsPanel.add(loadGame);
		panel.add(buttonsPanel, BorderLayout.SOUTH);

		add(panel, BorderLayout.CENTER);
	    
		revalidate();
		repaint();
	}

	public File saveGame(char option) {
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        
    	// Establece el directorio actual al directorio "games_saved"
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator + "games_saved"));
        
        // Filtrar para mostrar solo archivos .txt
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        // Muestra el diálogo de guardar archivo
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            
            // Añade .txt al fichero si no lo tiene ya
            if (option == 's' && !file.getPath().endsWith(".txt")) {
                file = new File(file.getPath() + ".txt");
            }
        }
            
        return file;
    }
	
	public void setGc(GameController gc) {
		this.gc = gc;
	}

	public void createMap(char[][] level) throws IlegalPositionException, ObjectPositionNotFoundException {
		this.level = level;
	}

	public void updateCounters(int countBox, int count, int globalCount) {
		this.boxCount = countBox;
		this.count = count;
		this.globalCount = globalCount;
		turnBox.setText("P: " + countBox);
		turnWarehouseman.setText("W: " + count);
		turnCount.setText("T: " + globalCount);
	}

	public void updateLevelName(int levelNumber) {
		this.levelNumber = levelNumber;
		this.levelName.setText("Nivel: " + levelNumber);
	}

	public void paintMap() {
		getContentPane().removeAll();
		JPanel mapPanel = new JPanel() {
			private static final long serialVersionUID = 1L;
	
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (level == null) {
					return;
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
		};
	
		// Creating count panel
		JPanel countPanel = new JPanel(new FlowLayout());
		turnWarehouseman = new JLabel("W: " + boxCount);
		countPanel.add(turnWarehouseman);
		turnBox = new JLabel("P: " + count);
		countPanel.add(turnBox);
		turnCount = new JLabel("T: " + globalCount);
		countPanel.add(turnCount);
	
		// Creating level panel
		JPanel levelPanel = new JPanel(new FlowLayout());
		levelName = new JLabel("Level: " + levelNumber);
		levelPanel.add(levelName);
	
		// Creating buttons panel
		JButton newGame = new JButton("New Game");
		JButton loadGame = new JButton("Load Game");
		JButton saveGame = new JButton("Save Game");
		JButton restartLevel = new JButton("Restart Level");
	
		newGame.addActionListener(e -> {
			try {
				gc.newGame(new File("maps" + File.separator + "level_1.txt").getAbsolutePath());
			} catch (IlegalPositionException | FileNotFoundException | IlegalMap | ObjectPositionNotFoundException e1) {
				e1.getMessage();
			}
		});
	
		saveGame.addActionListener(e -> {
			try {
				gc.saveGame();
			} catch (ObjectPositionNotFoundException | IlegalPositionException e1) {
				e1.getMessage();
			}
		});
	
		loadGame.addActionListener(e -> {
			try {
				gc.loadGame();
			} catch (NumberFormatException | IlegalPositionException | ObjectPositionNotFoundException e1) {
				e1.getMessage();
			}
		});
	
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
	
		// Creating container for the panels
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(countPanel);
		container.add(buttonsPanel);
		container.add(levelPanel);
	
		add(container, BorderLayout.SOUTH);
		add(mapPanel, BorderLayout.CENTER);
	
		revalidate();
		repaint();
	}

	public void showCongrats(int punctuation) {
		getContentPane().removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Create a label for the congratulations message
        JLabel congratsLabel = new JLabel("<html><div style='text-align: center;'>Congratulations!<br>"
        		+ "You've completed the Sokoban game!<br><br>Punctuation:" + Integer.toString(punctuation) + "</div></html> ", SwingConstants.CENTER);
        congratsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        congratsLabel.setForeground(Color.BLACK);
        // Add the label to the panel
        add(congratsLabel, BorderLayout.CENTER);

        // Create a button that allows the user to play again
        JPanel buttonPanel = new JPanel(new FlowLayout());
	    buttonPanel.setBackground(Color.BLACK);
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> this.createBottonsFromExternalClasses());

        // Add the button to the panel
        buttonPanel.add(continueButton, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
    }
	
	public void showError() {
		JOptionPane.showMessageDialog(null, "Wrong map", "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_UP:
			try {
				gc.moveUp();
			} catch (FileNotFoundException | ObjectPositionNotFoundException | IlegalPositionException
					| IlegalMap e2) {
				e2.getMessage();
			}
				break;
			case KeyEvent.VK_DOWN:
			try {
				gc.moveDown();
			} catch (FileNotFoundException | ObjectPositionNotFoundException | IlegalPositionException
					| IlegalMap e2) {
				e2.getMessage();
			}
				break;
			case KeyEvent.VK_LEFT:
			try {
				gc.moveLeft();
			} catch (FileNotFoundException | ObjectPositionNotFoundException | IlegalPositionException
					| IlegalMap e2) {
				e2.getMessage();
			}
				break;
			case KeyEvent.VK_RIGHT:
			try {
				gc.moveRight();
			} catch (FileNotFoundException | ObjectPositionNotFoundException | IlegalPositionException
					| IlegalMap e2) {
				e2.getMessage();
			}
				break;
			case KeyEvent.VK_Z:
				if(e.isControlDown()) {
					try {
						gc.undoMovement();
					} catch (IlegalPositionException | ObjectPositionNotFoundException e1) {
						e1.getMessage();
					}
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// No implementation needed
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// No implementation needed
	}

}