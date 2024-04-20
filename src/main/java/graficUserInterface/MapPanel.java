package graficUserInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.IlegalPositionException;
import exceptions.ObjectPositionNotFoundException;
import exceptions.WallException;
import sokoban.CreateMap;
import sokoban.DownAction;
import sokoban.GoalPosition;
import sokoban.LeftAction;
import sokoban.RightAction;
import sokoban.UpAction;
import sokoban.WarehouseMan;

public class MapPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private final int BLOCK_SIZE = 50;
	private final char WALL = '+';
	private final char EMPTY = '.';
	private final char GOAL = '*';
	private final char BOX = '#';
	private final char PLAYER = 'W';

	private char[][] level;
	private WarehouseMan m = new WarehouseMan(0, 0);
	private GoalPosition g = new GoalPosition(0, 0);
	private CreateMap createMap = new CreateMap();
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

	private int counter = 0;
	private WarehouseMan mlast = new WarehouseMan(0, 0);
	private WarehouseMan mAux = new WarehouseMan(0, 0);
	private WarehouseMan m2 = new WarehouseMan(0, 0);
	private boolean last = false;
	private boolean boxMoved = false;
	private ArrayList<WarehouseMan> undoMove = new ArrayList<>();

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
		File file = new File("maps/map_level_1.txt");
		createMap(file.getAbsolutePath());
		setPreferredSize(new Dimension(level[0].length * BLOCK_SIZE, level.length * BLOCK_SIZE));
		addKeyListener(this);
		setFocusable(true);

	}

	private void createMap(String fileName) throws IlegalPositionException, ObjectPositionNotFoundException {
		try {
			level = createMap.createMap(fileName);
			for (int x = 0; x < level.length; x++) {
				for (int y = 0; y < level[0].length; y++) {
					if (level[x][y] == PLAYER) {
						m.setX(x);
						m.setY(y);
					} else if (level[x][y] == GOAL) {
						g.setX(x);
						g.setY(y);
					}
				}
			}
			if (m.getX() == 0 && m.getY() == 0) {
				throw new ObjectPositionNotFoundException("Not found position for all objects");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			level = new char[0][0];
		}
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

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		// boolean finish = false;
		if(m.getBoxCount() != 0)
			boxMoved=true;	
		try {
			mAux = new WarehouseMan(m.getX(), m.getY());
		} catch (IlegalPositionException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (last) {
			m2 = undoMove.get(undoMove.size() - 1);
			undoMove.remove(undoMove.size() - 1);
			mlast.setX(m2.getX());
			mlast.setY(m2.getY());
		}
		switch (keyCode) {
		case KeyEvent.VK_UP:

			UpAction up = new UpAction(m, level);
			try {
				/* finish = */ up.move(m, g, level);
				/*
				 * if(finish) { int option = JOptionPane.showOptionDialog(null,
				 * "¡Nivel completado! ¿Quieres avanzar al siguiente nivel?",
				 * "Nivel completado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				 * null, new String[]{"Sí", "No"}, "Sí"); if (option == JOptionPane.YES_OPTION)
				 * { level_map += level_map; createMap(
				 * "C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\maps\\map_level_"
				 * + level_map + ".txt"); } }
				 */
			} catch (WallException e1) {
				e1.getMessage();
				/*
				 * } catch (IlegalPositionException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); } catch (PlayerNotFoundException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace();
				 */
			}
			break;
		case KeyEvent.VK_DOWN:
			DownAction down = new DownAction(m, level);
			try {
				/* finish = */ down.move(m, g, level);
				/*
				 * if(finish) { int option = JOptionPane.showOptionDialog(null,
				 * "¡Nivel completado! ¿Quieres avanzar al siguiente nivel?",
				 * "Nivel completado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				 * null, new String[]{"Sí", "No"}, "Sí"); if (option == JOptionPane.YES_OPTION)
				 * { level_map += level_map; createMap(
				 * "C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\maps\\map_level_"
				 * + level_map + ".txt"); } }
				 */
			} catch (WallException e1) {
				e1.getMessage();
				/*
				 * } catch (IlegalPositionException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); } catch (PlayerNotFoundException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace();
				 */
			}
			break;
		case KeyEvent.VK_LEFT:
			LeftAction left = new LeftAction(m, level);
			try {
				/* finish = */ left.move(m, g, level);
				/*
				 * if(finish) { int option = JOptionPane.showOptionDialog(null,
				 * "¡Nivel completado! ¿Quieres avanzar al siguiente nivel?",
				 * "Nivel completado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				 * null, new String[]{"Sí", "No"}, "Sí"); if (option == JOptionPane.YES_OPTION)
				 * { level_map += level_map; createMap(
				 * "C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\maps\\map_level_"
				 * + level_map + ".txt"); } }
				 */
			} catch (WallException e1) {
				e1.getMessage();
				/*
				 * } catch (IlegalPositionException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); } catch (PlayerNotFoundException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace();
				 */
			}
			break;
		case KeyEvent.VK_RIGHT:
			RightAction right = new RightAction(m, level);
			try {
				/* finish = */ right.move(m, g, level);
				/*
				 * if(finish) { int option = JOptionPane.showOptionDialog(null,
				 * "¡Nivel completado! ¿Quieres avanzar al siguiente nivel?",
				 * "Nivel completado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				 * null, new String[]{"Sí", "No"}, "Sí"); if (option == JOptionPane.YES_OPTION)
				 * { level_map += level_map; createMap(
				 * "C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\maps\\map_level_"
				 * + level_map + ".txt"); } }
				 */
			} catch (WallException e1) {
				e1.getMessage();
				/*
				 * } catch (IlegalPositionException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); } catch (PlayerNotFoundException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace();
				 */
			}
			break;
		}
		if (!boxMoved && m.getX() == mlast.getX() && m.getY() == mlast.getY()) {
			if (!last) {
				m2 = undoMove.get(undoMove.size() - 1);
				undoMove.remove(undoMove.size() - 1);
			}
			m.setCount(m.getCount() - 2);			
			mlast.setX(m2.getX());
			mlast.setY(m2.getY());
			last = true;
			counter--;
			if (counter == 0) {
				undoMove.add(mlast);
			}
		} else {
			undoMove.add(mAux);
			mlast.setX(mAux.getX());
			mlast.setY(mAux.getY());
			last = false;
			counter++;
		}
		turnWarehouseman.setText("W: " + m.getCount());
		turnBox.setText("P: " + m.getBoxCount());
		turnCount.setText("T: " + counter);
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// No es necesario implementar este método, pero se debe proporcionar debido a
		// la interfaz KeyListener
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// No es necesario implementar este método, pero se debe proporcionar debido a
		// la interfaz KeyListener
	}
}
