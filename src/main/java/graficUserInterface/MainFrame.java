package graficUserInterface;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import exceptions.IlegalPositionException;
import exceptions.PlayerNotFoundException;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	public MainFrame() throws IlegalPositionException, PlayerNotFoundException {
		setSize(400,500);  
		setTitle("Sokoban");
        setLocationRelativeTo(null);
        
		this.setLayout(new BorderLayout());

		createComponents();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void createComponents () throws IlegalPositionException, PlayerNotFoundException {
		JPanel b=new JPanel(); 
		b.setOpaque(true);
		b.setBackground(Color.BLACK);
		add(b);  
		
        MapPanel mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) throws IlegalPositionException, PlayerNotFoundException {
		JFrame frame = new MainFrame(); 
		frame.setVisible(true);  
	}

}
