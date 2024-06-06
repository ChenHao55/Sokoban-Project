import controller.GameController;
import model.exceptions.IlegalPositionException;
import model.exceptions.ObjectPositionNotFoundException;
import model.services.GameService;
import view.MainFrame;
import view.MapPanel;

public class GameMain {
	
	public static void main(String[] args) throws IlegalPositionException, ObjectPositionNotFoundException {
		
		MainFrame mf = new MainFrame();
		MapPanel mp = new MapPanel();
		GameService gs = new GameService(mf, mp);
		GameController gc = new GameController(gs);
		
		mf.setGc(gc);
		mp.setGc(gc);
		mf.setVisible(true);
	}
}
