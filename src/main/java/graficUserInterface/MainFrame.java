package graficUserInterface;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	public MainFrame() {
		setSize(400,500);  
		setTitle("Sokoban");
        setLocationRelativeTo(null);
        
		this.setLayout(new BorderLayout());

		createComponents();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}

	private void createComponents () {
		JPanel b=new JPanel(); 
		b.setOpaque(true);
		b.setBackground(Color.BLACK);
		add(b);  
		
        MapPanel mapPanel = new MapPanel("C:\\Users\\cesar\\OneDrive\\Escritorio\\Eclipse\\eclipse-workspace\\PProject\\sokobanproject\\maps\\map_level_1.txt");
        add(mapPanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		JFrame frame = new MainFrame(); 
		frame.setVisible(true);  
	}

}
