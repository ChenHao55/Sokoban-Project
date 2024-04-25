package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int BLOCK_SIZE = 50;
	private final char WALL = '+';
	private final char EMPTY = '.';
	private final char GOAL = '*';
	private final char BOX = '#';
	private final char PLAYER = 'W';

	private char[][] level;
	// private int level_map = 1;

	private final ImageIcon wallIcon = createImageIconFromUrl(
			"https://github.com/jdesrosiers/sokoban-ui/blob/master/img/wall.png?raw=true");// = new
																							// ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\wall.png");
	private final ImageIcon emptyIcon = createImageIconFromUrl(
			"https://github.com/jdesrosiers/sokoban-ui/blob/master/img/ground.png?raw=true");// = new
																								// ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\ground.png");
	private final ImageIcon boxIcon = createImageIconFromUrl(
			"https://github.com/jdesrosiers/sokoban-ui/blob/master/img/box.png?raw=true");// = new
																							// ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\box.png");
	private final ImageIcon playerIcon = createImageIconFromUrl(
			"https://github.com/jdesrosiers/sokoban-ui/blob/master/img/player.png?raw=true");// = new
																								// ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\player.png");
	private final ImageIcon goal = createImageIconFromUrl(
			"https://github.com/jdesrosiers/sokoban-ui/blob/master/img/goal.png?raw=true");// = new
																							// ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\goal.png");

	private JLabel turnCount;
	private JLabel turnBox;
	private JLabel turnWarehouseman;
	
	//Metodo para crear imagenes a partir de una URL
	private ImageIcon createImageIconFromUrl(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			return new ImageIcon(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public MapPanel() throws IlegalPositionException, ObjectPositionNotFoundException {
		setLayout(new BorderLayout());
		JPanel CountPanel = new JPanel(new FlowLayout());

		turnWarehouseman = new JLabel("W: 0");
		CountPanel.add(turnWarehouseman);

		turnBox = new JLabel("P: 0");
		CountPanel.add(turnBox);

		turnCount = new JLabel("T: 0");
		CountPanel.add(turnCount);
		add(CountPanel, BorderLayout.AFTER_LAST_LINE);
		setPreferredSize(new Dimension(500, 700));
		setFocusable(true);

	}

	public void createMap(char[][] level) throws IlegalPositionException, ObjectPositionNotFoundException {
		this.level = level;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[y].length; x++) {
				Image image = null;
				switch (level[y][x]) {
				case WALL:
					image = wallIcon.getImage();
					break;
				case EMPTY:
					image = emptyIcon.getImage();
					break;
				case GOAL:
					image = goal.getImage();
					break;
				case BOX:
					image = boxIcon.getImage();
					break;
				case PLAYER:
					image = playerIcon.getImage();
					break;
				}
				if (image != null) {
					g.drawImage(image, x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, null);
				}
			}
		}
	}

}
