import controller.GameController;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import view.MainFrame;
import view.MapPanel;

public class GameMain {
	
	public static void main(String[] args) throws IlegalPositionException, ObjectPositionNotFoundException {
		
		MainFrame mf = new MainFrame();
		MapPanel mp = new MapPanel();
		GameController gc = new GameController(mf, mp);
		
		mf.setGc(gc);
		mp.setGc(gc);
		mf.setVisible(true);
	}
}
