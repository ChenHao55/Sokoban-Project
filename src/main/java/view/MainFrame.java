package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import controller.GameController;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;


public class MainFrame extends JFrame implements KeyListener  {

	private static final long serialVersionUID = 1L;
	private GameController gc;


	public MainFrame() throws IlegalPositionException, ObjectPositionNotFoundException {

		setSize(400,500);  
		setTitle("Sokoban");
        setLocationRelativeTo(null);
        
		setLayout(new BorderLayout());
		addKeyListener(this);
		
		createButtons();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setFocusable(true);
	}
	
	//Metodo para crear los botones de "Nueva Partida" o "Cargar Partida"
	private void createButtons() {
		JPanel panel = new JPanel(new BorderLayout()); // Usamos BorderLayout para organizar los componentes

	    // Crear el JLabel para el título
	    JLabel title = new JLabel("<html><div style='text-align: center;'>SOKOBAN</div></html>", SwingConstants.CENTER);
	    title.setFont(new Font("Times New Roman", Font.BOLD, 70));
	    title.setForeground(Color.WHITE);

	    // Crear un panel para los botones y establecer su diseño
	    JPanel buttonsPanel = new JPanel(new FlowLayout());
	    buttonsPanel.setBackground(Color.BLACK);

	    // Crear los botones
	    JButton newGame = new JButton("New Game");
	    JButton loadGame = new JButton("Load Game");

	    // Agregar acción a los botones
	    newGame.addActionListener(e -> {
			try {
				gc.createMap(new File("maps/map_level_1.txt").getAbsolutePath());
			} catch (IlegalPositionException | ObjectPositionNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	    loadGame.addActionListener(e -> JOptionPane.showMessageDialog(null, "Cargar Partida"));

	    // Agregar el título al centro del panel principal
	    panel.add(title, BorderLayout.CENTER);
	    panel.setBackground(Color.BLACK);

	    // Agregar los botones debajo del título
	    buttonsPanel.add(newGame);
	    buttonsPanel.add(loadGame);
	    panel.add(buttonsPanel, BorderLayout.SOUTH);

	    // Agregar el panel principal al centro del JFrame
	    add(panel, BorderLayout.CENTER);
	}

	public GameController getGc() {
		return gc;
	}

	public void setGc(GameController gc) {
		this.gc = gc;
	}
	
	//Añadir al Frame el Panel del Mapa
	public void paintMap(MapPanel mp) throws IlegalPositionException, ObjectPositionNotFoundException {
		getContentPane().removeAll();
		add(mp, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_UP:
			try {
				gc.moveUp();
			} catch (ObjectPositionNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case KeyEvent.VK_DOWN:
			try {
				gc.moveDown();
			} catch (ObjectPositionNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case KeyEvent.VK_LEFT:
			try {
				gc.moveLeft();
			} catch (ObjectPositionNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case KeyEvent.VK_RIGHT:
			try {
				gc.moveRight();
			} catch (ObjectPositionNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case KeyEvent.VK_CONTROL:
			try {
				gc.undoMovement();
			} catch (IlegalPositionException | ObjectPositionNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
