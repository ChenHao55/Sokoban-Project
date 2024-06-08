package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import controller.GameController;
import model.exceptions.IlegalMap;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;


public class MainFrame extends JFrame implements KeyListener  {

	private static final long serialVersionUID = 1L;
	private GameController gc;

	public MainFrame() throws IlegalPositionException, ObjectPositionNotFoundException {
		
		setSize(450,550);  
		setTitle("Sokoban");
        setLocationRelativeTo(null);
        
		setLayout(new BorderLayout());
		addKeyListener(this);
		
		createButtons();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setFocusable(true);
	}
	
	//Metodo para crear los botones de "Nueva Partida" o "Cargar Partida"
	public void createButtons() {
		getContentPane().removeAll();
		
		JPanel panel = new JPanel(new BorderLayout()); // Usamos BorderLayout para organizar los componentes

	    // Crear el JLabel para el título
	    JLabel title = new JLabel("<html><div style='text-align: center;'>SOKOBAN</div></html>", SwingConstants.CENTER);
	    title.setFont(new Font("Comic Sans MS", Font.BOLD, 70));
	    title.setForeground(Color.WHITE);

	    // Crear un panel para los botones y establecer su diseño
	    JPanel buttonsPanel = new JPanel(new FlowLayout());
	    buttonsPanel.setBackground(Color.BLACK);

	    // Crear los botones
	    JButton newGame = new JButton("New Game");
	    JButton loadGame = new JButton("Load Game");

	    // Agregar acción a los botones
	    //New Game Button
	    newGame.addActionListener(e -> {
			try {
				gc.newGame(new File("maps" + File.separator + "level_1.txt").getAbsolutePath());
			} catch (IlegalPositionException | FileNotFoundException | IlegalMap e1) {
				e1.getMessage();
			}
		});
	    
	    //Load Game Button
	    loadGame.addActionListener(e -> {
	    	try {
	    		gc.loadGameMF();
			} catch (NumberFormatException | IlegalPositionException e1) {
				e1.getMessage();
			}
	    });

	    // Agregar el título al centro del panel principal
	    panel.add(title, BorderLayout.CENTER);
	    panel.setBackground(Color.BLACK);

	    // Agregar los botones debajo del título
	    buttonsPanel.add(newGame);
	    buttonsPanel.add(loadGame);
	    panel.add(buttonsPanel, BorderLayout.SOUTH);

	    // Agregar el panel principal al centro del JFrame
	    add(panel, BorderLayout.CENTER);
	    
	    revalidate();
		repaint();
	}

	public GameController getGc() {
		return this.gc;
	}

	public void setGc(GameController gc) {
		this.gc = gc;
	}
	
	//Añadir al Frame el Panel del Mapa
	public void paintMap(MapPanel mp) {
		getContentPane().removeAll();
		add(mp, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	public void showCongrats(int punctuation) {
		getContentPane().removeAll();

        // Add the container to the frame
        CongratsPanel cp = new CongratsPanel(this, punctuation);
        add(cp, BorderLayout.CENTER);
        revalidate();
		repaint();
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
					} catch (IlegalPositionException e1) {
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
