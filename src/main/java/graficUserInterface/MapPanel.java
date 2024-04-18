package graficUserInterface;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import sokoban.CreateMap;

public class MapPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final int BLOCK_SIZE = 50;
    private final char WALL = '+';
    private final char EMPTY = '.';
    private final char GOAL = '*';
    private final char BOX = '#';
    private final char PLAYER = 'W';

    private char[][] level;
    
    private final ImageIcon wallIcon = new ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\wall.png");
    private final ImageIcon emptyIcon = new ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\ground.png");
    private final ImageIcon boxIcon = new ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\box.png");
    private final ImageIcon playerIcon = new ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\player.png");
    private final ImageIcon goal = new ImageIcon("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\img\\goal.png");
    public MapPanel(String fileName) {
        CreateMap createMap = new CreateMap();
        try {
            level = createMap.createMap(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            level = new char[0][0];
        }
        setPreferredSize(new Dimension(level[0].length * BLOCK_SIZE, level.length * BLOCK_SIZE));
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
