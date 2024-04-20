package graficUserInterface;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import exceptions.IlegalPositionException;
import exceptions.ObjectPositionNotFoundException;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	public MainFrame() throws IlegalPositionException, ObjectPositionNotFoundException {
		setSize(400,500);  
		setTitle("Sokoban");
        setLocationRelativeTo(null);
        
		this.setLayout(new BorderLayout());

		createComponents();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void createComponents () throws IlegalPositionException, ObjectPositionNotFoundException {
		JPanel b=new JPanel(); 
		b.setOpaque(true);
		b.setBackground(Color.BLACK);
		add(b);  
		
        MapPanel mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) throws IlegalPositionException, ObjectPositionNotFoundException {
		JFrame frame = new MainFrame(); 
		frame.setVisible(true);  
	}

}
